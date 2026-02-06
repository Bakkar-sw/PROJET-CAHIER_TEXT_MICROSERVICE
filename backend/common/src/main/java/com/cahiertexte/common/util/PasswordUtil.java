package com.cahiertexte.common.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utilitaire pour le hashage des mots de passe
 * Utilise SHA-256 comme dans le projet original
 * 
 * NOTE: Pour la production, il est recommandé d'utiliser BCrypt
 * Cette implémentation est pour la compatibilité avec l'ancien système
 */
public class PasswordUtil {

    private static final String ALGORITHM = "SHA-256";

    /**
     * Hash un mot de passe avec SHA-256
     * 
     * @param password Le mot de passe en clair
     * @return Le hash en hexadécimal
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erreur lors du hashage du mot de passe", e);
        }
    }

    /**
     * Vérifie si un mot de passe correspond au hash
     * 
     * @param password Le mot de passe en clair
     * @param hashedPassword Le hash stocké
     * @return true si le mot de passe correspond
     */
    public static boolean verifyPassword(String password, String hashedPassword) {
        String hashOfInput = hashPassword(password);
        return hashOfInput.equals(hashedPassword);
    }

    /**
     * Convertit un tableau de bytes en chaîne hexadécimale
     */
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * Génère un mot de passe aléatoire
     * Utile pour la réinitialisation de mot de passe
     */
    public static String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%";
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * chars.length());
            password.append(chars.charAt(index));
        }
        return password.toString();
    }
}
