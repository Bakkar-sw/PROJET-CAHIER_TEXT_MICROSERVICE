package com.cahiertexte.cours.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * DTO pour l'entité Cours (lecture).
 *
 * @author Abdoulaye Guene
 */
public class CoursDTO {

    private Long id;
    private Long matiereId;
    private String matiereNom;
    private String matiereCode;
    private Long professeurId;
    private String classe;
    private LocalDate dateCours;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private String salle;
    private String cahierTexte;
    private String status;
    private Boolean valideParProf;
    private LocalDateTime dateValidation;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getMatiereId() { return matiereId; }
    public void setMatiereId(Long matiereId) { this.matiereId = matiereId; }
    public String getMatiereNom() { return matiereNom; }
    public void setMatiereNom(String matiereNom) { this.matiereNom = matiereNom; }
    public String getMatiereCode() { return matiereCode; }
    public void setMatiereCode(String matiereCode) { this.matiereCode = matiereCode; }
    public Long getProfesseurId() { return professeurId; }
    public void setProfesseurId(Long professeurId) { this.professeurId = professeurId; }
    public String getClasse() { return classe; }
    public void setClasse(String classe) { this.classe = classe; }
    public LocalDate getDateCours() { return dateCours; }
    public void setDateCours(LocalDate dateCours) { this.dateCours = dateCours; }
    public LocalTime getHeureDebut() { return heureDebut; }
    public void setHeureDebut(LocalTime heureDebut) { this.heureDebut = heureDebut; }
    public LocalTime getHeureFin() { return heureFin; }
    public void setHeureFin(LocalTime heureFin) { this.heureFin = heureFin; }
    public String getSalle() { return salle; }
    public void setSalle(String salle) { this.salle = salle; }
    public String getCahierTexte() { return cahierTexte; }
    public void setCahierTexte(String cahierTexte) { this.cahierTexte = cahierTexte; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Boolean getValideParProf() { return valideParProf; }
    public void setValideParProf(Boolean valideParProf) { this.valideParProf = valideParProf; }
    public LocalDateTime getDateValidation() { return dateValidation; }
    public void setDateValidation(LocalDateTime dateValidation) { this.dateValidation = dateValidation; }
    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
    public LocalDateTime getDateModification() { return dateModification; }
    public void setDateModification(LocalDateTime dateModification) { this.dateModification = dateModification; }
}
