package com.cahiertexte.cours.controller;

import com.cahiertexte.common.dto.ApiResponseDTO;
import com.cahiertexte.cours.dto.CoursDTO;
import com.cahiertexte.cours.service.CoursService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Contrôleur REST pour la gestion des cours
 * 
 * @author Abdoulaye Guene
 */
@RestController
@RequestMapping("/")
@Tag(name = "Cours", description = "API de gestion des cours")
@CrossOrigin(origins = "${cors.allowed-origins}")
public class CoursController {

    @Autowired
    private CoursService coursService;

    /**
     * Health check
     */
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Vérifie que le service est opérationnel")
    public ResponseEntity<ApiResponseDTO<String>> health() {
        return ResponseEntity.ok(ApiResponseDTO.success("Cours Service is running"));
    }

    /**
     * Récupère tous les cours
     */
    @GetMapping
    @Operation(summary = "Liste des cours", description = "Récupère tous les cours")
    public ResponseEntity<ApiResponseDTO<List<CoursDTO>>> getAllCours() {
        List<CoursDTO> cours = coursService.getAllCours();
        return ResponseEntity.ok(ApiResponseDTO.success("Cours récupérés", cours));
    }

    /**
     * Récupère un cours par son ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Détails d'un cours", description = "Récupère un cours par son ID")
    public ResponseEntity<ApiResponseDTO<CoursDTO>> getCoursById(@PathVariable Long id) {
        CoursDTO cours = coursService.getCoursById(id);
        return ResponseEntity.ok(ApiResponseDTO.success(cours));
    }

    /**
     * Récupère les cours d'une classe
     */
    @GetMapping("/classe/{classe}")
    @Operation(summary = "Cours par classe", description = "Récupère tous les cours d'une classe")
    public ResponseEntity<ApiResponseDTO<List<CoursDTO>>> getCoursByClasse(@PathVariable String classe) {
        List<CoursDTO> cours = coursService.getCoursByClasse(classe);
        return ResponseEntity.ok(ApiResponseDTO.success("Cours de la classe " + classe, cours));
    }

    /**
     * Récupère les cours d'un professeur
     */
    @GetMapping("/professeur/{professeurId}")
    @Operation(summary = "Cours par professeur", description = "Récupère tous les cours d'un professeur")
    public ResponseEntity<ApiResponseDTO<List<CoursDTO>>> getCoursByProfesseur(@PathVariable Long professeurId) {
        List<CoursDTO> cours = coursService.getCoursByProfesseur(professeurId);
        return ResponseEntity.ok(ApiResponseDTO.success("Cours du professeur", cours));
    }

    /**
     * Récupère les cours d'une matière
     */
    @GetMapping("/matiere/{matiereId}")
    @Operation(summary = "Cours par matière", description = "Récupère tous les cours d'une matière")
    public ResponseEntity<ApiResponseDTO<List<CoursDTO>>> getCoursByMatiere(@PathVariable Long matiereId) {
        List<CoursDTO> cours = coursService.getCoursByMatiere(matiereId);
        return ResponseEntity.ok(ApiResponseDTO.success("Cours de la matière", cours));
    }

    /**
     * Récupère les cours par status
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "Cours par status", description = "Récupère les cours par status (PLANIFIE, VALIDE, etc.)")
    public ResponseEntity<ApiResponseDTO<List<CoursDTO>>> getCoursByStatus(@PathVariable String status) {
        List<CoursDTO> cours = coursService.getCoursByStatus(status);
        return ResponseEntity.ok(ApiResponseDTO.success("Cours avec status " + status, cours));
    }

    /**
     * Crée un nouveau cours (planification)
     */
    @PostMapping
    @Operation(summary = "Planifier un cours", description = "Crée un nouveau cours")
    public ResponseEntity<ApiResponseDTO<CoursDTO>> createCours(@Valid @RequestBody CoursDTO coursDTO) {
        CoursDTO createdCours = coursService.createCours(coursDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success("Cours planifié avec succès", createdCours));
    }

    /**
     * Met à jour un cours
     */
    @PutMapping("/{id}")
    @Operation(summary = "Modifier un cours", description = "Met à jour un cours existant")
    public ResponseEntity<ApiResponseDTO<CoursDTO>> updateCours(
            @PathVariable Long id,
            @Valid @RequestBody CoursDTO coursDTO) {
        CoursDTO updatedCours = coursService.updateCours(id, coursDTO);
        return ResponseEntity.ok(ApiResponseDTO.success("Cours modifié avec succès", updatedCours));
    }

    /**
     * Saisie du cahier de texte
     */
    @PutMapping("/{id}/cahier-texte")
    @Operation(summary = "Saisir le cahier de texte", description = "Ajoute ou modifie le contenu pédagogique d'un cours")
    public ResponseEntity<ApiResponseDTO<CoursDTO>> saisirCahierTexte(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String cahierTexte = body.get("cahierTexte");
        CoursDTO updatedCours = coursService.saisirCahierTexte(id, cahierTexte);
        return ResponseEntity.ok(ApiResponseDTO.success("Cahier de texte saisi avec succès", updatedCours));
    }

    /**
     * Valider un cours (par le professeur)
     */
    @PutMapping("/{id}/valider")
    @Operation(summary = "Valider un cours", description = "Le professeur valide que le cours a bien eu lieu")
    public ResponseEntity<ApiResponseDTO<CoursDTO>> validerCours(@PathVariable Long id) {
        CoursDTO validatedCours = coursService.validerCours(id);
        return ResponseEntity.ok(ApiResponseDTO.success("Cours validé avec succès", validatedCours));
    }

    /**
     * Annuler un cours
     */
    @PutMapping("/{id}/annuler")
    @Operation(summary = "Annuler un cours", description = "Annule un cours planifié")
    public ResponseEntity<ApiResponseDTO<CoursDTO>> annulerCours(@PathVariable Long id) {
        CoursDTO cancelledCours = coursService.annulerCours(id);
        return ResponseEntity.ok(ApiResponseDTO.success("Cours annulé avec succès", cancelledCours));
    }

    /**
     * Supprime un cours
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un cours", description = "Supprime définitivement un cours")
    public ResponseEntity<ApiResponseDTO<Void>> deleteCours(@PathVariable Long id) {
        coursService.deleteCours(id);
        return ResponseEntity.ok(ApiResponseDTO.success("Cours supprimé avec succès", null));
    }
}
