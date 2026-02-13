package com.cahiertexte.cours.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO pour la création d'un cours.
 *
 * @author Abdoulaye Guene
 */
public class CoursCreateDTO {

    @NotNull(message = "L'ID de la matière est requis")
    private Long matiereId;

    @NotNull(message = "L'ID du professeur est requis")
    private Long professeurId;

    @NotBlank(message = "La classe est requise")
    @Size(max = 20)
    private String classe;

    @NotNull(message = "La date du cours est requise")
    private LocalDate dateCours;

    @NotNull(message = "L'heure de début est requise")
    private LocalTime heureDebut;

    @NotNull(message = "L'heure de fin est requise")
    private LocalTime heureFin;

    @Size(max = 50)
    private String salle;

    private String cahierTexte;

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
}
