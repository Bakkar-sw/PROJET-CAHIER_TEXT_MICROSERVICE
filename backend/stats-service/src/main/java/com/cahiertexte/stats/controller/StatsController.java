package com.cahiertexte.stats.controller;

import com.cahiertexte.common.dto.ApiResponseDTO;
import com.cahiertexte.stats.dto.MatiereAlerteDTO;
import com.cahiertexte.stats.dto.StatClasseDTO;
import com.cahiertexte.stats.dto.StatEtudiantDTO;
import com.cahiertexte.stats.service.StatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Contrôleur REST pour les statistiques et reporting
 * 
 * @author Cheikh Tjian Diaw
 */
@RestController
@RequestMapping("/")
@Tag(name = "Statistiques", description = "API de gestion des statistiques et reporting")
@CrossOrigin(origins = "${cors.allowed-origins}")
public class StatsController {

    @Autowired
    private StatsService statsService;

    /**
     * Health check
     */
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Vérifie que le service est opérationnel")
    public ResponseEntity<ApiResponseDTO<String>> health() {
        return ResponseEntity.ok(ApiResponseDTO.success("Stats Service is running"));
    }

    /**
     * Récupère les statistiques d'un étudiant
     */
    @GetMapping("/etudiant/{etudiantId}")
    @Operation(summary = "Stats d'un étudiant", 
               description = "Récupère les statistiques de présence et justificatifs d'un étudiant")
    public ResponseEntity<ApiResponseDTO<StatEtudiantDTO>> getStatsEtudiant(
            @PathVariable Long etudiantId,
            @RequestHeader("Authorization") String authorization) {
        
        StatEtudiantDTO stats = statsService.getStatsEtudiant(etudiantId, authorization);
        return ResponseEntity.ok(ApiResponseDTO.success("Statistiques récupérées", stats));
    }

    /**
     * Récupère les statistiques d'une classe
     */
    @GetMapping("/classe/{classe}")
    @Operation(summary = "Stats d'une classe", 
               description = "Récupère les statistiques globales d'une classe (présences, top absences, alertes)")
    public ResponseEntity<ApiResponseDTO<StatClasseDTO>> getStatsClasse(
            @PathVariable String classe,
            @RequestHeader("Authorization") String authorization) {
        
        StatClasseDTO stats = statsService.getStatsClasse(classe, authorization);
        return ResponseEntity.ok(ApiResponseDTO.success("Statistiques de la classe récupérées", stats));
    }

    /**
     * Récupère les étudiants avec absences critiques (>= 3 absences)
     */
    @GetMapping("/absences/critiques")
    @Operation(summary = "Absences critiques", 
               description = "Liste des étudiants ayant 3 absences ou plus")
    public ResponseEntity<ApiResponseDTO<List<StatEtudiantDTO>>> getAbsencesCritiques(
            @RequestHeader("Authorization") String authorization) {
        
        List<StatEtudiantDTO> stats = statsService.getEtudiantsAvecAbsencesCritiques(authorization);
        return ResponseEntity.ok(ApiResponseDTO.success(
                "Liste des étudiants avec absences critiques", stats));
    }

    /**
     * Récupère les matières en alerte (< 12h restantes)
     */
    @GetMapping("/matieres/alertes")
    @Operation(summary = "Matières en alerte", 
               description = "Liste des matières avec moins de 12h de volume horaire restant")
    public ResponseEntity<ApiResponseDTO<List<MatiereAlerteDTO>>> getMatieresEnAlerte(
            @RequestHeader("Authorization") String authorization) {
        
        List<MatiereAlerteDTO> alertes = statsService.getMatieresEnAlerte(authorization);
        return ResponseEntity.ok(ApiResponseDTO.success(
                "Liste des matières en alerte", alertes));
    }

    /**
     * Dashboard global pour le responsable de formation
     */
    @GetMapping("/dashboard/formation")
    @Operation(summary = "Dashboard global", 
               description = "Vue d'ensemble de toutes les statistiques pour le responsable de formation")
    public ResponseEntity<ApiResponseDTO<Map<String, Object>>> getDashboardFormation(
            @RequestHeader("Authorization") String authorization) {
        
        Map<String, Object> dashboard = statsService.getDashboardGlobal(authorization);
        return ResponseEntity.ok(ApiResponseDTO.success("Dashboard récupéré", dashboard));
    }

    /**
     * Statistiques globales par classe
     */
    @GetMapping("/global/classes")
    @Operation(summary = "Stats de toutes les classes", 
               description = "Statistiques de toutes les classes")
    public ResponseEntity<ApiResponseDTO<Map<String, StatClasseDTO>>> getStatsAllClasses(
            @RequestHeader("Authorization") String authorization) {
        
        Map<String, StatClasseDTO> stats = Map.of(
            "CI_M1", statsService.getStatsClasse("CI_M1", authorization),
            "CI_M2", statsService.getStatsClasse("CI_M2", authorization),
            "MCS_M1", statsService.getStatsClasse("MCS_M1", authorization),
            "MCS_M2", statsService.getStatsClasse("MCS_M2", authorization)
        );
        
        return ResponseEntity.ok(ApiResponseDTO.success(
                "Statistiques de toutes les classes", stats));
    }

    /**
     * Taux de présence global de l'institut
     */
    @GetMapping("/taux-presence/global")
    @Operation(summary = "Taux de présence global", 
               description = "Taux de présence moyen de tout l'institut")
    public ResponseEntity<ApiResponseDTO<Map<String, Object>>> getTauxPresenceGlobal(
            @RequestHeader("Authorization") String authorization) {
        
        Map<String, StatClasseDTO> statsClasses = Map.of(
            "CI_M1", statsService.getStatsClasse("CI_M1", authorization),
            "CI_M2", statsService.getStatsClasse("CI_M2", authorization),
            "MCS_M1", statsService.getStatsClasse("MCS_M1", authorization),
            "MCS_M2", statsService.getStatsClasse("MCS_M2", authorization)
        );

        double sommeTaux = statsClasses.values().stream()
                .filter(s -> s.getTauxPresenceMoyen() != null)
                .mapToDouble(StatClasseDTO::getTauxPresenceMoyen)
                .sum();
        
        long nbClasses = statsClasses.values().stream()
                .filter(s -> s.getTauxPresenceMoyen() != null)
                .count();

        double tauxGlobal = nbClasses > 0 ? Math.round((sommeTaux / nbClasses) * 100.0) / 100.0 : 0.0;

        Map<String, Object> result = Map.of(
            "tauxPresenceGlobal", tauxGlobal,
            "nombreClasses", nbClasses,
            "detailParClasse", statsClasses
        );

        return ResponseEntity.ok(ApiResponseDTO.success("Taux de présence global", result));
    }
}
