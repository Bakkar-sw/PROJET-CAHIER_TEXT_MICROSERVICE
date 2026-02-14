package com.cahiertexte.presence.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * DTO pour Presence
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PresenceDTO {
    
    private Long id;
    
    @NotNull(message = "Le cours est obligatoire")
    private Long coursId;
    
    @NotNull(message = "L'étudiant est obligatoire")
    private Long etudiantId;
    
    // Informations calculées (depuis d'autres services)
    private String etudiantNom;
    private String etudiantPrenom;
    private String etudiantClasse;
    
    @NotBlank(message = "Le status est obligatoire")
    private String status; // PRESENT, ABSENT, RETARD
    
    private String remarque;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;

    // Constructeurs
    public PresenceDTO() {
    }

    public PresenceDTO(Long coursId, Long etudiantId, String status) {
        this.coursId = coursId;
        this.etudiantId = etudiantId;
        this.status = status;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCoursId() {
        return coursId;
    }

    public void setCoursId(Long coursId) {
        this.coursId = coursId;
    }

    public Long getEtudiantId() {
        return etudiantId;
    }

    public void setEtudiantId(Long etudiantId) {
        this.etudiantId = etudiantId;
    }

    public String getEtudiantNom() {
        return etudiantNom;
    }

    public void setEtudiantNom(String etudiantNom) {
        this.etudiantNom = etudiantNom;
    }

    public String getEtudiantPrenom() {
        return etudiantPrenom;
    }

    public void setEtudiantPrenom(String etudiantPrenom) {
        this.etudiantPrenom = etudiantPrenom;
    }

    public String getEtudiantClasse() {
        return etudiantClasse;
    }

    public void setEtudiantClasse(String etudiantClasse) {
        this.etudiantClasse = etudiantClasse;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDateTime getDateModification() {
        return dateModification;
    }

    public void setDateModification(LocalDateTime dateModification) {
        this.dateModification = dateModification;
    }
}
