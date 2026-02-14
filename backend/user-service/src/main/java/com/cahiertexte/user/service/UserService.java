package com.cahiertexte.user.service;

import com.cahiertexte.common.constants.AppConstants;
import com.cahiertexte.common.dto.UserDTO;
import com.cahiertexte.common.exception.BadRequestException;
import com.cahiertexte.common.exception.ResourceNotFoundException;
import com.cahiertexte.common.util.PasswordUtil;
import com.cahiertexte.user.model.User;
import com.cahiertexte.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service de gestion des utilisateurs
 * 
 * @author Ousmane Deme
 */
@Service
@Transactional
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    /**
     * Récupère tous les utilisateurs
     */
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère un utilisateur par son ID
     */
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.Messages.USER_NOT_FOUND));
        return convertToDTO(user);
    }

    /**
     * Récupère un utilisateur par son username
     */
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.Messages.USER_NOT_FOUND));
        return convertToDTO(user);
    }

    /**
     * Récupère les utilisateurs par rôle
     */
    public List<UserDTO> getUsersByRole(String role) {
        return userRepository.findByRole(role).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère les utilisateurs d'une classe
     */
    public List<UserDTO> getUsersByClasse(String classe) {
        return userRepository.findByClasse(classe).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère les utilisateurs actifs
     */
    public List<UserDTO> getActiveUsers() {
        return userRepository.findByActif(true).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère tous les professeurs
     */
    public List<UserDTO> getProfesseurs() {
        return getUsersByRole(AppConstants.Roles.PROFESSEUR);
    }

    /**
     * Récupère tous les étudiants
     */
    public List<UserDTO> getEtudiants() {
        return getUsersByRole(AppConstants.Roles.ETUDIANT);
    }

    /**
     * Récupère les étudiants d'une classe
     */
    public List<UserDTO> getEtudiantsByClasse(String classe) {
        return userRepository.findByRoleAndClasse(AppConstants.Roles.ETUDIANT, classe).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Crée un nouvel utilisateur
     */
    public UserDTO createUser(UserDTO userDTO) {
        logger.info("Création d'un nouvel utilisateur: {}", userDTO.getUsername());

        // Vérifier si le username existe déjà
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new BadRequestException("Un utilisateur avec ce nom d'utilisateur existe déjà");
        }

        // Vérifier si l'email existe déjà
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new BadRequestException("Un utilisateur avec cet email existe déjà");
        }

        // Valider le rôle
        validateRole(userDTO.getRole());

        // Valider la classe pour les étudiants
        if (AppConstants.Roles.ETUDIANT.equals(userDTO.getRole())) {
            if (userDTO.getClasse() == null || userDTO.getClasse().trim().isEmpty()) {
                throw new BadRequestException("La classe est obligatoire pour un étudiant");
            }
            validateClasse(userDTO.getClasse());
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(PasswordUtil.hashPassword(userDTO.getPassword()));
        user.setPrenom(userDTO.getPrenom());
        user.setNom(userDTO.getNom());
        user.setRole(userDTO.getRole());
        user.setClasse(userDTO.getClasse());
        user.setActif(true);

        User savedUser = userRepository.save(user);
        logger.info("Utilisateur créé avec succès: ID {}", savedUser.getId());

        return convertToDTO(savedUser);
    }

    /**
     * Met à jour un utilisateur
     */
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        logger.info("Mise à jour de l'utilisateur ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.Messages.USER_NOT_FOUND));

        // Vérifier si le username est modifié et s'il existe déjà
        if (!user.getUsername().equals(userDTO.getUsername())) {
            if (userRepository.existsByUsername(userDTO.getUsername())) {
                throw new BadRequestException("Un utilisateur avec ce nom d'utilisateur existe déjà");
            }
        }

        // Vérifier si l'email est modifié et s'il existe déjà
        if (!user.getEmail().equals(userDTO.getEmail())) {
            if (userRepository.existsByEmail(userDTO.getEmail())) {
                throw new BadRequestException("Un utilisateur avec cet email existe déjà");
            }
        }

        // Valider le rôle
        validateRole(userDTO.getRole());

        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPrenom(userDTO.getPrenom());
        user.setNom(userDTO.getNom());
        user.setRole(userDTO.getRole());
        user.setClasse(userDTO.getClasse());

        User updatedUser = userRepository.save(user);
        logger.info("Utilisateur mis à jour avec succès: ID {}", id);

        return convertToDTO(updatedUser);
    }

    /**
     * Change le mot de passe d'un utilisateur
     */
    public void changePassword(Long id, String oldPassword, String newPassword) {
        logger.info("Changement de mot de passe pour l'utilisateur ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.Messages.USER_NOT_FOUND));

        // Vérifier l'ancien mot de passe
        if (!PasswordUtil.verifyPassword(oldPassword, user.getPassword())) {
            throw new BadRequestException("Ancien mot de passe incorrect");
        }

        // Valider le nouveau mot de passe
        if (newPassword == null || newPassword.length() < 6) {
            throw new BadRequestException("Le nouveau mot de passe doit contenir au moins 6 caractères");
        }

        user.setPassword(PasswordUtil.hashPassword(newPassword));
        userRepository.save(user);

        logger.info("Mot de passe changé avec succès pour l'utilisateur ID: {}", id);
    }

    /**
     * Réinitialise le mot de passe d'un utilisateur (par un admin)
     */
    public String resetPassword(Long id) {
        logger.info("Réinitialisation du mot de passe pour l'utilisateur ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.Messages.USER_NOT_FOUND));

        String newPassword = PasswordUtil.generateRandomPassword(10);
        user.setPassword(PasswordUtil.hashPassword(newPassword));
        userRepository.save(user);

        logger.info("Mot de passe réinitialisé pour l'utilisateur ID: {}", id);

        return newPassword;
    }

    /**
     * Active/Désactive un utilisateur
     */
    public UserDTO toggleUserStatus(Long id) {
        logger.info("Changement de statut pour l'utilisateur ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.Messages.USER_NOT_FOUND));

        user.setActif(!user.getActif());
        User updatedUser = userRepository.save(user);

        logger.info("Statut changé pour l'utilisateur ID: {} - Actif: {}", id, updatedUser.getActif());

        return convertToDTO(updatedUser);
    }

    /**
     * Supprime un utilisateur (désactivation logique)
     */
    public void deleteUser(Long id) {
        logger.info("Suppression de l'utilisateur ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.Messages.USER_NOT_FOUND));

        user.setActif(false);
        userRepository.save(user);

        logger.info("Utilisateur désactivé avec succès: ID {}", id);
    }

    /**
     * Statistiques des utilisateurs
     */
    public java.util.Map<String, Object> getUserStats() {
        long totalUsers = userRepository.count();
        long activeUsers = userRepository.countByActif(true);
        long professeurs = userRepository.countByRole(AppConstants.Roles.PROFESSEUR);
        long etudiants = userRepository.countByRole(AppConstants.Roles.ETUDIANT);
        long responsables = userRepository.countByRole(AppConstants.Roles.RESPONSABLE_CLASSE) + 
                           userRepository.countByRole(AppConstants.Roles.RESPONSABLE_FORMATION);

        java.util.Map<String, Object> stats = new java.util.HashMap<>();
        stats.put("totalUsers", totalUsers);
        stats.put("activeUsers", activeUsers);
        stats.put("professeurs", professeurs);
        stats.put("etudiants", etudiants);
        stats.put("responsables", responsables);

        return stats;
    }

    /**
     * Valide un rôle
     */
    private void validateRole(String role) {
        List<String> validRoles = List.of(
                AppConstants.Roles.RESPONSABLE_FORMATION,
                AppConstants.Roles.RESPONSABLE_CLASSE,
                AppConstants.Roles.PROFESSEUR,
                AppConstants.Roles.ETUDIANT
        );

        if (!validRoles.contains(role)) {
            throw new BadRequestException("Rôle invalide: " + role);
        }
    }

    /**
     * Valide une classe
     */
    private void validateClasse(String classe) {
        List<String> validClasses = List.of(
                AppConstants.Classes.CI_M1,
                AppConstants.Classes.CI_M2,
                AppConstants.Classes.MCS_M1,
                AppConstants.Classes.MCS_M2
        );

        if (!validClasses.contains(classe)) {
            throw new BadRequestException("Classe invalide: " + classe);
        }
    }

    /**
     * Convertit une entité User en DTO
     */
    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPrenom(),
                user.getNom(),
                user.getRole(),
                user.getClasse()
        );
        dto.setActif(user.getActif());
        dto.setDateCreation(user.getDateCreation());
        dto.setDateModification(user.getDateModification());
        dto.hidePassword(); // Important : masquer le mot de passe
        return dto;
    }
}
