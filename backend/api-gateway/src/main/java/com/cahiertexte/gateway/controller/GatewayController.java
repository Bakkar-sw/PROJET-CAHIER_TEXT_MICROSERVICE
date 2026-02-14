package com.cahiertexte.gateway.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Contrôleur pour les informations de la Gateway
 */
@RestController
@RequestMapping("/")
public class GatewayController {

    @Value("${services.auth.url}")
    private String authServiceUrl;

    @Value("${services.user.url}")
    private String userServiceUrl;

    @Value("${services.cours.url}")
    private String coursServiceUrl;

    @Value("${services.presence.url}")
    private String presenceServiceUrl;

    @Value("${services.justificatif.url}")
    private String justificatifServiceUrl;

    @Value("${services.stats.url}")
    private String statsServiceUrl;

    /**
     * Health check de la gateway
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "API Gateway is running");
        response.put("port", 8080);
        return ResponseEntity.ok(response);
    }

    /**
     * Informations sur les routes disponibles
     */
    @GetMapping("/routes")
    public ResponseEntity<Map<String, Object>> getRoutes() {
        Map<String, Object> routes = new HashMap<>();
        
        Map<String, String> services = new HashMap<>();
        services.put("auth", authServiceUrl + "/api/auth");
        services.put("users", userServiceUrl + "/api/users");
        services.put("cours", coursServiceUrl + "/api/cours");
        services.put("matieres", coursServiceUrl + "/api/cours/matieres");
        services.put("presences", presenceServiceUrl + "/api/presences");
        services.put("justificatifs", justificatifServiceUrl + "/api/justificatifs");
        services.put("stats", statsServiceUrl + "/api/stats");

        routes.put("success", true);
        routes.put("gateway", "http://localhost:8080");
        routes.put("services", services);
        routes.put("documentation", "Utilisez /api/{service}/** pour accéder aux microservices");

        return ResponseEntity.ok(routes);
    }

    /**
     * Informations de version
     */
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> info() {
        Map<String, Object> info = new HashMap<>();
        info.put("application", "Cahier de Texte - API Gateway");
        info.put("version", "1.0.0");
        info.put("description", "Point d'entrée centralisé pour tous les microservices");
        info.put("services", 6);
        
        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("health", "GET /health");
        endpoints.put("routes", "GET /routes");
        endpoints.put("info", "GET /info");
        
        info.put("endpoints", endpoints);

        return ResponseEntity.ok(info);
    }
}
