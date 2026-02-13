package com.cahiertexte.cours.dto;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO pour la mise à jour d'un cours (champs optionnels).
 *
 * @author Abdoulaye Guene
 */
public class CoursUpdateDTO {

    private Long matiereId;
    private Long professeurId;
    private String classe;
    private LocalDate dateCours;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    @Size(max = 50)
    private String salle;
    private String cahierTexte;
    private String status;

    public Long getMatiereId() { return matiereId; }
    public void setMatiereId(Long matiereId) { this.matiereId = matiereId; }
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
}
