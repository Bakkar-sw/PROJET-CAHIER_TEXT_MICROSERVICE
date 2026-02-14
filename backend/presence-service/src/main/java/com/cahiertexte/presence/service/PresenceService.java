package com.cahiertexte.presence.service;

import com.cahiertexte.common.constants.AppConstants;
import com.cahiertexte.common.exception.BadRequestException;
import com.cahiertexte.common.exception.ResourceNotFoundException;
import com.cahiertexte.presence.dto.PresenceBatchDTO;
import com.cahiertexte.presence.dto.PresenceDTO;
import com.cahiertexte.presence.model.Presence;
import com.cahiertexte.presence.repository.PresenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service de gestion des présences
 * 
 * @author Alioune Kebe
 */
@Service
@Transactional
public class PresenceService {

    private static final Logger logger = LoggerFactory.getLogger(PresenceService.class);

    @Autowired
    private PresenceRepository presenceRepository;

    /**
     * Récupère toutes les présences
     */
    public List<PresenceDTO> getAllPresences() {
        return presenceRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère une présence par son ID
     */
    public PresenceDTO getPresenceById(Long id) {
        Presence presence = presenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.Messages.PRESENCE_NOT_FOUND));
        return convertToDTO(presence);
    }

    /**
     * Récupère les présences d'un cours
     */
    public List<PresenceDTO> getPresencesByCours(Long coursId) {
        return presenceRepository.findByCoursId(coursId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère les présences d'un étudiant
     */
    public List<PresenceDTO> getPresencesByEtudiant(Long etudiantId) {
        return presenceRepository.findByEtudiantId(etudiantId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère les présences par status
     */
    public List<PresenceDTO> getPresencesByStatus(String status) {
        validateStatus(status);
        return presenceRepository.findByStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Enregistre une présence unique
     */
    public PresenceDTO createPresence(PresenceDTO presenceDTO) {
        logger.info("Enregistrement de la présence - Cours: {}, Étudiant: {}", 
                   presenceDTO.getCoursId(), presenceDTO.getEtudiantId());

        // Vérifier si la présence existe déjà
        if (presenceRepository.existsByCoursIdAndEtudiantId(
                presenceDTO.getCoursId(), presenceDTO.getEtudiantId())) {
            throw new BadRequestException("Une présence existe déjà pour cet étudiant à ce cours");
        }

        // Valider le status
        validateStatus(presenceDTO.getStatus());

        Presence presence = new Presence();
        presence.setCoursId(presenceDTO.getCoursId());
        presence.setEtudiantId(presenceDTO.getEtudiantId());
        presence.setStatus(presenceDTO.getStatus());
        presence.setRemarque(presenceDTO.getRemarque());

        Presence savedPresence = presenceRepository.save(presence);
        logger.info("Présence enregistrée avec succès: ID {}", savedPresence.getId());

        return convertToDTO(savedPresence);
    }

    /**
     * Enregistre les présences en batch (pour tout un cours)
     */
    public List<PresenceDTO> createPresencesBatch(PresenceBatchDTO batchDTO) {
        logger.info("Enregistrement en batch des présences pour le cours ID: {}", batchDTO.getCoursId());

        List<PresenceDTO> savedPresences = new ArrayList<>();

        for (PresenceBatchDTO.PresenceItemDTO item : batchDTO.getPresences()) {
            try {
                // Vérifier si existe déjà
                if (presenceRepository.existsByCoursIdAndEtudiantId(
                        batchDTO.getCoursId(), item.getEtudiantId())) {
                    logger.warn("Présence déjà existante - Cours: {}, Étudiant: {}", 
                               batchDTO.getCoursId(), item.getEtudiantId());
                    continue;
                }

                // Valider le status
                validateStatus(item.getStatus());

                Presence presence = new Presence();
                presence.setCoursId(batchDTO.getCoursId());
                presence.setEtudiantId(item.getEtudiantId());
                presence.setStatus(item.getStatus());
                presence.setRemarque(item.getRemarque());

                Presence saved = presenceRepository.save(presence);
                savedPresences.add(convertToDTO(saved));

            } catch (Exception e) {
                logger.error("Erreur lors de l'enregistrement de la présence pour l'étudiant {}: {}", 
                            item.getEtudiantId(), e.getMessage());
            }
        }

        logger.info("{} présences enregistrées sur {} demandées", 
                   savedPresences.size(), batchDTO.getPresences().size());

        return savedPresences;
    }

    /**
     * Met à jour une présence
     */
    public PresenceDTO updatePresence(Long id, PresenceDTO presenceDTO) {
        logger.info("Mise à jour de la présence ID: {}", id);

        Presence presence = presenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.Messages.PRESENCE_NOT_FOUND));

        // Valider le status
        validateStatus(presenceDTO.getStatus());

        presence.setStatus(presenceDTO.getStatus());
        presence.setRemarque(presenceDTO.getRemarque());

        Presence updatedPresence = presenceRepository.save(presence);
        logger.info("Présence mise à jour avec succès: ID {}", id);

        return convertToDTO(updatedPresence);
    }

    /**
     * Supprime une présence
     */
    public void deletePresence(Long id) {
        logger.info("Suppression de la présence ID: {}", id);

        Presence presence = presenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.Messages.PRESENCE_NOT_FOUND));

        presenceRepository.delete(presence);
        logger.info("Présence supprimée avec succès: ID {}", id);
    }

    /**
     * Supprime toutes les présences d'un cours
     */
    @Transactional
    public void deletePresencesByCours(Long coursId) {
        logger.info("Suppression de toutes les présences du cours ID: {}", coursId);
        presenceRepository.deleteByCoursId(coursId);
        logger.info("Présences du cours {} supprimées", coursId);
    }

    /**
     * Statistiques de présence d'un étudiant
     */
    public Map<String, Object> getStatsEtudiant(Long etudiantId) {
        long total = presenceRepository.countByEtudiantId(etudiantId);
        long presences = presenceRepository.countByEtudiantIdAndStatus(etudiantId, AppConstants.PresenceType.PRESENT);
        long absences = presenceRepository.countByEtudiantIdAndStatus(etudiantId, AppConstants.PresenceType.ABSENT);
        long retards = presenceRepository.countByEtudiantIdAndStatus(etudiantId, AppConstants.PresenceType.RETARD);

        double tauxPresence = total > 0 ? (presences * 100.0 / total) : 0.0;

        Map<String, Object> stats = new HashMap<>();
        stats.put("etudiantId", etudiantId);
        stats.put("totalSeances", total);
        stats.put("presences", presences);
        stats.put("absences", absences);
        stats.put("retards", retards);
        stats.put("tauxPresence", Math.round(tauxPresence * 100.0) / 100.0);
        stats.put("alerteAbsence", absences >= AppConstants.Alerts.ABSENCES_CRITIQUES_SEUIL);

        return stats;
    }

    /**
     * Valide un status de présence
     */
    private void validateStatus(String status) {
        List<String> validStatus = List.of(
                AppConstants.PresenceType.PRESENT,
                AppConstants.PresenceType.ABSENT,
                AppConstants.PresenceType.RETARD
        );

        if (!validStatus.contains(status)) {
            throw new BadRequestException("Status invalide: " + status + 
                    ". Valeurs autorisées: PRESENT, ABSENT, RETARD");
        }
    }

    /**
     * Convertit une entité Presence en DTO
     */
    private PresenceDTO convertToDTO(Presence presence) {
        PresenceDTO dto = new PresenceDTO();
        dto.setId(presence.getId());
        dto.setCoursId(presence.getCoursId());
        dto.setEtudiantId(presence.getEtudiantId());
        dto.setStatus(presence.getStatus());
        dto.setRemarque(presence.getRemarque());
        dto.setDateCreation(presence.getDateCreation());
        dto.setDateModification(presence.getDateModification());

        // TODO: Enrichir avec les infos de l'étudiant via Feign
        // dto.setEtudiantNom("...");
        // dto.setEtudiantPrenom("...");
        // dto.setEtudiantClasse("...");

        return dto;
    }
}
