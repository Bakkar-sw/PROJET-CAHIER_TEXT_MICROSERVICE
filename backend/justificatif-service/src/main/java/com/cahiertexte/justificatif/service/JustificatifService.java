package com.cahiertexte.justificatif.service;

import com.cahiertexte.common.constants.AppConstants;
import com.cahiertexte.common.exception.BadRequestException;
import com.cahiertexte.common.exception.ResourceNotFoundException;
import com.cahiertexte.justificatif.dto.JustificatifDTO;
import com.cahiertexte.justificatif.model.Justificatif;
import com.cahiertexte.justificatif.repository.JustificatifRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service de gestion des justificatifs
 * 
 * @author Fatou Leye
 */
@Service
@Transactional
public class JustificatifService {

    private static final Logger logger = LoggerFactory.getLogger(JustificatifService.class);

    @Autowired
    private JustificatifRepository justificatifRepository;

    @Value("${upload.path:/tmp/justificatifs}")
    private String uploadPath;

    /**
     * Récupère tous les justificatifs
     */
    public List<JustificatifDTO> getAllJustificatifs() {
        return justificatifRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère un justificatif par son ID
     */
    public JustificatifDTO getJustificatifById(Long id) {
        Justificatif justificatif = justificatifRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.Messages.JUSTIFICATIF_NOT_FOUND));
        return convertToDTO(justificatif);
    }

    /**
     * Récupère les justificatifs d'un étudiant
     */
    public List<JustificatifDTO> getJustificatifsByEtudiant(Long etudiantId) {
        return justificatifRepository.findByEtudiantId(etudiantId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère les justificatifs d'un cours
     */
    public List<JustificatifDTO> getJustificatifsByCours(Long coursId) {
        return justificatifRepository.findByCoursId(coursId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère les justificatifs par status
     */
    public List<JustificatifDTO> getJustificatifsByStatus(String status) {
        validateStatus(status);
        return justificatifRepository.findByStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère les justificatifs en attente (triés par date)
     */
    public List<JustificatifDTO> getJustificatifsEnAttente() {
        return justificatifRepository.findByStatusOrderByDateSoumissionAsc(
                AppConstants.JustificatifStatus.EN_ATTENTE).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Soumet un justificatif (avec ou sans fichier)
     */
    public JustificatifDTO createJustificatif(JustificatifDTO justificatifDTO, MultipartFile fichier) {
        logger.info("Soumission d'un justificatif - Étudiant: {}, Cours: {}", 
                   justificatifDTO.getEtudiantId(), justificatifDTO.getCoursId());

        Justificatif justificatif = new Justificatif();
        justificatif.setEtudiantId(justificatifDTO.getEtudiantId());
        justificatif.setCoursId(justificatifDTO.getCoursId());
        justificatif.setMotif(justificatifDTO.getMotif());
        justificatif.setStatus(AppConstants.JustificatifStatus.EN_ATTENTE);

        // Upload du fichier si présent
        if (fichier != null && !fichier.isEmpty()) {
            try {
                String filename = saveFile(fichier);
                justificatif.setFichier(filename);
                logger.info("Fichier uploadé: {}", filename);
            } catch (IOException e) {
                logger.error("Erreur lors de l'upload du fichier: {}", e.getMessage());
                throw new BadRequestException("Erreur lors de l'upload du fichier");
            }
        }

        Justificatif saved = justificatifRepository.save(justificatif);
        logger.info("Justificatif soumis avec succès: ID {}", saved.getId());

        return convertToDTO(saved);
    }

    /**
     * Valide un justificatif
     */
    public JustificatifDTO validerJustificatif(Long id, Long responsableId, String commentaire) {
        logger.info("Validation du justificatif ID: {} par responsable ID: {}", id, responsableId);

        Justificatif justificatif = justificatifRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.Messages.JUSTIFICATIF_NOT_FOUND));

        if (!AppConstants.JustificatifStatus.EN_ATTENTE.equals(justificatif.getStatus())) {
            throw new BadRequestException("Ce justificatif a déjà été traité");
        }

        justificatif.setStatus(AppConstants.JustificatifStatus.ACCEPTE);
        justificatif.setDateTraitement(LocalDateTime.now());
        justificatif.setTraitePar(responsableId);
        justificatif.setCommentaireTraitement(commentaire);

        Justificatif validated = justificatifRepository.save(justificatif);
        logger.info("Justificatif validé: ID {}", id);

        return convertToDTO(validated);
    }

    /**
     * Refuse un justificatif
     */
    public JustificatifDTO refuserJustificatif(Long id, Long responsableId, String commentaire) {
        logger.info("Refus du justificatif ID: {} par responsable ID: {}", id, responsableId);

        Justificatif justificatif = justificatifRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.Messages.JUSTIFICATIF_NOT_FOUND));

        if (!AppConstants.JustificatifStatus.EN_ATTENTE.equals(justificatif.getStatus())) {
            throw new BadRequestException("Ce justificatif a déjà été traité");
        }

        if (commentaire == null || commentaire.trim().isEmpty()) {
            throw new BadRequestException("Un commentaire est obligatoire pour refuser un justificatif");
        }

        justificatif.setStatus(AppConstants.JustificatifStatus.REFUSE);
        justificatif.setDateTraitement(LocalDateTime.now());
        justificatif.setTraitePar(responsableId);
        justificatif.setCommentaireTraitement(commentaire);

        Justificatif refused = justificatifRepository.save(justificatif);
        logger.info("Justificatif refusé: ID {}", id);

        return convertToDTO(refused);
    }

    /**
     * Supprime un justificatif
     */
    public void deleteJustificatif(Long id) {
        logger.info("Suppression du justificatif ID: {}", id);

        Justificatif justificatif = justificatifRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.Messages.JUSTIFICATIF_NOT_FOUND));

        // Supprimer le fichier si présent
        if (justificatif.getFichier() != null) {
            deleteFile(justificatif.getFichier());
        }

        justificatifRepository.delete(justificatif);
        logger.info("Justificatif supprimé: ID {}", id);
    }

    /**
     * Statistiques des justificatifs d'un étudiant
     */
    public Map<String, Object> getStatsEtudiant(Long etudiantId) {
        long enAttente = justificatifRepository.countByEtudiantIdAndStatus(
                etudiantId, AppConstants.JustificatifStatus.EN_ATTENTE);
        long acceptes = justificatifRepository.countByEtudiantIdAndStatus(
                etudiantId, AppConstants.JustificatifStatus.ACCEPTE);
        long refuses = justificatifRepository.countByEtudiantIdAndStatus(
                etudiantId, AppConstants.JustificatifStatus.REFUSE);

        Map<String, Object> stats = new HashMap<>();
        stats.put("etudiantId", etudiantId);
        stats.put("enAttente", enAttente);
        stats.put("acceptes", acceptes);
        stats.put("refuses", refuses);
        stats.put("total", enAttente + acceptes + refuses);

        return stats;
    }

    /**
     * Sauvegarde un fichier uploadé
     */
    private String saveFile(MultipartFile file) throws IOException {
        // Créer le dossier s'il n'existe pas
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Générer un nom unique
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null && originalFilename.contains(".") 
                ? originalFilename.substring(originalFilename.lastIndexOf(".")) 
                : "";
        String filename = UUID.randomUUID().toString() + extension;

        // Sauvegarder le fichier
        Path filePath = Paths.get(uploadPath, filename);
        Files.write(filePath, file.getBytes());

        return filename;
    }

    /**
     * Supprime un fichier
     */
    private void deleteFile(String filename) {
        try {
            Path filePath = Paths.get(uploadPath, filename);
            Files.deleteIfExists(filePath);
            logger.info("Fichier supprimé: {}", filename);
        } catch (IOException e) {
            logger.error("Erreur lors de la suppression du fichier: {}", e.getMessage());
        }
    }

    /**
     * Valide un status
     */
    private void validateStatus(String status) {
        List<String> validStatus = List.of(
                AppConstants.JustificatifStatus.EN_ATTENTE,
                AppConstants.JustificatifStatus.ACCEPTE,
                AppConstants.JustificatifStatus.REFUSE
        );

        if (!validStatus.contains(status)) {
            throw new BadRequestException("Status invalide: " + status);
        }
    }

    /**
     * Convertit une entité en DTO
     */
    private JustificatifDTO convertToDTO(Justificatif justificatif) {
        JustificatifDTO dto = new JustificatifDTO();
        dto.setId(justificatif.getId());
        dto.setEtudiantId(justificatif.getEtudiantId());
        dto.setCoursId(justificatif.getCoursId());
        dto.setMotif(justificatif.getMotif());
        dto.setFichier(justificatif.getFichier());
        dto.setStatus(justificatif.getStatus());
        dto.setDateSoumission(justificatif.getDateSoumission());
        dto.setDateTraitement(justificatif.getDateTraitement());
        dto.setTraitePar(justificatif.getTraitePar());
        dto.setCommentaireTraitement(justificatif.getCommentaireTraitement());

        // TODO: Enrichir avec les infos via Feign
        // dto.setEtudiantNom("...");
        // dto.setCoursDate("...");

        return dto;
    }
}
