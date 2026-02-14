package com.cahiertexte.cours.service;

import com.cahiertexte.common.constants.AppConstants;
import com.cahiertexte.common.exception.BadRequestException;
import com.cahiertexte.common.exception.ResourceNotFoundException;
import com.cahiertexte.cours.dto.CoursDTO;
import com.cahiertexte.cours.model.Cours;
import com.cahiertexte.cours.model.Matiere;
import com.cahiertexte.cours.repository.CoursRepository;
import com.cahiertexte.cours.repository.MatiereRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service de gestion des cours
 * 
 * @author Abdoulaye Guene
 */
@Service
@Transactional
public class CoursService {

    private static final Logger logger = LoggerFactory.getLogger(CoursService.class);

    @Autowired
    private CoursRepository coursRepository;

    @Autowired
    private MatiereRepository matiereRepository;

    @Autowired
    private MatiereService matiereService;

    /**
     * Récupère tous les cours
     */
    public List<CoursDTO> getAllCours() {
        return coursRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère un cours par son ID
     */
    public CoursDTO getCoursById(Long id) {
        Cours cours = coursRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cours", "id", id));
        return convertToDTO(cours);
    }

    /**
     * Récupère les cours d'une classe
     */
    public List<CoursDTO> getCoursByClasse(String classe) {
        return coursRepository.findByClasse(classe).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère les cours d'un professeur
     */
    public List<CoursDTO> getCoursByProfesseur(Long professeurId) {
        return coursRepository.findByProfesseurId(professeurId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère les cours d'une matière
     */
    public List<CoursDTO> getCoursByMatiere(Long matiereId) {
        return coursRepository.findByMatiereId(matiereId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère les cours par status
     */
    public List<CoursDTO> getCoursByStatus(String status) {
        return coursRepository.findByStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Crée un nouveau cours (planification)
     */
    public CoursDTO createCours(CoursDTO coursDTO) {
        logger.info("Planification d'un nouveau cours pour la classe: {}", coursDTO.getClasse());

        // Vérifier que la matière existe
        Matiere matiere = matiereRepository.findById(coursDTO.getMatiereId())
                .orElseThrow(() -> new ResourceNotFoundException("Matière", "id", coursDTO.getMatiereId()));

        // Vérifier que le cours n'est pas dans le passé
        if (coursDTO.getDateCours().isBefore(LocalDate.now())) {
            throw new BadRequestException("Impossible de planifier un cours dans le passé");
        }

        // Vérifier que l'heure de fin est après l'heure de début
        if (coursDTO.getHeureFin().isBefore(coursDTO.getHeureDebut()) || 
            coursDTO.getHeureFin().equals(coursDTO.getHeureDebut())) {
            throw new BadRequestException("L'heure de fin doit être après l'heure de début");
        }

        Cours cours = new Cours();
        cours.setMatiereId(coursDTO.getMatiereId());
        cours.setProfesseurId(coursDTO.getProfesseurId());
        cours.setClasse(coursDTO.getClasse());
        cours.setDateCours(coursDTO.getDateCours());
        cours.setHeureDebut(coursDTO.getHeureDebut());
        cours.setHeureFin(coursDTO.getHeureFin());
        cours.setSalle(coursDTO.getSalle());
        cours.setStatus(AppConstants.Status.PLANIFIE);
        cours.setValideParProf(false);

        Cours savedCours = coursRepository.save(cours);
        logger.info("Cours planifié avec succès: ID {}", savedCours.getId());

        return convertToDTO(savedCours);
    }

    /**
     * Met à jour un cours
     */
    public CoursDTO updateCours(Long id, CoursDTO coursDTO) {
        logger.info("Mise à jour du cours ID: {}", id);

        Cours cours = coursRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cours", "id", id));

        // Ne pas modifier un cours déjà validé
        if (cours.getValideParProf()) {
            throw new BadRequestException("Impossible de modifier un cours déjà validé");
        }

        cours.setMatiereId(coursDTO.getMatiereId());
        cours.setProfesseurId(coursDTO.getProfesseurId());
        cours.setClasse(coursDTO.getClasse());
        cours.setDateCours(coursDTO.getDateCours());
        cours.setHeureDebut(coursDTO.getHeureDebut());
        cours.setHeureFin(coursDTO.getHeureFin());
        cours.setSalle(coursDTO.getSalle());

        Cours updatedCours = coursRepository.save(cours);
        logger.info("Cours mis à jour avec succès: ID {}", id);

        return convertToDTO(updatedCours);
    }

    /**
     * Saisie du cahier de texte
     */
    public CoursDTO saisirCahierTexte(Long id, String cahierTexte) {
        logger.info("Saisie du cahier de texte pour le cours ID: {}", id);

        Cours cours = coursRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cours", "id", id));

        cours.setCahierTexte(cahierTexte);
        Cours updatedCours = coursRepository.save(cours);

        logger.info("Cahier de texte saisi pour le cours ID: {}", id);
        return convertToDTO(updatedCours);
    }

    /**
     * Valider un cours (par le professeur)
     */
    public CoursDTO validerCours(Long id) {
        logger.info("Validation du cours ID: {}", id);

        Cours cours = coursRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cours", "id", id));

        if (cours.getValideParProf()) {
            throw new BadRequestException("Ce cours est déjà validé");
        }

        // Vérifier que le cahier de texte est renseigné
        if (cours.getCahierTexte() == null || cours.getCahierTexte().trim().isEmpty()) {
            throw new BadRequestException("Le cahier de texte doit être renseigné avant validation");
        }

        cours.setValideParProf(true);
        cours.setStatus(AppConstants.Status.VALIDE);
        cours.setDateValidation(LocalDateTime.now());

        // Incrémenter le volume réalisé de la matière
        double duree = cours.getDureeEnHeures();
        matiereService.incrementerVolumeRealise(cours.getMatiereId(), duree);

        Cours validatedCours = coursRepository.save(cours);
        logger.info("Cours validé avec succès: ID {}", id);

        return convertToDTO(validatedCours);
    }

    /**
     * Annuler un cours
     */
    public CoursDTO annulerCours(Long id) {
        logger.info("Annulation du cours ID: {}", id);

        Cours cours = coursRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cours", "id", id));

        if (cours.getValideParProf()) {
            throw new BadRequestException("Impossible d'annuler un cours déjà validé");
        }

        cours.setStatus(AppConstants.Status.ANNULE);
        Cours cancelledCours = coursRepository.save(cours);

        logger.info("Cours annulé avec succès: ID {}", id);
        return convertToDTO(cancelledCours);
    }

    /**
     * Supprime un cours
     */
    public void deleteCours(Long id) {
        logger.info("Suppression du cours ID: {}", id);

        Cours cours = coursRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cours", "id", id));

        if (cours.getValideParProf()) {
            throw new BadRequestException("Impossible de supprimer un cours validé");
        }

        coursRepository.delete(cours);
        logger.info("Cours supprimé avec succès: ID {}", id);
    }

    /**
     * Convertit une entité Cours en DTO
     */
    private CoursDTO convertToDTO(Cours cours) {
        CoursDTO dto = new CoursDTO();
        dto.setId(cours.getId());
        dto.setMatiereId(cours.getMatiereId());
        dto.setProfesseurId(cours.getProfesseurId());
        dto.setClasse(cours.getClasse());
        dto.setDateCours(cours.getDateCours());
        dto.setHeureDebut(cours.getHeureDebut());
        dto.setHeureFin(cours.getHeureFin());
        dto.setSalle(cours.getSalle());
        dto.setCahierTexte(cours.getCahierTexte());
        dto.setStatus(cours.getStatus());
        dto.setValideParProf(cours.getValideParProf());
        dto.setDateValidation(cours.getDateValidation());
        dto.setDateCreation(cours.getDateCreation());
        dto.setDateModification(cours.getDateModification());
        dto.setDureeEnHeures(cours.getDureeEnHeures());

        // TODO: Ajouter les noms via Feign
        // dto.setMatiereNom("...");
        // dto.setProfesseurNom("...");

        return dto;
    }
}
