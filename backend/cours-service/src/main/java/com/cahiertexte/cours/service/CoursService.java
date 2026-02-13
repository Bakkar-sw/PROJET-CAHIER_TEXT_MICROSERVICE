package com.cahiertexte.cours.service;

import com.cahiertexte.common.exception.ResourceNotFoundException;
import com.cahiertexte.cours.dto.CoursCreateDTO;
import com.cahiertexte.cours.dto.CoursDTO;
import com.cahiertexte.cours.dto.CoursUpdateDTO;
import com.cahiertexte.cours.model.Cours;
import com.cahiertexte.cours.model.Matiere;
import com.cahiertexte.cours.repository.CoursRepository;
import com.cahiertexte.cours.repository.MatiereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service métier pour la gestion des cours (README - Cours Service).
 *
 * @author Abdoulaye Guene
 */
@Service
@Transactional
public class CoursService {

    private static final List<String> STATUS_VALIDES = List.of("PLANIFIE", "VALIDE", "TERMINE", "ANNULE");

    @Autowired
    private CoursRepository coursRepository;

    @Autowired
    private MatiereRepository matiereRepository;

    public List<CoursDTO> findAll() {
        return coursRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CoursDTO findById(Long id) {
        Cours cours = coursRepository.findByIdWithMatiere(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cours non trouvé avec l'id : " + id));
        return toDTO(cours);
    }

    public List<CoursDTO> findByClasse(String classe) {
        return coursRepository.findByClasse(classe).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<CoursDTO> findByProfesseurId(Long profId) {
        return coursRepository.findByProfesseurId(profId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CoursDTO create(CoursCreateDTO dto) {
        Matiere matiere = matiereRepository.findById(dto.getMatiereId())
                .orElseThrow(() -> new ResourceNotFoundException("Matière non trouvée avec l'id : " + dto.getMatiereId()));

        Cours cours = new Cours();
        cours.setMatiereId(dto.getMatiereId());
        cours.setProfesseurId(dto.getProfesseurId());
        cours.setClasse(dto.getClasse());
        cours.setDateCours(dto.getDateCours());
        cours.setHeureDebut(dto.getHeureDebut());
        cours.setHeureFin(dto.getHeureFin());
        cours.setSalle(dto.getSalle());
        cours.setCahierTexte(dto.getCahierTexte());
        cours.setStatus("PLANIFIE");
        cours.setValideParProf(false);

        cours = coursRepository.save(cours);
        cours.setMatiere(matiere);
        return toDTO(cours);
    }

    public CoursDTO update(Long id, CoursUpdateDTO dto) {
        Cours cours = coursRepository.findByIdWithMatiere(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cours non trouvé avec l'id : " + id));

        if (dto.getMatiereId() != null) {
            matiereRepository.findById(dto.getMatiereId())
                    .orElseThrow(() -> new ResourceNotFoundException("Matière non trouvée avec l'id : " + dto.getMatiereId()));
            cours.setMatiereId(dto.getMatiereId());
        }
        if (dto.getProfesseurId() != null) cours.setProfesseurId(dto.getProfesseurId());
        if (dto.getClasse() != null) cours.setClasse(dto.getClasse());
        if (dto.getDateCours() != null) cours.setDateCours(dto.getDateCours());
        if (dto.getHeureDebut() != null) cours.setHeureDebut(dto.getHeureDebut());
        if (dto.getHeureFin() != null) cours.setHeureFin(dto.getHeureFin());
        if (dto.getSalle() != null) cours.setSalle(dto.getSalle());
        if (dto.getCahierTexte() != null) cours.setCahierTexte(dto.getCahierTexte());
        if (dto.getStatus() != null && STATUS_VALIDES.contains(dto.getStatus())) {
            cours.setStatus(dto.getStatus());
        }

        cours = coursRepository.save(cours);
        return toDTO(coursRepository.findByIdWithMatiere(cours.getId()).orElse(cours));
    }

    public CoursDTO valider(Long id) {
        Cours cours = coursRepository.findByIdWithMatiere(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cours non trouvé avec l'id : " + id));
        cours.setValideParProf(true);
        cours.setStatus("VALIDE");
        cours.setDateValidation(LocalDateTime.now());
        cours = coursRepository.save(cours);
        return toDTO(coursRepository.findByIdWithMatiere(cours.getId()).orElse(cours));
    }

    public void deleteById(Long id) {
        if (!coursRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cours non trouvé avec l'id : " + id);
        }
        coursRepository.deleteById(id);
    }

    private CoursDTO toDTO(Cours c) {
        CoursDTO dto = new CoursDTO();
        dto.setId(c.getId());
        dto.setMatiereId(c.getMatiereId());
        dto.setProfesseurId(c.getProfesseurId());
        dto.setClasse(c.getClasse());
        dto.setDateCours(c.getDateCours());
        dto.setHeureDebut(c.getHeureDebut());
        dto.setHeureFin(c.getHeureFin());
        dto.setSalle(c.getSalle());
        dto.setCahierTexte(c.getCahierTexte());
        dto.setStatus(c.getStatus());
        dto.setValideParProf(c.getValideParProf());
        dto.setDateValidation(c.getDateValidation());
        dto.setDateCreation(c.getDateCreation());
        dto.setDateModification(c.getDateModification());
        if (c.getMatiere() != null) {
            dto.setMatiereNom(c.getMatiere().getNom());
            dto.setMatiereCode(c.getMatiere().getCode());
        }
        return dto;
    }
}
