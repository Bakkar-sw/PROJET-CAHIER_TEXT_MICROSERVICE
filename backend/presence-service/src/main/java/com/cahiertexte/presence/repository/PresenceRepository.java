package com.cahiertexte.presence.repository;

import com.cahiertexte.presence.model.Presence;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PresenceRepository extends JpaRepository<Presence, Long> {

    List<Presence> findByCoursId(Long coursId);

    List<Presence> findByEtudiantId(Long etudiantId);
}
