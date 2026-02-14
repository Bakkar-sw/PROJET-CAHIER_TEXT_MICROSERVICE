package com.cahiertexte.presence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Point d'entrée du microservice de gestion des présences
 * 
 * Ce service gère :
 * - L'enregistrement des présences/absences/retards
 * - Les listes d'émargement par cours
 * - Le suivi des présences par étudiant
 * - Les statistiques de présence
 * - Les alertes absences (>= 3 absences)
 * 
 * @author Alioune Kebe
 * @version 1.0.0
 */
@SpringBootApplication(scanBasePackages = {"com.cahiertexte.presence", "com.cahiertexte.common"})
@EnableFeignClients
public class PresenceServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PresenceServiceApplication.class, args);
        System.out.println("\n==============================================");
        System.out.println("🚀 PRESENCE SERVICE DÉMARRÉ AVEC SUCCÈS !");
        System.out.println("📝 Swagger UI: http://localhost:8084/api/presences/swagger-ui.html");
        System.out.println("📚 API Docs: http://localhost:8084/api/presences/api-docs");
        System.out.println("==============================================\n");
    }
}
