package com.cahiertexte.gateway.filter;

import com.cahiertexte.common.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Filtre JWT pour valider les tokens sur les routes protégées
 */
@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    public JwtAuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            
            // Vérifier si la route nécessite une authentification
            String path = request.getPath().toString();
            
            // Les endpoints /health ne nécessitent pas d'authentification
            if (path.endsWith("/health")) {
                return chain.filter(exchange);
            }

            // Extraire le token du header Authorization
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                logger.warn("Token manquant pour la requête: {}", path);
                return onError(exchange, "Token d'authentification manquant", HttpStatus.UNAUTHORIZED);
            }

            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                logger.warn("Format du token invalide pour: {}", path);
                return onError(exchange, "Format du token invalide", HttpStatus.UNAUTHORIZED);
            }

            String token = authHeader.substring(7);

            try {
                // Valider le token
                if (!jwtUtil.validateToken(token)) {
                    logger.warn("Token invalide ou expiré pour: {}", path);
                    return onError(exchange, "Token invalide ou expiré", HttpStatus.UNAUTHORIZED);
                }

                // Extraire les informations du token et les ajouter aux headers
                String username = jwtUtil.extractUsername(token);
                String role = jwtUtil.extractRole(token);

                // Ajouter les informations au header pour les services en aval
                ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                        .header("X-User-Username", username)
                        .header("X-User-Role", role)
                        .build();

                logger.debug("Token validé pour l'utilisateur: {} ({})", username, role);

                return chain.filter(exchange.mutate().request(modifiedRequest).build());

            } catch (Exception e) {
                logger.error("Erreur lors de la validation du token: {}", e.getMessage());
                return onError(exchange, "Erreur de validation du token", HttpStatus.UNAUTHORIZED);
            }
        };
    }

    /**
     * Gestion des erreurs
     */
    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json");
        
        String errorBody = String.format("{\"success\":false,\"message\":\"%s\"}", message);
        
        return response.writeWith(Mono.just(response.bufferFactory().wrap(errorBody.getBytes())));
    }

    public static class Config {
        // Configuration du filtre si nécessaire
    }
}
