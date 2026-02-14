package com.cahiertexte.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Point d'entrée du microservice de gestion des utilisateurs
 * 
 * Ce service gère :
 * - CRUD complet des utilisateurs (responsables, professeurs, étudiants)
 * - Gestion des rôles et des classes
 * - Activation/Désactivation des comptes
 * - Changement et réinitialisation des mots de passe
 * - Statistiques des utilisateurs
 * 
 * @author Ousmane Deme
 * @version 1.0.0
 */
@SpringBootApplication(scanBasePackages = {"com.cahiertexte.user", "com.cahiertexte.common"})
@EnableFeignClients
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
        System.out.println("\n==============================================");
        System.out.println("🚀 USER SERVICE DÉMARRÉ AVEC SUCCÈS !");
        System.out.println("📝 Swagger UI: http://localhost:8082/api/users/swagger-ui.html");
        System.out.println("📚 API Docs: http://localhost:8082/api/users/api-docs");
        System.out.println("==============================================\n");
    }
}
