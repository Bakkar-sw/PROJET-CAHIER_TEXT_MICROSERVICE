package com.cahiertexte.presence.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "presences")
public class Presence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cours_id", nullable = false)
    private Long coursId;

    @Column(name = "etudiant_id", nullable = false)
    private Long etudiantId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PresenceStatus status;

    @Column(nullable = false)
    private LocalDate date;

    private String remarque;

    public Presence() {}

    public Long getId() { return id; }
    public Long getCoursId() { return coursId; }
    public Long getEtudiantId() { return etudiantId; }
    public PresenceStatus getStatus() { return status; }
    public LocalDate getDate() { return date; }
    public String getRemarque() { return remarque; }

    public void setId(Long id) { this.id = id; }
    public void setCoursId(Long coursId) { this.coursId = coursId; }
    public void setEtudiantId(Long etudiantId) { this.etudiantId = etudiantId; }
    public void setStatus(PresenceStatus status) { this.status = status; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setRemarque(String remarque) { this.remarque = remarque; }
}
