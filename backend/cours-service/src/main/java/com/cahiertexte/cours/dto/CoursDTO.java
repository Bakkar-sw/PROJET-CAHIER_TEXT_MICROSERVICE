package com.cahiertexte.cours.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * DTO pour Cours
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoursDTO {
    
    private Long id;
    
    @NotNull(message = "La matière est obligatoire")
    private Long matiereId;
    
    private String matiereNom; // Nom de la matière (calculé)
    
    @NotNull(message = "Le professeur est obligatoire")
    private Long professeurId;
    
    private String professeurNom; // Nom du professeur (calculé)
    
    @NotBlank(message = "La classe est obligatoire")
    private String classe;
    
    @NotNull(message = "La date est obligatoire")
    private LocalDate dateCours;
    
    @NotNull(message = "L'heure de début est obligatoire")
    private LocalTime heureDebut;
    
    @NotNull(message = "L'heure de fin est obligatoire")
    private LocalTime heureFin;
    
    private String salle;
    private String cahierTexte;
    private String status;
    private Boolean valideParProf;
    private LocalDateTime dateValidation;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
    
    // Champ calculé
    private Double dureeEnHeures;

    // Constructeurs
    public CoursDTO() {
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMatiereId() {
        return matiereId;
    }

    public void setMatiereId(Long matiereId) {
        this.matiereId = matiereId;
    }

    public String getMatiereNom() {
        return matiereNom;
    }

    public void setMatiereNom(String matiereNom) {
        this.matiereNom = matiereNom;
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

    public LocalDate getDateCours() {
        return dateCours;
    }

    public void setDateCours(LocalDate dateCours) {
        this.dateCours = dateCours;
    }

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public LocalTime getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(LocalTime heureFin) {
        this.heureFin = heureFin;
    }

    public String getSalle() {
        return salle;
    }

    public void setSalle(String salle) {
        this.salle = salle;
    }

    public String getCahierTexte() {
        return cahierTexte;
    }

    public void setCahierTexte(String cahierTexte) {
        this.cahierTexte = cahierTexte;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getValideParProf() {
        return valideParProf;
    }

    public void setValideParProf(Boolean valideParProf) {
        this.valideParProf = valideParProf;
    }

    public LocalDateTime getDateValidation() {
        return dateValidation;
    }

    public void setDateValidation(LocalDateTime dateValidation) {
        this.dateValidation = dateValidation;
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

    public Double getDureeEnHeures() {
        return dureeEnHeures;
    }

    public void setDureeEnHeures(Double dureeEnHeures) {
        this.dureeEnHeures = dureeEnHeures;
    }
}
