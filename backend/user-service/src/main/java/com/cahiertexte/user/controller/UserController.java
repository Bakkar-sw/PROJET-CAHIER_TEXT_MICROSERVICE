package com.cahiertexte.user.controller;

import com.cahiertexte.common.dto.ApiResponseDTO;
import com.cahiertexte.common.dto.UserDTO;
import com.cahiertexte.user.service.UserService;
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
 * Contrôleur REST pour la gestion des utilisateurs
 * 
 * @author Ousmane Deme
 */
@RestController
@RequestMapping("/")
@Tag(name = "Utilisateurs", description = "API de gestion des utilisateurs")
@CrossOrigin(origins = "${cors.allowed-origins}")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Health check
     */
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Vérifie que le service est opérationnel")
    public ResponseEntity<ApiResponseDTO<String>> health() {
        return ResponseEntity.ok(ApiResponseDTO.success("User Service is running"));
    }

    /**
     * Récupère tous les utilisateurs
     */
    @GetMapping
    @Operation(summary = "Liste des utilisateurs", description = "Récupère tous les utilisateurs")
    public ResponseEntity<ApiResponseDTO<List<UserDTO>>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponseDTO.success("Utilisateurs récupérés", users));
    }

    /**
     * Récupère un utilisateur par son ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Détails d'un utilisateur", description = "Récupère un utilisateur par son ID")
    public ResponseEntity<ApiResponseDTO<UserDTO>> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponseDTO.success(user));
    }

    /**
     * Récupère un utilisateur par son username
     */
    @GetMapping("/username/{username}")
    @Operation(summary = "Utilisateur par username", description = "Récupère un utilisateur par son nom d'utilisateur")
    public ResponseEntity<ApiResponseDTO<UserDTO>> getUserByUsername(@PathVariable String username) {
        UserDTO user = userService.getUserByUsername(username);
        return ResponseEntity.ok(ApiResponseDTO.success(user));
    }

    /**
     * Récupère les utilisateurs par rôle
     */
    @GetMapping("/role/{role}")
    @Operation(summary = "Utilisateurs par rôle", description = "Récupère tous les utilisateurs d'un rôle spécifique")
    public ResponseEntity<ApiResponseDTO<List<UserDTO>>> getUsersByRole(@PathVariable String role) {
        List<UserDTO> users = userService.getUsersByRole(role);
        return ResponseEntity.ok(ApiResponseDTO.success("Utilisateurs avec rôle " + role, users));
    }

    /**
     * Récupère les utilisateurs d'une classe
     */
    @GetMapping("/classe/{classe}")
    @Operation(summary = "Utilisateurs par classe", description = "Récupère tous les utilisateurs (étudiants) d'une classe")
    public ResponseEntity<ApiResponseDTO<List<UserDTO>>> getUsersByClasse(@PathVariable String classe) {
        List<UserDTO> users = userService.getUsersByClasse(classe);
        return ResponseEntity.ok(ApiResponseDTO.success("Utilisateurs de la classe " + classe, users));
    }

    /**
     * Récupère les utilisateurs actifs
     */
    @GetMapping("/actifs")
    @Operation(summary = "Utilisateurs actifs", description = "Récupère tous les utilisateurs actifs")
    public ResponseEntity<ApiResponseDTO<List<UserDTO>>> getActiveUsers() {
        List<UserDTO> users = userService.getActiveUsers();
        return ResponseEntity.ok(ApiResponseDTO.success("Utilisateurs actifs", users));
    }

    /**
     * Récupère tous les professeurs
     */
    @GetMapping("/professeurs")
    @Operation(summary = "Liste des professeurs", description = "Récupère tous les professeurs")
    public ResponseEntity<ApiResponseDTO<List<UserDTO>>> getProfesseurs() {
        List<UserDTO> professeurs = userService.getProfesseurs();
        return ResponseEntity.ok(ApiResponseDTO.success("Liste des professeurs", professeurs));
    }

    /**
     * Récupère tous les étudiants
     */
    @GetMapping("/etudiants")
    @Operation(summary = "Liste des étudiants", description = "Récupère tous les étudiants")
    public ResponseEntity<ApiResponseDTO<List<UserDTO>>> getEtudiants() {
        List<UserDTO> etudiants = userService.getEtudiants();
        return ResponseEntity.ok(ApiResponseDTO.success("Liste des étudiants", etudiants));
    }

    /**
     * Récupère les étudiants d'une classe
     */
    @GetMapping("/etudiants/classe/{classe}")
    @Operation(summary = "Étudiants par classe", description = "Récupère tous les étudiants d'une classe spécifique")
    public ResponseEntity<ApiResponseDTO<List<UserDTO>>> getEtudiantsByClasse(@PathVariable String classe) {
        List<UserDTO> etudiants = userService.getEtudiantsByClasse(classe);
        return ResponseEntity.ok(ApiResponseDTO.success("Étudiants de " + classe, etudiants));
    }

    /**
     * Crée un nouvel utilisateur
     */
    @PostMapping
    @Operation(summary = "Créer un utilisateur", description = "Crée un nouvel utilisateur")
    public ResponseEntity<ApiResponseDTO<UserDTO>> createUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success("Utilisateur créé avec succès", createdUser));
    }

    /**
     * Met à jour un utilisateur
     */
    @PutMapping("/{id}")
    @Operation(summary = "Modifier un utilisateur", description = "Met à jour un utilisateur existant")
    public ResponseEntity<ApiResponseDTO<UserDTO>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(ApiResponseDTO.success("Utilisateur modifié avec succès", updatedUser));
    }

    /**
     * Change le mot de passe d'un utilisateur
     */
    @PutMapping("/{id}/change-password")
    @Operation(summary = "Changer le mot de passe", description = "Change le mot de passe d'un utilisateur")
    public ResponseEntity<ApiResponseDTO<Void>> changePassword(
            @PathVariable Long id,
            @RequestBody Map<String, String> passwords) {
        String oldPassword = passwords.get("oldPassword");
        String newPassword = passwords.get("newPassword");
        userService.changePassword(id, oldPassword, newPassword);
        return ResponseEntity.ok(ApiResponseDTO.success("Mot de passe changé avec succès", null));
    }

    /**
     * Réinitialise le mot de passe d'un utilisateur
     */
    @PutMapping("/{id}/reset-password")
    @Operation(summary = "Réinitialiser le mot de passe", 
               description = "Génère un nouveau mot de passe aléatoire (admin uniquement)")
    public ResponseEntity<ApiResponseDTO<Map<String, String>>> resetPassword(@PathVariable Long id) {
        String newPassword = userService.resetPassword(id);
        Map<String, String> response = Map.of("newPassword", newPassword);
        return ResponseEntity.ok(ApiResponseDTO.success("Mot de passe réinitialisé", response));
    }

    /**
     * Active/Désactive un utilisateur
     */
    @PutMapping("/{id}/toggle-status")
    @Operation(summary = "Activer/Désactiver", description = "Change le statut actif/inactif d'un utilisateur")
    public ResponseEntity<ApiResponseDTO<UserDTO>> toggleUserStatus(@PathVariable Long id) {
        UserDTO user = userService.toggleUserStatus(id);
        String message = user.getActif() ? "Utilisateur activé" : "Utilisateur désactivé";
        return ResponseEntity.ok(ApiResponseDTO.success(message, user));
    }

    /**
     * Supprime un utilisateur (désactivation logique)
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un utilisateur", description = "Désactive un utilisateur")
    public ResponseEntity<ApiResponseDTO<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponseDTO.success("Utilisateur supprimé avec succès", null));
    }

    /**
     * Statistiques des utilisateurs
     */
    @GetMapping("/stats")
    @Operation(summary = "Statistiques", description = "Récupère les statistiques des utilisateurs")
    public ResponseEntity<ApiResponseDTO<Map<String, Object>>> getUserStats() {
        Map<String, Object> stats = userService.getUserStats();
        return ResponseEntity.ok(ApiResponseDTO.success("Statistiques récupérées", stats));
    }
}
