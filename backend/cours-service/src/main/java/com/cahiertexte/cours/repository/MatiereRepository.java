package com.cahiertexte.cours.repository;

import com.cahiertexte.cours.model.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository JPA pour l'entité Matière.
 *
 * @author Abdoulaye Guene
 */
@Repository
public interface MatiereRepository extends JpaRepository<Matiere, Long> {

    Optional<Matiere> findByCode(String code);

    List<Matiere> findByProfesseurId(Long professeurId);

    List<Matiere> findByClasse(String classe);

    List<Matiere> findByActifTrue();
}
