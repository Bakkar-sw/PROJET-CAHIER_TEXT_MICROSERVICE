package com.cahiertexte.cours.repository;

import com.cahiertexte.cours.model.Cours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository pour l'entité Cours
 */
@Repository
public interface CoursRepository extends JpaRepository<Cours, Long> {

    /**
     * Recherche les cours d'une classe
     */
    List<Cours> findByClasse(String classe);

    /**
     * Recherche les cours d'un professeur
     */
    List<Cours> findByProfesseurId(Long professeurId);

    /**
     * Recherche les cours d'une matière
     */
    List<Cours> findByMatiereId(Long matiereId);

    /**
     * Recherche les cours par status
     */
    List<Cours> findByStatus(String status);

    /**
     * Recherche les cours d'une classe par status
     */
    List<Cours> findByClasseAndStatus(String classe, String status);

    /**
     * Recherche les cours entre deux dates
     */
    List<Cours> findByDateCoursBetween(LocalDate dateDebut, LocalDate dateFin);

    /**
     * Recherche les cours d'une classe entre deux dates
     */
    List<Cours> findByClasseAndDateCoursBetween(String classe, LocalDate dateDebut, LocalDate dateFin);

    /**
     * Recherche les cours non validés d'un professeur
     */
    List<Cours> findByProfesseurIdAndValideParProf(Long professeurId, Boolean valideParProf);

    /**
     * Compte les cours d'une matière par status
     */
    long countByMatiereIdAndStatus(Long matiereId, String status);

    /**
     * Recherche les cours d'aujourd'hui pour une classe
     */
    @Query("SELECT c FROM Cours c WHERE c.classe = :classe AND c.dateCours = :date")
    List<Cours> findCoursOfToday(@Param("classe") String classe, @Param("date") LocalDate date);

    /**
     * Recherche les cours à venir pour un professeur
     */
    @Query("SELECT c FROM Cours c WHERE c.professeurId = :profId AND c.dateCours >= :date ORDER BY c.dateCours ASC")
    List<Cours> findCoursAVenir(@Param("profId") Long profId, @Param("date") LocalDate date);
}
