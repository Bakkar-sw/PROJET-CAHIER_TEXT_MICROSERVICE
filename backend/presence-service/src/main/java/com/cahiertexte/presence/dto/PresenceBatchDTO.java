package com.cahiertexte.presence.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * DTO pour l'enregistrement en batch des présences d'un cours
 */
public class PresenceBatchDTO {
    
    @NotNull(message = "Le cours est obligatoire")
    private Long coursId;
    
    @NotEmpty(message = "La liste des présences ne peut pas être vide")
    private List<PresenceItemDTO> presences;

    // Constructeurs
    public PresenceBatchDTO() {
    }

    // Getters et Setters
    public Long getCoursId() {
        return coursId;
    }

    public void setCoursId(Long coursId) {
        this.coursId = coursId;
    }

    public List<PresenceItemDTO> getPresences() {
        return presences;
    }

    public void setPresences(List<PresenceItemDTO> presences) {
        this.presences = presences;
    }

    /**
     * Classe interne pour un item de présence
     */
    public static class PresenceItemDTO {
        @NotNull(message = "L'étudiant est obligatoire")
        private Long etudiantId;
        
        @NotNull(message = "Le status est obligatoire")
        private String status;
        
        private String remarque;

        // Constructeurs
        public PresenceItemDTO() {
        }

        // Getters et Setters
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
}
