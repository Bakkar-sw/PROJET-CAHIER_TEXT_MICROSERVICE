package com.cahiertexte.cours.controller;

import com.cahiertexte.common.dto.ApiResponseDTO;
import com.cahiertexte.cours.dto.CoursCreateDTO;
import com.cahiertexte.cours.dto.CoursDTO;
import com.cahiertexte.cours.dto.CoursUpdateDTO;
import com.cahiertexte.cours.service.CoursService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST Cours Service - README : GET/POST/PUT/DELETE /cours, valider.
 * Context-path: /api/cours (port 8083)
 *
 * @author Abdoulaye Guene
 */
@RestController
@RequestMapping("/")
@Tag(name = "Cours Service", description = "API de gestion des cours - Abdoulaye Guene")
@CrossOrigin(origins = "${cors.allowed-origins}")
public class CoursController {

    @Autowired
    private CoursService coursService;

    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Vérifie que le service est opérationnel")
    public ResponseEntity<ApiResponseDTO<String>> health() {
        return ResponseEntity.ok(ApiResponseDTO.success("Cours Service is running"));
    }

    @GetMapping("/cours")
    @Operation(summary = "Liste des cours", description = "Retourne tous les cours")
    public ResponseEntity<ApiResponseDTO<List<CoursDTO>>> getAllCours() {
        List<CoursDTO> list = coursService.findAll();
        return ResponseEntity.ok(ApiResponseDTO.success(list));
    }

    @GetMapping("/cours/{id}")
    @Operation(summary = "Détails d'un cours", description = "Retourne un cours par son ID")
    public ResponseEntity<ApiResponseDTO<CoursDTO>> getCoursById(@PathVariable Long id) {
        CoursDTO dto = coursService.findById(id);
        return ResponseEntity.ok(ApiResponseDTO.success(dto));
    }

    @GetMapping("/cours/classe/{classe}")
    @Operation(summary = "Cours par classe", description = "Retourne les cours d'une classe donnée")
    public ResponseEntity<ApiResponseDTO<List<CoursDTO>>> getCoursByClasse(@PathVariable String classe) {
        List<CoursDTO> list = coursService.findByClasse(classe);
        return ResponseEntity.ok(ApiResponseDTO.success(list));
    }

    @GetMapping("/cours/professeur/{profId}")
    @Operation(summary = "Cours par professeur", description = "Retourne les cours d'un professeur donné")
    public ResponseEntity<ApiResponseDTO<List<CoursDTO>>> getCoursByProfesseur(@PathVariable Long profId) {
        List<CoursDTO> list = coursService.findByProfesseurId(profId);
        return ResponseEntity.ok(ApiResponseDTO.success(list));
    }

    @PostMapping("/cours")
    @Operation(summary = "Planifier un cours", description = "Crée un nouveau cours")
    public ResponseEntity<ApiResponseDTO<CoursDTO>> createCours(@Valid @RequestBody CoursCreateDTO dto) {
        CoursDTO created = coursService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success("Cours planifié avec succès", created));
    }

    @PutMapping("/cours/{id}")
    @Operation(summary = "Modifier un cours", description = "Met à jour un cours existant")
    public ResponseEntity<ApiResponseDTO<CoursDTO>> updateCours(
            @PathVariable Long id,
            @Valid @RequestBody CoursUpdateDTO dto) {
        CoursDTO updated = coursService.update(id, dto);
        return ResponseEntity.ok(ApiResponseDTO.success("Cours modifié avec succès", updated));
    }

    @DeleteMapping("/cours/{id}")
    @Operation(summary = "Annuler / Supprimer un cours", description = "Supprime un cours")
    public ResponseEntity<ApiResponseDTO<Void>> deleteCours(@PathVariable Long id) {
        coursService.deleteById(id);
        return ResponseEntity.ok(ApiResponseDTO.success("Cours supprimé", null));
    }

    @PutMapping("/cours/{id}/valider")
    @Operation(summary = "Valider un cours", description = "Marque le cours comme validé par le professeur")
    public ResponseEntity<ApiResponseDTO<CoursDTO>> validerCours(@PathVariable Long id) {
        CoursDTO dto = coursService.valider(id);
        return ResponseEntity.ok(ApiResponseDTO.success("Cours validé", dto));
    }
}
