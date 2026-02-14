package com.cahiertexte.cours.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Entité représentant un cours (séance)
 */
@Entity
@Table(name = "cours")
public class Cours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "matiere_id", nullable = false)
    private Long matiereId;

    @NotNull
    @Column(name = "professeur_id", nullable = false)
    private Long professeurId;

    @NotBlank
    @Column(nullable = false, length = 20)
    private String classe;

    @NotNull
    @Column(name = "date_cours", nullable = false)
    private LocalDate dateCours;

    @NotNull
    @Column(name = "heure_debut", nullable = false)
    private LocalTime heureDebut;

    @NotNull
    @Column(name = "heure_fin", nullable = false)
    private LocalTime heureFin;

    @Column(length = 50)
    private String salle;

    @Column(name = "cahier_texte", columnDefinition = "TEXT")
    private String cahierTexte;

    @Column(nullable = false, length = 20)
    private String status = "PLANIFIE"; // PLANIFIE, VALIDE, TERMINE, ANNULE

    @Column(name = "valide_par_prof", nullable = false)
    private Boolean valideParProf = false;

    @Column(name = "date_validation")
    private LocalDateTime dateValidation;

    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @Column(name = "date_modification")
    private LocalDateTime dateModification;

    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        dateModification = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dateModification = LocalDateTime.now();
    }

    // Constructeurs
    public Cours() {
    }

    public Cours(Long matiereId, Long professeurId, String classe, LocalDate dateCours, 
                LocalTime heureDebut, LocalTime heureFin) {
        this.matiereId = matiereId;
        this.professeurId = professeurId;
        this.classe = classe;
        this.dateCours = dateCours;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.status = "PLANIFIE";
        this.valideParProf = false;
    }

    // Méthode utilitaire pour calculer la durée en heures
    public double getDureeEnHeures() {
        if (heureDebut == null || heureFin == null) return 0;
        long minutes = java.time.Duration.between(heureDebut, heureFin).toMinutes();
        return minutes / 60.0;
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

    public Long getProfesseurId() {
        return professeurId;
    }

    public void setProfesseurId(Long professeurId) {
        this.professeurId = professeurId;
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
}
