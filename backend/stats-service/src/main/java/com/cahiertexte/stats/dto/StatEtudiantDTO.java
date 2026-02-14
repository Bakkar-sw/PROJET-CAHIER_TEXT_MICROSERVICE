package com.cahiertexte.stats.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * DTO pour les statistiques d'un étudiant
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatEtudiantDTO {
    
    private Long etudiantId;
    private String username;
    private String prenom;
    private String nom;
    private String classe;
    
    // Statistiques de présence
    private Integer totalSeances;
    private Integer presences;
    private Integer absences;
    private Integer retards;
    private Double tauxPresence; // En pourcentage
    
    // Statistiques des justificatifs
    private Integer justificatifsEnAttente;
    private Integer justificatifsAcceptes;
    private Integer justificatifsRefuses;
    
    // Alertes
    private Boolean alerteAbsence; // Si absences >= 3

    // Constructeurs
    public StatEtudiantDTO() {
    }

    // Getters et Setters
    public Long getEtudiantId() {
        return etudiantId;
    }

    public void setEtudiantId(Long etudiantId) {
        this.etudiantId = etudiantId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public Integer getTotalSeances() {
        return totalSeances;
    }

    public void setTotalSeances(Integer totalSeances) {
        this.totalSeances = totalSeances;
    }

    public Integer getPresences() {
        return presences;
    }

    public void setPresences(Integer presences) {
        this.presences = presences;
    }

    public Integer getAbsences() {
        return absences;
    }

    public void setAbsences(Integer absences) {
        this.absences = absences;
    }

    public Integer getRetards() {
        return retards;
    }

    public void setRetards(Integer retards) {
        this.retards = retards;
    }

    public Double getTauxPresence() {
        return tauxPresence;
    }

    public void setTauxPresence(Double tauxPresence) {
        this.tauxPresence = tauxPresence;
    }

    public Integer getJustificatifsEnAttente() {
        return justificatifsEnAttente;
    }

    public void setJustificatifsEnAttente(Integer justificatifsEnAttente) {
        this.justificatifsEnAttente = justificatifsEnAttente;
    }

    public Integer getJustificatifsAcceptes() {
        return justificatifsAcceptes;
    }

    public void setJustificatifsAcceptes(Integer justificatifsAcceptes) {
        this.justificatifsAcceptes = justificatifsAcceptes;
    }

    public Integer getJustificatifsRefuses() {
        return justificatifsRefuses;
    }

    public void setJustificatifsRefuses(Integer justificatifsRefuses) {
        this.justificatifsRefuses = justificatifsRefuses;
    }

    public Boolean getAlerteAbsence() {
        return alerteAbsence;
    }

    public void setAlerteAbsence(Boolean alerteAbsence) {
        this.alerteAbsence = alerteAbsence;
    }
}
