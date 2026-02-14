package com.cahiertexte.stats.controller;

import com.cahiertexte.stats.service.AuthService;
import com.cahiertexte.common.dto.ApiResponseDTO;
import com.cahiertexte.common.dto.AuthResponseDTO;
import com.cahiertexte.common.dto.LoginRequestDTO;
import com.cahiertexte.common.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST pour l'authentification
 * 
 * Endpoints disponibles :
 * - POST /login : Connexion
 * - POST /register : Inscription
 * - GET /validate : Valider un token
 * - GET /me : Obtenir les infos de l'utilisateur connecté
 * 
 * @author Boubacar Souare
 */
@RestController
@RequestMapping("/") // Le context-path est déjà /api/auth
@Tag(name = "Authentication", description = "API d'authentification et gestion des tokens JWT")
@CrossOrigin(origins = "${cors.allowed-origins}")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Endpoint de connexion
     * 
     * @param loginRequest Les identifiants (username, password)
     * @return Le token JWT et les informations utilisateur
     */
    @PostMapping("/login")
    @Operation(summary = "Connexion", description = "Authentifie un utilisateur et retourne un token JWT")
    public ResponseEntity<ApiResponseDTO<AuthResponseDTO>> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        AuthResponseDTO response = authService.login(loginRequest);
        return ResponseEntity.ok(ApiResponseDTO.success("Connexion réussie", response));
    }

    /**
     * Endpoint d'inscription (optionnel - peut être désactivé en production)
     * 
     * @param userDTO Les informations du nouvel utilisateur
     * @return L'utilisateur créé
     */
    @PostMapping("/register")
    @Operation(summary = "Inscription", description = "Crée un nouveau compte utilisateur")
    public ResponseEntity<ApiResponseDTO<UserDTO>> register(@Valid @RequestBody UserDTO userDTO) {
        UserDTO createdUser = authService.register(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success("Utilisateur créé avec succès", createdUser));
    }

    /**
     * Valide un token JWT
     * 
     * @param authorization Le header Authorization avec le token
     * @return true si le token est valide
     */
    @GetMapping("/validate")
    @Operation(summary = "Valider un token", description = "Vérifie si un token JWT est valide")
    public ResponseEntity<ApiResponseDTO<Boolean>> validateToken(
            @RequestHeader("Authorization") String authorization) {
        
        String token = extractToken(authorization);
        boolean isValid = authService.validateToken(token);
        
        if (isValid) {
            return ResponseEntity.ok(ApiResponseDTO.success("Token valide", true));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponseDTO.error("Token invalide ou expiré"));
        }
    }

    /**
     * Récupère les informations de l'utilisateur connecté
     * 
     * @param authorization Le header Authorization avec le token
     * @return Les informations utilisateur
     */
    @GetMapping("/me")
    @Operation(summary = "Obtenir l'utilisateur connecté", description = "Retourne les informations de l'utilisateur à partir du token")
    public ResponseEntity<ApiResponseDTO<UserDTO>> getCurrentUser(
            @RequestHeader("Authorization") String authorization) {
        
        String token = extractToken(authorization);
        UserDTO user = authService.getUserFromToken(token);
        
        return ResponseEntity.ok(ApiResponseDTO.success(user));
    }

    /**
     * Endpoint de test pour vérifier que le service fonctionne
     */
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Vérifie que le service est opérationnel")
    public ResponseEntity<ApiResponseDTO<String>> health() {
        return ResponseEntity.ok(ApiResponseDTO.success("Auth Service is running"));
    }

    /**
     * Extrait le token du header Authorization
     */
    private String extractToken(String authorization) {
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        throw new IllegalArgumentException("Token manquant ou format invalide");
    }
}
