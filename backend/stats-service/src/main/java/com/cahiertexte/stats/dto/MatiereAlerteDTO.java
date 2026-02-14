package com.cahiertexte.stats.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * DTO pour les matières en alerte (< 12h restantes)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatiereAlerteDTO {
    
    private Long id;
    private String nom;
    private String code;
    private Integer volumeHoraire;
    private Integer volumeRealise;
    private Integer heuresRestantes;
    private String classe;
    private String professeur;
    private Boolean critique; // Si heures restantes < 6

    // Constructeurs
    public MatiereAlerteDTO() {
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

    public Integer getHeuresRestantes() {
        return heuresRestantes;
    }

    public void setHeuresRestantes(Integer heuresRestantes) {
        this.heuresRestantes = heuresRestantes;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getProfesseur() {
        return professeur;
    }

    public void setProfesseur(String professeur) {
        this.professeur = professeur;
    }

    public Boolean getCritique() {
        return critique;
    }

    public void setCritique(Boolean critique) {
        this.critique = critique;
    }
}
