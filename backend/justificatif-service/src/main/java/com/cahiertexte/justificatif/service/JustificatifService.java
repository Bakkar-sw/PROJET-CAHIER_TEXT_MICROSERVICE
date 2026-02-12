package com.cahiertexte.justificatif.service;

import com.cahiertexte.justificatif.model.Justificatif;
import com.cahiertexte.justificatif.repository.JustificatifRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import java.util.Optional;

@Service
public class JustificatifService {

    private final JustificatifRepository repository;

    public JustificatifService(JustificatifRepository repository) {
        this.repository = repository;
    }

    // 🔹 getAll
    public List<Justificatif> getAll() {
        return repository.findAll();
    }

    // 🔹 getById
    public Optional<Justificatif> getById(Long id) {
        return repository.findById(id);
    }

    // 🔹 submit
    public Justificatif submit(Justificatif justificatif) {
        justificatif.setStatus("EN_ATTENTE");
        justificatif.setDateSoumission(LocalDateTime.now());
        return repository.save(justificatif);
    }

    // 🔹 valider
    public Justificatif valider(Long id) {
        Justificatif j = repository.findById(id).orElseThrow();
        j.setStatus("VALIDE");
        j.setDateTraitement(LocalDateTime.now());
        return repository.save(j);
    }

    // 🔹 refuser
    public Justificatif refuser(Long id) {
        Justificatif j = repository.findById(id).orElseThrow();
        j.setStatus("REFUSE");
        j.setDateTraitement(LocalDateTime.now());
        return repository.save(j);
    }

    // 🔹 getByEtudiant
    public List<Justificatif> getByEtudiant(Long etudiantId) {
        return repository.findByEtudiantId(etudiantId);
    }

    // 🔹 getEnAttente
    public List<Justificatif> getEnAttente() {
        return repository.findByStatus("EN_ATTENTE");
    }
}
