package com.cahiertexte.justificatif.repository;

import com.cahiertexte.justificatif.model.Justificatif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JustificatifRepository extends JpaRepository<Justificatif, Long> {

    List<Justificatif> findByEtudiantId(Long etudiantId);

    List<Justificatif> findByCoursId(Long coursId);

    List<Justificatif> findByStatus(String status);
}
