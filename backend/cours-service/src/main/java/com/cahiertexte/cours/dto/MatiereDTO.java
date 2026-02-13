package com.cahiertexte.cours.dto;

import java.time.LocalDateTime;

/**
 * DTO pour l'entité Matière.
 *
 * @author Abdoulaye Guene
 */
public class MatiereDTO {

    private Long id;
    private String nom;
    private String code;
    private Integer volumeHoraire;
    private Integer volumeRealise;
    private Long professeurId;
    private String classe;
    private Boolean actif;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public Integer getVolumeHoraire() { return volumeHoraire; }
    public void setVolumeHoraire(Integer volumeHoraire) { this.volumeHoraire = volumeHoraire; }
    public Integer getVolumeRealise() { return volumeRealise; }
    public void setVolumeRealise(Integer volumeRealise) { this.volumeRealise = volumeRealise; }
    public Long getProfesseurId() { return professeurId; }
    public void setProfesseurId(Long professeurId) { this.professeurId = professeurId; }
    public String getClasse() { return classe; }
    public void setClasse(String classe) { this.classe = classe; }
    public Boolean getActif() { return actif; }
    public void setActif(Boolean actif) { this.actif = actif; }
    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
    public LocalDateTime getDateModification() { return dateModification; }
    public void setDateModification(LocalDateTime dateModification) { this.dateModification = dateModification; }
}
