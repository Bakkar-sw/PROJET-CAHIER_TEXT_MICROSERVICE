package com.cahiertexte.justificatif.controller;

import com.cahiertexte.common.dto.ApiResponseDTO;
import com.cahiertexte.justificatif.dto.JustificatifDTO;
import com.cahiertexte.justificatif.service.JustificatifService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * Contrôleur REST pour la gestion des justificatifs
 * 
 * @author Fatou Leye
 */
@RestController
@RequestMapping("/")
@Tag(name = "Justificatifs", description = "API de gestion des justificatifs d'absence")
@CrossOrigin(origins = "${cors.allowed-origins}")
public class JustificatifController {

    @Autowired
    private JustificatifService justificatifService;

    /**
     * Health check
     */
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Vérifie que le service est opérationnel")
    public ResponseEntity<ApiResponseDTO<String>> health() {
        return ResponseEntity.ok(ApiResponseDTO.success("Justificatif Service is running"));
    }

    /**
     * Récupère tous les justificatifs
     */
    @GetMapping
    @Operation(summary = "Liste des justificatifs", description = "Récupère tous les justificatifs")
    public ResponseEntity<ApiResponseDTO<List<JustificatifDTO>>> getAllJustificatifs() {
        List<JustificatifDTO> justificatifs = justificatifService.getAllJustificatifs();
        return ResponseEntity.ok(ApiResponseDTO.success("Justificatifs récupérés", justificatifs));
    }

    /**
     * Récupère un justificatif par son ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Détails d'un justificatif", description = "Récupère un justificatif par son ID")
    public ResponseEntity<ApiResponseDTO<JustificatifDTO>> getJustificatifById(@PathVariable Long id) {
        JustificatifDTO justificatif = justificatifService.getJustificatifById(id);
        return ResponseEntity.ok(ApiResponseDTO.success(justificatif));
    }

    /**
     * Récupère les justificatifs d'un étudiant
     */
    @GetMapping("/etudiant/{etudiantId}")
    @Operation(summary = "Justificatifs par étudiant", 
               description = "Récupère tous les justificatifs d'un étudiant")
    public ResponseEntity<ApiResponseDTO<List<JustificatifDTO>>> getJustificatifsByEtudiant(
            @PathVariable Long etudiantId) {
        List<JustificatifDTO> justificatifs = justificatifService.getJustificatifsByEtudiant(etudiantId);
        return ResponseEntity.ok(ApiResponseDTO.success("Justificatifs de l'étudiant", justificatifs));
    }

    /**
     * Récupère les justificatifs d'un cours
     */
    @GetMapping("/cours/{coursId}")
    @Operation(summary = "Justificatifs par cours", 
               description = "Récupère tous les justificatifs liés à un cours")
    public ResponseEntity<ApiResponseDTO<List<JustificatifDTO>>> getJustificatifsByCours(
            @PathVariable Long coursId) {
        List<JustificatifDTO> justificatifs = justificatifService.getJustificatifsByCours(coursId);
        return ResponseEntity.ok(ApiResponseDTO.success("Justificatifs du cours", justificatifs));
    }

    /**
     * Récupère les justificatifs par status
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "Justificatifs par status", 
               description = "Récupère les justificatifs par status (EN_ATTENTE, ACCEPTE, REFUSE)")
    public ResponseEntity<ApiResponseDTO<List<JustificatifDTO>>> getJustificatifsByStatus(
            @PathVariable String status) {
        List<JustificatifDTO> justificatifs = justificatifService.getJustificatifsByStatus(status);
        return ResponseEntity.ok(ApiResponseDTO.success("Justificatifs avec status " + status, justificatifs));
    }

    /**
     * Récupère les justificatifs en attente
     */
    @GetMapping("/en-attente")
    @Operation(summary = "Justificatifs en attente", 
               description = "Récupère tous les justificatifs en attente de traitement (triés par date)")
    public ResponseEntity<ApiResponseDTO<List<JustificatifDTO>>> getJustificatifsEnAttente() {
        List<JustificatifDTO> justificatifs = justificatifService.getJustificatifsEnAttente();
        return ResponseEntity.ok(ApiResponseDTO.success("Justificatifs en attente", justificatifs));
    }

    /**
     * Soumet un justificatif (avec upload de fichier optionnel)
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Soumettre un justificatif", 
               description = "Soumet un justificatif d'absence (avec fichier optionnel)")
    public ResponseEntity<ApiResponseDTO<JustificatifDTO>> createJustificatif(
            @RequestParam("etudiantId") Long etudiantId,
            @RequestParam("coursId") Long coursId,
            @RequestParam("motif") String motif,
            @RequestParam(value = "fichier", required = false) MultipartFile fichier) {
        
        JustificatifDTO justificatifDTO = new JustificatifDTO();
        justificatifDTO.setEtudiantId(etudiantId);
        justificatifDTO.setCoursId(coursId);
        justificatifDTO.setMotif(motif);

        JustificatifDTO created = justificatifService.createJustificatif(justificatifDTO, fichier);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success("Justificatif soumis avec succès", created));
    }

    /**
     * Valide un justificatif
     */
    @PutMapping("/{id}/valider")
    @Operation(summary = "Valider un justificatif", 
               description = "Accepte un justificatif (responsable uniquement)")
    public ResponseEntity<ApiResponseDTO<JustificatifDTO>> validerJustificatif(
            @PathVariable Long id,
            @RequestBody Map<String, Object> body) {
        
        Long responsableId = ((Number) body.get("responsableId")).longValue();
        String commentaire = (String) body.get("commentaire");
        
        JustificatifDTO validated = justificatifService.validerJustificatif(id, responsableId, commentaire);
        return ResponseEntity.ok(ApiResponseDTO.success("Justificatif validé", validated));
    }

    /**
     * Refuse un justificatif
     */
    @PutMapping("/{id}/refuser")
    @Operation(summary = "Refuser un justificatif", 
               description = "Refuse un justificatif avec commentaire obligatoire (responsable uniquement)")
    public ResponseEntity<ApiResponseDTO<JustificatifDTO>> refuserJustificatif(
            @PathVariable Long id,
            @RequestBody Map<String, Object> body) {
        
        Long responsableId = ((Number) body.get("responsableId")).longValue();
        String commentaire = (String) body.get("commentaire");
        
        JustificatifDTO refused = justificatifService.refuserJustificatif(id, responsableId, commentaire);
        return ResponseEntity.ok(ApiResponseDTO.success("Justificatif refusé", refused));
    }

    /**
     * Supprime un justificatif
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un justificatif", description = "Supprime un justificatif")
    public ResponseEntity<ApiResponseDTO<Void>> deleteJustificatif(@PathVariable Long id) {
        justificatifService.deleteJustificatif(id);
        return ResponseEntity.ok(ApiResponseDTO.success("Justificatif supprimé", null));
    }

    /**
     * Statistiques des justificatifs d'un étudiant
     */
    @GetMapping("/stats/etudiant/{etudiantId}")
    @Operation(summary = "Stats d'un étudiant", 
               description = "Récupère les statistiques des justificatifs d'un étudiant")
    public ResponseEntity<ApiResponseDTO<Map<String, Object>>> getStatsEtudiant(
            @PathVariable Long etudiantId) {
        Map<String, Object> stats = justificatifService.getStatsEtudiant(etudiantId);
        return ResponseEntity.ok(ApiResponseDTO.success("Statistiques récupérées", stats));
    }
}
