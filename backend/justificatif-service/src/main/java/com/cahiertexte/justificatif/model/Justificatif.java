package com.cahiertexte.justificatif.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * Entité représentant un justificatif d'absence
 */
@Entity
@Table(name = "justificatifs")
public class Justificatif {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "etudiant_id", nullable = false)
    private Long etudiantId;

    @NotNull
    @Column(name = "cours_id", nullable = false)
    private Long coursId;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String motif;

    @Column(length = 255)
    private String fichier; // Chemin vers le fichier uploadé

    @Column(nullable = false, length = 20)
    private String status = "EN_ATTENTE"; // EN_ATTENTE, ACCEPTE, REFUSE

    @Column(name = "date_soumission", nullable = false, updatable = false)
    private LocalDateTime dateSoumission;

    @Column(name = "date_traitement")
    private LocalDateTime dateTraitement;

    @Column(name = "traite_par")
    private Long traitePar; // ID du responsable qui a traité

    @Column(name = "commentaire_traitement", columnDefinition = "TEXT")
    private String commentaireTraitement;

    @PrePersist
    protected void onCreate() {
        dateSoumission = LocalDateTime.now();
    }

    // Constructeurs
    public Justificatif() {
    }

    public Justificatif(Long etudiantId, Long coursId, String motif) {
        this.etudiantId = etudiantId;
        this.coursId = coursId;
        this.motif = motif;
        this.status = "EN_ATTENTE";
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEtudiantId() {
        return etudiantId;
    }

    public void setEtudiantId(Long etudiantId) {
        this.etudiantId = etudiantId;
    }

    public Long getCoursId() {
        return coursId;
    }

    public void setCoursId(Long coursId) {
        this.coursId = coursId;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getFichier() {
        return fichier;
    }

    public void setFichier(String fichier) {
        this.fichier = fichier;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDateSoumission() {
        return dateSoumission;
    }

    public void setDateSoumission(LocalDateTime dateSoumission) {
        this.dateSoumission = dateSoumission;
    }

    public LocalDateTime getDateTraitement() {
        return dateTraitement;
    }

    public void setDateTraitement(LocalDateTime dateTraitement) {
        this.dateTraitement = dateTraitement;
    }

    public Long getTraitePar() {
        return traitePar;
    }

    public void setTraitePar(Long traitePar) {
        this.traitePar = traitePar;
    }

    public String getCommentaireTraitement() {
        return commentaireTraitement;
    }

    public void setCommentaireTraitement(String commentaireTraitement) {
        this.commentaireTraitement = commentaireTraitement;
    }
}
