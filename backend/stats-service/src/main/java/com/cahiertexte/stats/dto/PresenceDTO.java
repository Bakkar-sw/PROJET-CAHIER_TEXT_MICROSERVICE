package com.cahiertexte.stats.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * DTO simplifié pour Presence
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PresenceDTO {
    private Long id;
    private Long coursId;
    private Long etudiantId;
    private String status; // PRESENT, ABSENT, RETARD
    private String remarque;

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCoursId() {
        return coursId;
    }

    public void setCoursId(Long coursId) {
        this.coursId = coursId;
    }

    public Long getEtudiantId() {
        return etudiantId;
    }

    public void setEtudiantId(Long etudiantId) {
        this.etudiantId = etudiantId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }
}
