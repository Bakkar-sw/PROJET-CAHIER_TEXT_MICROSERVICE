package com.cahiertexte.stats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Point d'entrée du microservice de statistiques
 * 
 * Ce service gère :
 * - Les statistiques de présence des étudiants
 * - Les statistiques globales par classe
 * - Les alertes (absences critiques, matières < 12h)
 * - Le dashboard pour le responsable de formation
 * 
 * NOTE: Ce service N'A PAS de base de données propre
 * Il appelle les autres microservices via OpenFeign pour agréger les données
 * 
 * @author Cheikh Tjian Diaw
 * @version 1.0.0
 */
@SpringBootApplication(scanBasePackages = {"com.cahiertexte.stats", "com.cahiertexte.common"})
@EnableFeignClients
public class StatsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatsServiceApplication.class, args);
        System.out.println("\n==============================================");
        System.out.println("🚀 STATS SERVICE DÉMARRÉ AVEC SUCCÈS !");
        System.out.println("📝 Swagger UI: http://localhost:8086/api/stats/swagger-ui.html");
        System.out.println("📚 API Docs: http://localhost:8086/api/stats/api-docs");
        System.out.println("==============================================\n");
    }
}
