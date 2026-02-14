package com.cahiertexte.common.util;

import com.cahiertexte.common.constants.AppConstants;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Utilitaire pour la gestion des tokens JWT
 * Utilisé par tous les microservices pour générer et valider les tokens
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret:votre-cle-secrete-ultra-secure-minimum-256-bits-pour-HS256-algorithm}")
    private String secret;

    @Value("${jwt.expiration:86400000}") // 24h par défaut
    private Long expiration;

    /**
     * Génère une clé secrète à partir de la chaîne de configuration
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Génère un token JWT pour un utilisateur
     */
    public String generateToken(String username, Long userId, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("role", role);
        return createToken(claims, username);
    }

    /**
     * Crée le token avec les claims fournis
     */
    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Extrait le username du token
     */
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    /**
     * Extrait l'ID utilisateur du token
     */
    public Long extractUserId(String token) {
        return extractClaims(token).get("userId", Long.class);
    }

    /**
     * Extrait le rôle du token
     */
    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

    /**
     * Extrait la date d'expiration du token
     */
    public Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }

    /**
     * Extrait tous les claims du token
     */
    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Vérifie si le token est expiré
     */
    public Boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Valide le token
     */
    public Boolean validateToken(String token, String username) {
        try {
            final String extractedUsername = extractUsername(token);
            return (extractedUsername.equals(username) && !isTokenExpired(token));
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Valide le token sans vérifier le username
     */
    public Boolean validateToken(String token) {
        try {
            extractClaims(token);
            return !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Extrait le token du header Authorization
     */
    public String extractTokenFromHeader(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith(AppConstants.Jwt.TOKEN_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
