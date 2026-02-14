package com.cahiertexte.user.repository;

import com.cahiertexte.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository pour l'entité User
 * Spring Data JPA génère automatiquement l'implémentation
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Recherche un utilisateur par son username
     */
    Optional<User> findByUsername(String username);

    /**
     * Recherche un utilisateur par son email
     */
    Optional<User> findByEmail(String email);

    /**
     * Vérifie si un username existe déjà
     */
    boolean existsByUsername(String username);

    /**
     * Vérifie si un email existe déjà
     */
    boolean existsByEmail(String email);

    /**
     * Recherche un utilisateur actif par username
     */
    Optional<User> findByUsernameAndActif(String username, Boolean actif);

    /**
     * Recherche les utilisateurs par rôle
     */
    List<User> findByRole(String role);

    /**
     * Recherche les utilisateurs d'une classe
     */
    List<User> findByClasse(String classe);

    /**
     * Recherche les utilisateurs par statut actif
     */
    List<User> findByActif(Boolean actif);

    /**
     * Recherche les utilisateurs par rôle et classe
     */
    List<User> findByRoleAndClasse(String role, String classe);

    /**
     * Compte les utilisateurs par rôle
     */
    long countByRole(String role);

    /**
     * Compte les utilisateurs actifs
     */
    long countByActif(Boolean actif);
}
