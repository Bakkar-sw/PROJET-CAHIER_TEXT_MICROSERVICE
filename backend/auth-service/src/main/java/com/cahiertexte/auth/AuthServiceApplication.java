package com.cahiertexte.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Point d'entrée du microservice d'authentification
 * 
 * Ce service gère :
 * - L'authentification des utilisateurs
 * - La génération et validation des tokens JWT
 * - La gestion des sessions
 * 
 * @author Boubacar Souare
 * @version 1.0.0
 */
@SpringBootApplication
@EnableFeignClients
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
        System.out.println("\n==============================================");
        System.out.println("🚀 AUTH SERVICE DÉMARRÉ AVEC SUCCÈS !");
        System.out.println("📝 Swagger UI: http://localhost:8081/api/auth/swagger-ui.html");
        System.out.println("📚 API Docs: http://localhost:8081/api/auth/api-docs");
        System.out.println("==============================================\n");
    }
}
