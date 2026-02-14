package com.cahiertexte.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Point d'entrée de l'API Gateway
 * 
 * L'API Gateway est le point d'entrée unique pour tous les clients.
 * Il route les requêtes vers les microservices appropriés et gère :
 * - Le routage centralisé
 * - L'authentification JWT
 * - La configuration CORS
 * - La transformation des requêtes/réponses
 * 
 * Routes disponibles :
 * - /api/auth/** → Auth Service (8081)
 * - /api/users/** → User Service (8082)
 * - /api/cours/** → Cours Service (8083)
 * - /api/matieres/** → Cours Service/Matières (8083)
 * - /api/presences/** → Presence Service (8084)
 * - /api/justificatifs/** → Justificatif Service (8085)
 * - /api/stats/** → Stats Service (8086)
 * 
 * @author Équipe TDSI
 * @version 1.0.0
 */
@SpringBootApplication(scanBasePackages = {"com.cahiertexte.gateway", "com.cahiertexte.common"})
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
        System.out.println("\n==============================================");
        System.out.println("🚀 API GATEWAY DÉMARRÉ AVEC SUCCÈS !");
        System.out.println("🌐 Gateway URL: http://localhost:8080");
        System.out.println("📋 Routes: http://localhost:8080/routes");
        System.out.println("ℹ️  Info: http://localhost:8080/info");
        System.out.println("==============================================");
        System.out.println("\n📡 SERVICES DISPONIBLES:");
        System.out.println("  • Auth:          /api/auth/**");
        System.out.println("  • Users:         /api/users/**");
        System.out.println("  • Cours:         /api/cours/**");
        System.out.println("  • Matières:      /api/matieres/**");
        System.out.println("  • Présences:     /api/presences/**");
        System.out.println("  • Justificatifs: /api/justificatifs/**");
        System.out.println("  • Stats:         /api/stats/**");
        System.out.println("==============================================\n");
    }
}
