package com.cahiertexte.justificatif.controller;

import com.cahiertexte.justificatif.model.Justificatif;
import com.cahiertexte.justificatif.service.JustificatifService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/justificatifs")
public class JustificatifController {

    private final JustificatifService service;

    public JustificatifController(JustificatifService service) {
        this.service = service;
    }

    // GET /justificatifs
    @GetMapping
    public List<Justificatif> getAll() {
        return service.getAll();
    }

    // GET /justificatifs/{id}
    @GetMapping("/{id}")
    public Justificatif getById(@PathVariable Long id) {
        return service.getById(id).orElseThrow();
    }

    // POST /justificatifs
    @PostMapping
    public Justificatif submit(@RequestBody Justificatif justificatif) {
        return service.submit(justificatif);
    }

    // PUT /justificatifs/{id}/valider
    @PutMapping("/{id}/valider")
    public Justificatif valider(@PathVariable Long id) {
        return service.valider(id);
    }

    // PUT /justificatifs/{id}/refuser
    @PutMapping("/{id}/refuser")
    public Justificatif refuser(@PathVariable Long id) {
        return service.refuser(id);
    }

    // GET /justificatifs/etudiant/{id}
    @GetMapping("/etudiant/{id}")
    public List<Justificatif> getByEtudiant(@PathVariable Long id) {
        return service.getByEtudiant(id);
    }

    // GET /justificatifs/en-attente
    @GetMapping("/en-attente")
    public List<Justificatif> getEnAttente() {
        return service.getEnAttente();
    }
}
