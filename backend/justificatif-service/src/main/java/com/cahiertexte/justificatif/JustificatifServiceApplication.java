package com.cahiertexte.justificatif;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Point d'entrée du microservice de gestion des justificatifs
 * 
 * Ce service gère :
 * - La soumission de justificatifs d'absence (avec upload de fichiers)
 * - La validation/refus des justificatifs par les responsables
 * - Le suivi des justificatifs par étudiant
 * - Les statistiques des justificatifs
 * - La gestion des fichiers uploadés
 * 
 * @author Fatou Leye
 * @version 1.0.0
 */
@SpringBootApplication(scanBasePackages = {"com.cahiertexte.justificatif", "com.cahiertexte.common"})
@EnableFeignClients
public class JustificatifServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JustificatifServiceApplication.class, args);
        System.out.println("\n==============================================");
        System.out.println("🚀 JUSTIFICATIF SERVICE DÉMARRÉ AVEC SUCCÈS !");
        System.out.println("📝 Swagger UI: http://localhost:8085/api/justificatifs/swagger-ui.html");
        System.out.println("📚 API Docs: http://localhost:8085/api/justificatifs/api-docs");
        System.out.println("==============================================\n");
    }
}
