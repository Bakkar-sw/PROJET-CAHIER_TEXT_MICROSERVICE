package com.cahiertexte.presence.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cahiertexte.presence.service.PresenceService;
import com.cahiertexte.presence.model.Presence;

@RestController
@RequestMapping("/presences")
public class PresenceController {

    private final PresenceService service;

    public PresenceController(PresenceService service) {
        this.service = service;
    }

    @GetMapping("/cours/{coursId}")
    public ResponseEntity<?> getByCours(@PathVariable Long coursId) {
        return ResponseEntity.ok(service.getByCours(coursId));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Presence presence) {
        return ResponseEntity.ok(service.save(presence));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody Presence presence) {
        return ResponseEntity.ok(service.update(id, presence));
    }

    @GetMapping("/etudiant/{etudiantId}")
    public ResponseEntity<?> getByEtudiant(@PathVariable Long etudiantId) {
        return ResponseEntity.ok(service.getByEtudiant(etudiantId));
    }

    @GetMapping("/stats/etudiant/{etudiantId}")
    public ResponseEntity<?> stats(@PathVariable Long etudiantId) {
        return ResponseEntity.ok(service.getStats(etudiantId));
    }
}
