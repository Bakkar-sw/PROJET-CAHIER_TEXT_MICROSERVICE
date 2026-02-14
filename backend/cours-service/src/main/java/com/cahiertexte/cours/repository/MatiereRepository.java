package com.cahiertexte.cours.repository;

import com.cahiertexte.cours.model.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour l'entité Matiere
 */
@Repository
public interface MatiereRepository extends JpaRepository<Matiere, Long> {

    /**
     * Recherche une matière par son code
     */
    Optional<Matiere> findByCode(String code);

    /**
     * Recherche les matières d'une classe
     */
    List<Matiere> findByClasse(String classe);

    /**
     * Recherche les matières d'un professeur
     */
    List<Matiere> findByProfesseurId(Long professeurId);

    /**
     * Recherche les matières actives d'une classe
     */
    List<Matiere> findByClasseAndActif(String classe, Boolean actif);

    /**
     * Vérifie si un code existe déjà
     */
    boolean existsByCode(String code);

    /**
     * Recherche les matières en alerte (heures restantes < 12)
     */
    @Query("SELECT m FROM Matiere m WHERE (m.volumeHoraire - m.volumeRealise) < 12 " +
           "AND (m.volumeHoraire - m.volumeRealise) > 0 AND m.actif = true")
    List<Matiere> findMatieresEnAlerte();

    /**
     * Recherche les matières critiques (heures restantes < 6)
     */
    @Query("SELECT m FROM Matiere m WHERE (m.volumeHoraire - m.volumeRealise) < 6 " +
           "AND (m.volumeHoraire - m.volumeRealise) > 0 AND m.actif = true")
    List<Matiere> findMatieresCritiques();
}
