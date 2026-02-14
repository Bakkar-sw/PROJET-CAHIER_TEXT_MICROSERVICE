package com.cahiertexte.cours.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * DTO pour Matiere
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatiereDTO {
    
    private Long id;
    
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;
    
    @NotBlank(message = "Le code est obligatoire")
    private String code;
    
    @NotNull(message = "Le volume horaire est obligatoire")
    @Min(value = 1, message = "Le volume horaire doit être supérieur à 0")
    private Integer volumeHoraire;
    
    private Integer volumeRealise;
    
    @NotNull(message = "Le professeur est obligatoire")
    private Long professeurId;
    
    private String professeurNom; // Nom complet du professeur (calculé)
    
    @NotBlank(message = "La classe est obligatoire")
    private String classe;
    
    private Boolean actif;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
    
    // Champs calculés
    private Integer heuresRestantes;
    private Boolean enAlerte;

    // Constructeurs
    public MatiereDTO() {
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getVolumeHoraire() {
        return volumeHoraire;
    }

    public void setVolumeHoraire(Integer volumeHoraire) {
        this.volumeHoraire = volumeHoraire;
    }

    public Integer getVolumeRealise() {
        return volumeRealise;
    }

    public void setVolumeRealise(Integer volumeRealise) {
        this.volumeRealise = volumeRealise;
    }

    public Long getProfesseurId() {
        return professeurId;
    }

    public void setProfesseurId(Long professeurId) {
        this.professeurId = professeurId;
    }

    public String getProfesseurNom() {
        return professeurNom;
    }

    public void setProfesseurNom(String professeurNom) {
        this.professeurNom = professeurNom;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
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

    public Integer getHeuresRestantes() {
        return heuresRestantes;
    }

    public void setHeuresRestantes(Integer heuresRestantes) {
        this.heuresRestantes = heuresRestantes;
    }

    public Boolean getEnAlerte() {
        return enAlerte;
    }

    public void setEnAlerte(Boolean enAlerte) {
        this.enAlerte = enAlerte;
    }
}
