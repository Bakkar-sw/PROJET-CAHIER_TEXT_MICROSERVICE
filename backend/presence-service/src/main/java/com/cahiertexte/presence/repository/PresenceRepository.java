package com.cahiertexte.presence.repository;

import com.cahiertexte.presence.model.Presence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour l'entité Presence
 */
@Repository
public interface PresenceRepository extends JpaRepository<Presence, Long> {

    /**
     * Recherche les présences d'un cours
     */
    List<Presence> findByCoursId(Long coursId);

    /**
     * Recherche les présences d'un étudiant
     */
    List<Presence> findByEtudiantId(Long etudiantId);

    /**
     * Recherche les présences par status
     */
    List<Presence> findByStatus(String status);

    /**
     * Recherche une présence spécifique (cours + étudiant)
     */
    Optional<Presence> findByCoursIdAndEtudiantId(Long coursId, Long etudiantId);

    /**
     * Vérifie si une présence existe pour un cours et un étudiant
     */
    boolean existsByCoursIdAndEtudiantId(Long coursId, Long etudiantId);

    /**
     * Compte les présences d'un étudiant par status
     */
    long countByEtudiantIdAndStatus(Long etudiantId, String status);

    /**
     * Compte le total de présences d'un étudiant
     */
    long countByEtudiantId(Long etudiantId);

    /**
     * Compte les absences d'un étudiant
     */
    @Query("SELECT COUNT(p) FROM Presence p WHERE p.etudiantId = :etudiantId AND p.status = 'ABSENT'")
    long countAbsencesByEtudiant(@Param("etudiantId") Long etudiantId);

    /**
     * Supprime toutes les présences d'un cours
     */
    void deleteByCoursId(Long coursId);
}
