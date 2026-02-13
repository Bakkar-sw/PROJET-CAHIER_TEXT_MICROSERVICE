package com.cahiertexte.cours.repository;

import com.cahiertexte.cours.model.Cours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository JPA pour l'entité Cours.
 *
 * @author Abdoulaye Guene
 */
@Repository
public interface CoursRepository extends JpaRepository<Cours, Long> {

    @Query("SELECT c FROM Cours c LEFT JOIN FETCH c.matiere WHERE c.id = :id")
    Optional<Cours> findByIdWithMatiere(@Param("id") Long id);

    List<Cours> findByClasse(String classe);

    List<Cours> findByProfesseurId(Long professeurId);

    List<Cours> findByStatus(String status);
}
