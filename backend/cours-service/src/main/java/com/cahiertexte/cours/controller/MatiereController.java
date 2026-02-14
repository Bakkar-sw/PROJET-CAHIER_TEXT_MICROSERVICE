package com.cahiertexte.cours.controller;

import com.cahiertexte.common.dto.ApiResponseDTO;
import com.cahiertexte.cours.dto.MatiereDTO;
import com.cahiertexte.cours.service.MatiereService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des matières
 * 
 * @author Abdoulaye Guene
 */
@RestController
@RequestMapping("/matieres")
@Tag(name = "Matières", description = "API de gestion des matières")
@CrossOrigin(origins = "${cors.allowed-origins}")
public class MatiereController {

    @Autowired
    private MatiereService matiereService;

    /**
     * Récupère toutes les matières
     */
    @GetMapping
    @Operation(summary = "Liste des matières", description = "Récupère toutes les matières")
    public ResponseEntity<ApiResponseDTO<List<MatiereDTO>>> getAllMatieres() {
        List<MatiereDTO> matieres = matiereService.getAllMatieres();
        return ResponseEntity.ok(ApiResponseDTO.success("Matières récupérées", matieres));
    }

    /**
     * Récupère une matière par son ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Détails d'une matière", description = "Récupère une matière par son ID")
    public ResponseEntity<ApiResponseDTO<MatiereDTO>> getMatiereById(@PathVariable Long id) {
        MatiereDTO matiere = matiereService.getMatiereById(id);
        return ResponseEntity.ok(ApiResponseDTO.success(matiere));
    }

    /**
     * Récupère les matières d'une classe
     */
    @GetMapping("/classe/{classe}")
    @Operation(summary = "Matières par classe", description = "Récupère toutes les matières d'une classe")
    public ResponseEntity<ApiResponseDTO<List<MatiereDTO>>> getMatieresByClasse(@PathVariable String classe) {
        List<MatiereDTO> matieres = matiereService.getMatieresByClasse(classe);
        return ResponseEntity.ok(ApiResponseDTO.success("Matières de la classe " + classe, matieres));
    }

    /**
     * Récupère les matières d'un professeur
     */
    @GetMapping("/professeur/{professeurId}")
    @Operation(summary = "Matières par professeur", description = "Récupère toutes les matières d'un professeur")
    public ResponseEntity<ApiResponseDTO<List<MatiereDTO>>> getMatieresByProfesseur(@PathVariable Long professeurId) {
        List<MatiereDTO> matieres = matiereService.getMatieresByProfesseur(professeurId);
        return ResponseEntity.ok(ApiResponseDTO.success("Matières du professeur", matieres));
    }

    /**
     * Récupère les matières en alerte (< 12h restantes)
     */
    @GetMapping("/alertes")
    @Operation(summary = "Matières en alerte", description = "Récupère les matières avec moins de 12h restantes")
    public ResponseEntity<ApiResponseDTO<List<MatiereDTO>>> getMatieresEnAlerte() {
        List<MatiereDTO> matieres = matiereService.getMatieresEnAlerte();
        return ResponseEntity.ok(ApiResponseDTO.success("Matières en alerte", matieres));
    }

    /**
     * Crée une nouvelle matière
     */
    @PostMapping
    @Operation(summary = "Créer une matière", description = "Crée une nouvelle matière")
    public ResponseEntity<ApiResponseDTO<MatiereDTO>> createMatiere(@Valid @RequestBody MatiereDTO matiereDTO) {
        MatiereDTO createdMatiere = matiereService.createMatiere(matiereDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success("Matière créée avec succès", createdMatiere));
    }

    /**
     * Met à jour une matière
     */
    @PutMapping("/{id}")
    @Operation(summary = "Modifier une matière", description = "Met à jour une matière existante")
    public ResponseEntity<ApiResponseDTO<MatiereDTO>> updateMatiere(
            @PathVariable Long id,
            @Valid @RequestBody MatiereDTO matiereDTO) {
        MatiereDTO updatedMatiere = matiereService.updateMatiere(id, matiereDTO);
        return ResponseEntity.ok(ApiResponseDTO.success("Matière modifiée avec succès", updatedMatiere));
    }

    /**
     * Supprime une matière (désactivation)
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une matière", description = "Désactive une matière")
    public ResponseEntity<ApiResponseDTO<Void>> deleteMatiere(@PathVariable Long id) {
        matiereService.deleteMatiere(id);
        return ResponseEntity.ok(ApiResponseDTO.success("Matière supprimée avec succès", null));
    }
}
