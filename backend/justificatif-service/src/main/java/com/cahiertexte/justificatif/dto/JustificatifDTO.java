package com.cahiertexte.justificatif.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * DTO pour Justificatif
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JustificatifDTO {
    
    private Long id;
    
    @NotNull(message = "L'étudiant est obligatoire")
    private Long etudiantId;
    
    // Informations calculées
    private String etudiantNom;
    private String etudiantPrenom;
    
    @NotNull(message = "Le cours est obligatoire")
    private Long coursId;
    
    // Informations calculées
    private String coursDate;
    private String coursMatiere;
    
    @NotBlank(message = "Le motif est obligatoire")
    private String motif;
    
    private String fichier;
    private String status; // EN_ATTENTE, ACCEPTE, REFUSE
    
    private LocalDateTime dateSoumission;
    private LocalDateTime dateTraitement;
    
    private Long traitePar;
    private String traiteParNom; // Calculé
    
    private String commentaireTraitement;

    // Constructeurs
    public JustificatifDTO() {
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getCoursId() {
        return coursId;
    }

    public void setCoursId(Long coursId) {
        this.coursId = coursId;
    }

    public String getCoursDate() {
        return coursDate;
    }

    public void setCoursDate(String coursDate) {
        this.coursDate = coursDate;
    }

    public String getCoursMatiere() {
        return coursMatiere;
    }

    public void setCoursMatiere(String coursMatiere) {
        this.coursMatiere = coursMatiere;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getFichier() {
        return fichier;
    }

    public void setFichier(String fichier) {
        this.fichier = fichier;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDateSoumission() {
        return dateSoumission;
    }

    public void setDateSoumission(LocalDateTime dateSoumission) {
        this.dateSoumission = dateSoumission;
    }

    public LocalDateTime getDateTraitement() {
        return dateTraitement;
    }

    public void setDateTraitement(LocalDateTime dateTraitement) {
        this.dateTraitement = dateTraitement;
    }

    public Long getTraitePar() {
        return traitePar;
    }

    public void setTraitePar(Long traitePar) {
        this.traitePar = traitePar;
    }

    public String getTraiteParNom() {
        return traiteParNom;
    }

    public void setTraiteParNom(String traiteParNom) {
        this.traiteParNom = traiteParNom;
    }

    public String getCommentaireTraitement() {
        return commentaireTraitement;
    }

    public void setCommentaireTraitement(String commentaireTraitement) {
        this.commentaireTraitement = commentaireTraitement;
    }
}
