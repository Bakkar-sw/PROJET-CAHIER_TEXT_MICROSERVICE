package com.cahiertexte.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * DTO pour la réponse d'authentification
 * Contient le token JWT et les informations utilisateur
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponseDTO {
    
    private String token;
    private String type = "Bearer";
    private Long userId;
    private String username;
    private String email;
    private String prenom;
    private String nom;
    private String role;
    private String classe;
    private Long expiresIn; // Durée de validité en millisecondes

    // Constructeurs
    public AuthResponseDTO() {
    }

    public AuthResponseDTO(String token, UserDTO user) {
        this.token = token;
        this.userId = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.prenom = user.getPrenom();
        this.nom = user.getNom();
        this.role = user.getRole();
        this.classe = user.getClasse();
    }

    public AuthResponseDTO(String token, Long userId, String username, String email, 
                          String prenom, String nom, String role, String classe) {
        this.token = token;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.prenom = prenom;
        this.nom = nom;
        this.role = role;
        this.classe = classe;
    }

    // Getters et Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
