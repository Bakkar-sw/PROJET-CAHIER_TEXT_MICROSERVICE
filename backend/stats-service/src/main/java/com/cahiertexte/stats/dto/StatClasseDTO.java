package com.cahiertexte.stats.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

/**
 * DTO pour les statistiques d'une classe
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatClasseDTO {
    
    private String classe; // CI_M1, CI_M2, MCS_M1, MCS_M2
    
    // Statistiques générales
    private Integer nombreEtudiants;
    private Integer nombreMatieres;
    private Integer nombreCoursTotal;
    private Integer nombreCoursPlanifies;
    private Integer nombreCoursValides;
    
    // Statistiques de présence globale
    private Double tauxPresenceMoyen; // En pourcentage
    private Integer totalAbsences;
    private Integer totalRetards;
    
    // Top étudiants avec le plus d'absences
    private List<StatEtudiantDTO> top5Absences;
    
    // Matières avec alertes (< 12h restantes)
    private List<MatiereAlerteDTO> matieresEnAlerte;

    // Constructeurs
    public StatClasseDTO() {
    }

    public StatClasseDTO(String classe) {
        this.classe = classe;
    }

    // Getters et Setters
    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public Integer getNombreEtudiants() {
        return nombreEtudiants;
    }

    public void setNombreEtudiants(Integer nombreEtudiants) {
        this.nombreEtudiants = nombreEtudiants;
    }

    public Integer getNombreMatieres() {
        return nombreMatieres;
    }

    public void setNombreMatieres(Integer nombreMatieres) {
        this.nombreMatieres = nombreMatieres;
    }

    public Integer getNombreCoursTotal() {
        return nombreCoursTotal;
    }

    public void setNombreCoursTotal(Integer nombreCoursTotal) {
        this.nombreCoursTotal = nombreCoursTotal;
    }

    public Integer getNombreCoursPlanifies() {
        return nombreCoursPlanifies;
    }

    public void setNombreCoursPlanifies(Integer nombreCoursPlanifies) {
        this.nombreCoursPlanifies = nombreCoursPlanifies;
    }

    public Integer getNombreCoursValides() {
        return nombreCoursValides;
    }

    public void setNombreCoursValides(Integer nombreCoursValides) {
        this.nombreCoursValides = nombreCoursValides;
    }

    public Double getTauxPresenceMoyen() {
        return tauxPresenceMoyen;
    }

    public void setTauxPresenceMoyen(Double tauxPresenceMoyen) {
        this.tauxPresenceMoyen = tauxPresenceMoyen;
    }

    public Integer getTotalAbsences() {
        return totalAbsences;
    }

    public void setTotalAbsences(Integer totalAbsences) {
        this.totalAbsences = totalAbsences;
    }

    public Integer getTotalRetards() {
        return totalRetards;
    }

    public void setTotalRetards(Integer totalRetards) {
        this.totalRetards = totalRetards;
    }

    public List<StatEtudiantDTO> getTop5Absences() {
        return top5Absences;
    }

    public void setTop5Absences(List<StatEtudiantDTO> top5Absences) {
        this.top5Absences = top5Absences;
    }

    public List<MatiereAlerteDTO> getMatieresEnAlerte() {
        return matieresEnAlerte;
    }

    public void setMatieresEnAlerte(List<MatiereAlerteDTO> matieresEnAlerte) {
        this.matieresEnAlerte = matieresEnAlerte;
    }
}
