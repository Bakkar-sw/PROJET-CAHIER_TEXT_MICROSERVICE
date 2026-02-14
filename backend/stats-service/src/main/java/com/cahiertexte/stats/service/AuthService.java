package com.cahiertexte.stats.service;

import com.cahiertexte.stats.model.User;
import com.cahiertexte.stats.repository.UserRepository;
import com.cahiertexte.common.constants.AppConstants;
import com.cahiertexte.common.dto.AuthResponseDTO;
import com.cahiertexte.common.dto.LoginRequestDTO;
import com.cahiertexte.common.dto.UserDTO;
import com.cahiertexte.common.exception.BadRequestException;
import com.cahiertexte.common.exception.ResourceNotFoundException;
import com.cahiertexte.common.exception.UnauthorizedException;
import com.cahiertexte.common.util.JwtUtil;
import com.cahiertexte.common.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service de gestion de l'authentification
 * 
 * @author Boubacar Souare
 */
@Service
@Transactional
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Authentifie un utilisateur et génère un token JWT
     * 
     * @param loginRequest Les identifiants de connexion
     * @return La réponse d'authentification avec le token
     */
    public AuthResponseDTO login(LoginRequestDTO loginRequest) {
        // Rechercher l'utilisateur
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UnauthorizedException(AppConstants.Messages.INVALID_CREDENTIALS));

        // Vérifier si le compte est actif
        if (!user.getActif()) {
            throw new UnauthorizedException("Compte désactivé. Contactez l'administrateur.");
        }

        // Vérifier le mot de passe
        if (!PasswordUtil.verifyPassword(loginRequest.getPassword(), user.getPassword())) {
            throw new UnauthorizedException(AppConstants.Messages.INVALID_CREDENTIALS);
        }

        // Générer le token JWT
        String token = jwtUtil.generateToken(user.getUsername(), user.getId(), user.getRole());

        // Construire la réponse
        AuthResponseDTO response = new AuthResponseDTO(
                token,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPrenom(),
                user.getNom(),
                user.getRole(),
                user.getClasse()
        );
        response.setExpiresIn(86400000L); // 24 heures

        return response;
    }

    /**
     * Valide un token JWT
     * 
     * @param token Le token à valider
     * @return true si le token est valide
     */
    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }

    /**
     * Extrait les informations utilisateur d'un token
     * 
     * @param token Le token JWT
     * @return Les informations utilisateur
     */
    public UserDTO getUserFromToken(String token) {
        String username = jwtUtil.extractUsername(token);
        
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.Messages.USER_NOT_FOUND));

        return convertToDTO(user);
    }

    /**
     * Inscrit un nouvel utilisateur
     * 
     * @param userDTO Les informations du nouvel utilisateur
     * @return L'utilisateur créé
     */
    public UserDTO register(UserDTO userDTO) {
        // Vérifier si le username existe déjà
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new BadRequestException("Le nom d'utilisateur existe déjà");
        }

        // Vérifier si l'email existe déjà
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new BadRequestException("L'email existe déjà");
        }

        // Créer le nouvel utilisateur
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
        
        return convertToDTO(savedUser);
    }

    /**
     * Convertit une entité User en UserDTO
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
