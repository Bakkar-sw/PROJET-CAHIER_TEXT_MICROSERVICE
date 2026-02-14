package com.cahiertexte.presence.controller;

import com.cahiertexte.common.dto.ApiResponseDTO;
import com.cahiertexte.presence.dto.PresenceBatchDTO;
import com.cahiertexte.presence.dto.PresenceDTO;
import com.cahiertexte.presence.service.PresenceService;
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
 * Contrôleur REST pour la gestion des présences
 * 
 * @author Alioune Kebe
 */
@RestController
@RequestMapping("/")
@Tag(name = "Présences", description = "API de gestion des présences et absences")
@CrossOrigin(origins = "${cors.allowed-origins}")
public class PresenceController {

    @Autowired
    private PresenceService presenceService;

    /**
     * Health check
     */
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Vérifie que le service est opérationnel")
    public ResponseEntity<ApiResponseDTO<String>> health() {
        return ResponseEntity.ok(ApiResponseDTO.success("Presence Service is running"));
    }

    /**
     * Récupère toutes les présences
     */
    @GetMapping
    @Operation(summary = "Liste des présences", description = "Récupère toutes les présences")
    public ResponseEntity<ApiResponseDTO<List<PresenceDTO>>> getAllPresences() {
        List<PresenceDTO> presences = presenceService.getAllPresences();
        return ResponseEntity.ok(ApiResponseDTO.success("Présences récupérées", presences));
    }

    /**
     * Récupère une présence par son ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Détails d'une présence", description = "Récupère une présence par son ID")
    public ResponseEntity<ApiResponseDTO<PresenceDTO>> getPresenceById(@PathVariable Long id) {
        PresenceDTO presence = presenceService.getPresenceById(id);
        return ResponseEntity.ok(ApiResponseDTO.success(presence));
    }

    /**
     * Récupère les présences d'un cours
     */
    @GetMapping("/cours/{coursId}")
    @Operation(summary = "Présences par cours", 
               description = "Récupère toutes les présences d'un cours (liste émargement)")
    public ResponseEntity<ApiResponseDTO<List<PresenceDTO>>> getPresencesByCours(@PathVariable Long coursId) {
        List<PresenceDTO> presences = presenceService.getPresencesByCours(coursId);
        return ResponseEntity.ok(ApiResponseDTO.success("Présences du cours", presences));
    }

    /**
     * Récupère les présences d'un étudiant
     */
    @GetMapping("/etudiant/{etudiantId}")
    @Operation(summary = "Présences par étudiant", 
               description = "Récupère toutes les présences d'un étudiant")
    public ResponseEntity<ApiResponseDTO<List<PresenceDTO>>> getPresencesByEtudiant(@PathVariable Long etudiantId) {
        List<PresenceDTO> presences = presenceService.getPresencesByEtudiant(etudiantId);
        return ResponseEntity.ok(ApiResponseDTO.success("Présences de l'étudiant", presences));
    }

    /**
     * Récupère les présences par status
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "Présences par status", 
               description = "Récupère les présences par status (PRESENT, ABSENT, RETARD)")
    public ResponseEntity<ApiResponseDTO<List<PresenceDTO>>> getPresencesByStatus(@PathVariable String status) {
        List<PresenceDTO> presences = presenceService.getPresencesByStatus(status);
        return ResponseEntity.ok(ApiResponseDTO.success("Présences avec status " + status, presences));
    }

    /**
     * Enregistre une présence unique
     */
    @PostMapping
    @Operation(summary = "Enregistrer une présence", 
               description = "Enregistre la présence/absence d'un étudiant à un cours")
    public ResponseEntity<ApiResponseDTO<PresenceDTO>> createPresence(@Valid @RequestBody PresenceDTO presenceDTO) {
        PresenceDTO createdPresence = presenceService.createPresence(presenceDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success("Présence enregistrée", createdPresence));
    }

    /**
     * Enregistre les présences en batch (tout un cours)
     */
    @PostMapping("/batch")
    @Operation(summary = "Enregistrement en batch", 
               description = "Enregistre les présences de tous les étudiants d'un cours")
    public ResponseEntity<ApiResponseDTO<List<PresenceDTO>>> createPresencesBatch(
            @Valid @RequestBody PresenceBatchDTO batchDTO) {
        List<PresenceDTO> createdPresences = presenceService.createPresencesBatch(batchDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success(
                        createdPresences.size() + " présences enregistrées", createdPresences));
    }

    /**
     * Met à jour une présence
     */
    @PutMapping("/{id}")
    @Operation(summary = "Modifier une présence", 
               description = "Met à jour le status ou la remarque d'une présence")
    public ResponseEntity<ApiResponseDTO<PresenceDTO>> updatePresence(
            @PathVariable Long id,
            @Valid @RequestBody PresenceDTO presenceDTO) {
        PresenceDTO updatedPresence = presenceService.updatePresence(id, presenceDTO);
        return ResponseEntity.ok(ApiResponseDTO.success("Présence modifiée", updatedPresence));
    }

    /**
     * Supprime une présence
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une présence", description = "Supprime une présence")
    public ResponseEntity<ApiResponseDTO<Void>> deletePresence(@PathVariable Long id) {
        presenceService.deletePresence(id);
        return ResponseEntity.ok(ApiResponseDTO.success("Présence supprimée", null));
    }

    /**
     * Supprime toutes les présences d'un cours
     */
    @DeleteMapping("/cours/{coursId}")
    @Operation(summary = "Supprimer les présences d'un cours", 
               description = "Supprime toutes les présences d'un cours (réinitialisation)")
    public ResponseEntity<ApiResponseDTO<Void>> deletePresencesByCours(@PathVariable Long coursId) {
        presenceService.deletePresencesByCours(coursId);
        return ResponseEntity.ok(ApiResponseDTO.success("Présences du cours supprimées", null));
    }

    /**
     * Statistiques de présence d'un étudiant
     */
    @GetMapping("/stats/etudiant/{etudiantId}")
    @Operation(summary = "Stats d'un étudiant", 
               description = "Récupère les statistiques de présence d'un étudiant")
    public ResponseEntity<ApiResponseDTO<Map<String, Object>>> getStatsEtudiant(@PathVariable Long etudiantId) {
        Map<String, Object> stats = presenceService.getStatsEtudiant(etudiantId);
        return ResponseEntity.ok(ApiResponseDTO.success("Statistiques récupérées", stats));
    }
}
