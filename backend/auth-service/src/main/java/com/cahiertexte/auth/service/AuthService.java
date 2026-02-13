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

@Service
@Transactional
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ================= LOGIN =================
    public AuthResponseDTO login(LoginRequestDTO loginRequest) {

        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() ->
                        new UnauthorizedException(AppConstants.Messages.INVALID_CREDENTIALS)
                );

        if (!user.getActif()) {
            throw new UnauthorizedException("Compte désactivé.");
        }

        // ================= DEBUG PASSWORD =================
        System.out.println("========== DEBUG LOGIN ==========");
        System.out.println("Username        = " + loginRequest.getUsername());
        System.out.println("Password saisi  = " + loginRequest.getPassword());
        System.out.println("Hash en base    = " + user.getPassword());

        boolean passwordOk = passwordEncoder.matches(
                loginRequest.getPassword(),
                user.getPassword()
        );

        System.out.println("Password MATCH  = " + passwordOk);
        System.out.println("=================================");
        // =================================================

        if (!passwordOk) {
            System.out.println("❌ Mot de passe invalide pour : " + user.getUsername());
            throw new UnauthorizedException(AppConstants.Messages.INVALID_CREDENTIALS);
        }

        System.out.println("✅ Authentification OK : " + user.getUsername());

        String token = jwtUtil.generateToken(
                user.getUsername(),
                user.getId(),
                user.getRole()
        );

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

    // ================= VALIDATE TOKEN =================
    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }

    // ================= USER FROM TOKEN =================
    public UserDTO getUserFromToken(String token) {

        String username = jwtUtil.extractUsername(token);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(AppConstants.Messages.USER_NOT_FOUND)
                );

        return convertToDTO(user);
    }

    // ================= REGISTER =================
    public UserDTO register(UserDTO userDTO) {

        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new BadRequestException("Username existe déjà");
        }

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new BadRequestException("Email existe déjà");
        }

        User user = new User();

        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());

        // 🔐 Hash BCrypt
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        user.setPrenom(userDTO.getPrenom());
        user.setNom(userDTO.getNom());
        user.setRole(userDTO.getRole());
        user.setClasse(userDTO.getClasse());
        user.setActif(true);

        User savedUser = userRepository.save(user);

        return convertToDTO(savedUser);
    }

    // ================= CONVERT =================
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

    public UserDTO getUserByUsername(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserByUsername'");
    }
}
