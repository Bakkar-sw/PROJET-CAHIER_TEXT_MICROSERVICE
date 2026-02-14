package com.cahiertexte.stats.client;

import com.cahiertexte.common.dto.ApiResponseDTO;
import com.cahiertexte.common.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * Client Feign pour communiquer avec le User Service
 */
@FeignClient(name = "user-service", url = "${feign.client.config.user-service.url:http://localhost:8082}/api/users")
public interface UserServiceClient {

    /**
     * Récupère tous les utilisateurs
     */
    @GetMapping("/")
    ApiResponseDTO<List<UserDTO>> getAllUsers(@RequestHeader("Authorization") String token);

    /**
     * Récupère un utilisateur par son ID
     */
    @GetMapping("/{id}")
    ApiResponseDTO<UserDTO> getUserById(@PathVariable Long id, @RequestHeader("Authorization") String token);

    /**
     * Récupère les utilisateurs par rôle
     */
    @GetMapping("/role/{role}")
    ApiResponseDTO<List<UserDTO>> getUsersByRole(@PathVariable String role, @RequestHeader("Authorization") String token);

    /**
     * Récupère les utilisateurs par classe
     */
    @GetMapping("/classe/{classe}")
    ApiResponseDTO<List<UserDTO>> getUsersByClasse(@PathVariable String classe, @RequestHeader("Authorization") String token);
}
