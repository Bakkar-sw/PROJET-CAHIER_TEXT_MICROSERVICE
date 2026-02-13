package com.cahiertexte.auth.controller;

import com.cahiertexte.auth.service.AuthService;
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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST pour l'authentification
 */
@RestController
@RequestMapping("/")
@Tag(name = "Authentication", description = "API d'authentification et gestion des tokens JWT")
@CrossOrigin(origins = "${cors.allowed-origins}")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * LOGIN
     */
    @PostMapping("/login")
    @Operation(summary = "Connexion", description = "Authentifie un utilisateur et retourne un token JWT")
    public ResponseEntity<ApiResponseDTO<AuthResponseDTO>> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        AuthResponseDTO response = authService.login(loginRequest);
        return ResponseEntity.ok(ApiResponseDTO.success("Connexion réussie", response));
    }

    /**
     * REGISTER
     */
    @PostMapping("/register")
    @Operation(summary = "Inscription", description = "Crée un nouveau compte utilisateur")
    public ResponseEntity<ApiResponseDTO<UserDTO>> register(@Valid @RequestBody UserDTO userDTO) {
        UserDTO createdUser = authService.register(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success("Utilisateur créé avec succès", createdUser));
    }

    /**
     * VALIDATE TOKEN (corrigé)
     */
    @GetMapping("/validate")
    @Operation(summary = "Valider un token", description = "Vérifie si un token JWT est valide")
    public ResponseEntity<ApiResponseDTO<Boolean>> validateToken(Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponseDTO.error("Token invalide ou absent"));
        }

        return ResponseEntity.ok(ApiResponseDTO.success("Token valide", true));
    }

    /**
     * GET CURRENT USER (corrigé)
     */
    @GetMapping("/me")
    @Operation(summary = "Utilisateur connecté", description = "Retourne les informations de l'utilisateur connecté")
    public ResponseEntity<ApiResponseDTO<UserDTO>> getCurrentUser(Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponseDTO.error("Non authentifié"));
        }

        String username = authentication.getName();
        UserDTO user = authService.getUserByUsername(username);

        return ResponseEntity.ok(ApiResponseDTO.success(user));
    }

    /**
     * HEALTH CHECK
     */
    @GetMapping("/health")
    public ResponseEntity<ApiResponseDTO<String>> health() {
        return ResponseEntity.ok(ApiResponseDTO.success("Auth Service is running"));
    }
}
