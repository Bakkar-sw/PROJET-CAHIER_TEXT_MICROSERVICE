package com.cahiertexte.justificatif.repository;

import com.cahiertexte.justificatif.model.Justificatif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour l'entité Justificatif
 */
@Repository
public interface JustificatifRepository extends JpaRepository<Justificatif, Long> {

    /**
     * Recherche les justificatifs d'un étudiant
     */
    List<Justificatif> findByEtudiantId(Long etudiantId);

    /**
     * Recherche les justificatifs d'un cours
     */
    List<Justificatif> findByCoursId(Long coursId);

    /**
     * Recherche les justificatifs par status
     */
    List<Justificatif> findByStatus(String status);

    /**
     * Recherche les justificatifs d'un étudiant par status
     */
    List<Justificatif> findByEtudiantIdAndStatus(Long etudiantId, String status);

    /**
     * Recherche les justificatifs en attente
     */
    List<Justificatif> findByStatusOrderByDateSoumissionAsc(String status);

    /**
     * Compte les justificatifs d'un étudiant par status
     */
    long countByEtudiantIdAndStatus(Long etudiantId, String status);

    /**
     * Compte les justificatifs en attente
     */
    long countByStatus(String status);
}
