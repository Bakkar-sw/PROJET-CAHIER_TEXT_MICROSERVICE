package com.cahiertexte.auth.service;

import com.cahiertexte.auth.model.User;
import com.cahiertexte.auth.repository.UserRepository;
import com.cahiertexte.common.constants.AppConstants;
import com.cahiertexte.common.dto.AuthResponseDTO;
import com.cahiertexte.common.dto.LoginRequestDTO;
import com.cahiertexte.common.dto.UserDTO;
import com.cahiertexte.common.exception.BadRequestException;
import com.cahiertexte.common.exception.ResourceNotFoundException;
import com.cahiertexte.common.exception.UnauthorizedException;
import com.cahiertexte.common.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service de gestion de l'authentification
 */
@Service
@Transactional
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Authentifie un utilisateur et génère un token JWT
     */
    public AuthResponseDTO login(LoginRequestDTO loginRequest) {

        // Rechercher l'utilisateur
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UnauthorizedException(AppConstants.Messages.INVALID_CREDENTIALS));

        // Vérifier si le compte est actif
        if (!user.getActif()) {
            throw new UnauthorizedException("Compte désactivé. Contactez l'administrateur.");
        }

        // Vérification du mot de passe (BCrypt)
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
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

        response.setExpiresIn(86400000L); // 24h

        return response;
    }

    /**
     * Valide un token JWT
     */
    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }

    /**
     * Extrait les informations utilisateur d'un token
     */
    public UserDTO getUserFromToken(String token) {
        String username = jwtUtil.extractUsername(token);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.Messages.USER_NOT_FOUND));

        return convertToDTO(user);
    }

    /**
     * Inscrit un nouvel utilisateur
     */
    public UserDTO register(UserDTO userDTO) {

        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new BadRequestException("Le nom d'utilisateur existe déjà");
        }

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new BadRequestException("L'email existe déjà");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());

        // Hash du mot de passe avec BCrypt
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        user.setPrenom(userDTO.getPrenom());
        user.setNom(userDTO.getNom());
        user.setRole(userDTO.getRole());
        user.setClasse(userDTO.getClasse());
        user.setActif(true);

        User savedUser = userRepository.save(user);

        return convertToDTO(savedUser);
    }

    /**
     * Convertit User → UserDTO
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
        dto.hidePassword();

        return dto;
    }
}
