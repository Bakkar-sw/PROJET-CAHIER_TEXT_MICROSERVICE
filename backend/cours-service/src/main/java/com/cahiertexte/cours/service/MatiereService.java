package com.cahiertexte.cours.service;

import com.cahiertexte.common.exception.BadRequestException;
import com.cahiertexte.common.exception.ResourceNotFoundException;
import com.cahiertexte.cours.dto.MatiereDTO;
import com.cahiertexte.cours.model.Matiere;
import com.cahiertexte.cours.repository.MatiereRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service de gestion des matières
 * 
 * @author Abdoulaye Guene
 */
@Service
@Transactional
public class MatiereService {

    private static final Logger logger = LoggerFactory.getLogger(MatiereService.class);

    @Autowired
    private MatiereRepository matiereRepository;

    /**
     * Récupère toutes les matières
     */
    public List<MatiereDTO> getAllMatieres() {
        return matiereRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère une matière par son ID
     */
    public MatiereDTO getMatiereById(Long id) {
        Matiere matiere = matiereRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Matière", "id", id));
        return convertToDTO(matiere);
    }

    /**
     * Récupère les matières d'une classe
     */
    public List<MatiereDTO> getMatieresByClasse(String classe) {
        return matiereRepository.findByClasse(classe).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère les matières d'un professeur
     */
    public List<MatiereDTO> getMatieresByProfesseur(Long professeurId) {
        return matiereRepository.findByProfesseurId(professeurId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère les matières en alerte (< 12h restantes)
     */
    public List<MatiereDTO> getMatieresEnAlerte() {
        return matiereRepository.findMatieresEnAlerte().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Crée une nouvelle matière
     */
    public MatiereDTO createMatiere(MatiereDTO matiereDTO) {
        logger.info("Création d'une nouvelle matière: {}", matiereDTO.getCode());

        // Vérifier si le code existe déjà
        if (matiereRepository.existsByCode(matiereDTO.getCode())) {
            throw new BadRequestException("Une matière avec ce code existe déjà: " + matiereDTO.getCode());
        }

        Matiere matiere = new Matiere();
        matiere.setNom(matiereDTO.getNom());
        matiere.setCode(matiereDTO.getCode());
        matiere.setVolumeHoraire(matiereDTO.getVolumeHoraire());
        matiere.setVolumeRealise(0);
        matiere.setProfesseurId(matiereDTO.getProfesseurId());
        matiere.setClasse(matiereDTO.getClasse());
        matiere.setActif(true);

        Matiere savedMatiere = matiereRepository.save(matiere);
        logger.info("Matière créée avec succès: ID {}", savedMatiere.getId());

        return convertToDTO(savedMatiere);
    }

    /**
     * Met à jour une matière
     */
    public MatiereDTO updateMatiere(Long id, MatiereDTO matiereDTO) {
        logger.info("Mise à jour de la matière ID: {}", id);

        Matiere matiere = matiereRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Matière", "id", id));

        // Vérifier si le code est modifié et s'il existe déjà
        if (!matiere.getCode().equals(matiereDTO.getCode())) {
            if (matiereRepository.existsByCode(matiereDTO.getCode())) {
                throw new BadRequestException("Une matière avec ce code existe déjà: " + matiereDTO.getCode());
            }
        }

        matiere.setNom(matiereDTO.getNom());
        matiere.setCode(matiereDTO.getCode());
        matiere.setVolumeHoraire(matiereDTO.getVolumeHoraire());
        matiere.setProfesseurId(matiereDTO.getProfesseurId());
        matiere.setClasse(matiereDTO.getClasse());

        if (matiereDTO.getActif() != null) {
            matiere.setActif(matiereDTO.getActif());
        }

        Matiere updatedMatiere = matiereRepository.save(matiere);
        logger.info("Matière mise à jour avec succès: ID {}", id);

        return convertToDTO(updatedMatiere);
    }

    /**
     * Supprime une matière (désactivation logique)
     */
    public void deleteMatiere(Long id) {
        logger.info("Suppression de la matière ID: {}", id);

        Matiere matiere = matiereRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Matière", "id", id));

        matiere.setActif(false);
        matiereRepository.save(matiere);

        logger.info("Matière désactivée avec succès: ID {}", id);
    }

    /**
     * Incrémente le volume réalisé d'une matière
     */
    public void incrementerVolumeRealise(Long matiereId, double heures) {
        Matiere matiere = matiereRepository.findById(matiereId)
                .orElseThrow(() -> new ResourceNotFoundException("Matière", "id", matiereId));

        int nouvelleDuree = matiere.getVolumeRealise() + (int) Math.ceil(heures);
        matiere.setVolumeRealise(nouvelleDuree);
        matiereRepository.save(matiere);

        logger.info("Volume réalisé incrémenté pour matière ID {}: +{} heures", matiereId, heures);
    }

    /**
     * Convertit une entité Matiere en DTO
     */
    private MatiereDTO convertToDTO(Matiere matiere) {
        MatiereDTO dto = new MatiereDTO();
        dto.setId(matiere.getId());
        dto.setNom(matiere.getNom());
        dto.setCode(matiere.getCode());
        dto.setVolumeHoraire(matiere.getVolumeHoraire());
        dto.setVolumeRealise(matiere.getVolumeRealise());
        dto.setProfesseurId(matiere.getProfesseurId());
        dto.setClasse(matiere.getClasse());
        dto.setActif(matiere.getActif());
        dto.setDateCreation(matiere.getDateCreation());
        dto.setDateModification(matiere.getDateModification());

        // Champs calculés
        dto.setHeuresRestantes(matiere.getHeuresRestantes());
        dto.setEnAlerte(matiere.isEnAlerte());

        // TODO: Ajouter le nom du professeur en appelant le user-service via Feign
        // dto.setProfesseurNom("...");

        return dto;
    }
}
