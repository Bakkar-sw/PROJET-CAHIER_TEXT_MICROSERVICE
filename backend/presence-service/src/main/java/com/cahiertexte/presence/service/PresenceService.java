package com.cahiertexte.presence.service;

import org.springframework.stereotype.Service;
import com.cahiertexte.presence.repository.PresenceRepository;
import com.cahiertexte.presence.model.*;

import java.util.*;

@Service
public class PresenceService {

    private final PresenceRepository repository;

    // Constructeur d'injection manuel
    public PresenceService(PresenceRepository repository) {
        this.repository = repository;
    }

    public List<Presence> getByCours(Long coursId) {
        return repository.findByCoursId(coursId);
    }

    public List<Presence> getByEtudiant(Long etudiantId) {
        return repository.findByEtudiantId(etudiantId);
    }

    public Presence save(Presence presence) {
        return repository.save(presence);
    }

    public Presence update(Long id, Presence updated) {

        Presence existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Presence non trouvée"));

        existing.setStatus(updated.getStatus());
        existing.setRemarque(updated.getRemarque());

        return repository.save(existing);
    }

    public Map<String, Object> getStats(Long etudiantId) {

        List<Presence> presences = repository.findByEtudiantId(etudiantId);

        long present = presences.stream()
                .filter(p -> p.getStatus() == PresenceStatus.PRESENT)
                .count();

        long absent = presences.stream()
                .filter(p -> p.getStatus() == PresenceStatus.ABSENT)
                .count();

        long retard = presences.stream()
                .filter(p -> p.getStatus() == PresenceStatus.RETARD)
                .count();

        Map<String, Object> stats = new HashMap<>();
        stats.put("present", present);
        stats.put("absent", absent);
        stats.put("retard", retard);
        stats.put("total", presences.size());

        return stats;
    }
}
