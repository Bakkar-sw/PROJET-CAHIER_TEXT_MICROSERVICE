package com.cahiertexte.cours;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Point d'entrée du microservice de gestion des cours et matières
 * 
 * Ce service gère :
 * - Les matières enseignées (création, modification, suivi)
 * - Les cours/séances (planification, cahier de texte, validation)
 * - Le suivi du volume horaire des matières
 * - Les alertes sur les matières (< 12h restantes)
 * 
 * @author Abdoulaye Guene
 * @version 1.0.0
 */
@SpringBootApplication(scanBasePackages = {"com.cahiertexte.cours", "com.cahiertexte.common"})
@EnableFeignClients
public class CoursServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoursServiceApplication.class, args);
        System.out.println("\n==============================================");
        System.out.println("🚀 COURS SERVICE DÉMARRÉ AVEC SUCCÈS !");
        System.out.println("📝 Swagger UI: http://localhost:8083/api/cours/swagger-ui.html");
        System.out.println("📚 API Docs: http://localhost:8083/api/cours/api-docs");
        System.out.println("==============================================\n");
    }
}
