package com.cahiertexte.common.constants;

/**
 * Constantes communes à tous les microservices
 */
public final class AppConstants {

    private AppConstants() {
        // Classe utilitaire, pas d'instanciation
    }

    // Rôles utilisateurs
    public static final class Roles {
        public static final String RESPONSABLE_FORMATION = "RESPONSABLE_FORMATION";
        public static final String RESPONSABLE_CLASSE = "RESPONSABLE_CLASSE";
        public static final String PROFESSEUR = "PROFESSEUR";
        public static final String ETUDIANT = "ETUDIANT";
    }

    // Classes
    public static final class Classes {
        public static final String CI_M1 = "CI_M1";
        public static final String CI_M2 = "CI_M2";
        public static final String MCS_M1 = "MCS_M1";
        public static final String MCS_M2 = "MCS_M2";
    }

    // Statuts
    public static final class Status {
        public static final String PLANIFIE = "PLANIFIE";
        public static final String VALIDE = "VALIDE";
        public static final String TERMINE = "TERMINE";
        public static final String ANNULE = "ANNULE";
    }

    // Types de présence
    public static final class PresenceType {
        public static final String PRESENT = "PRESENT";
        public static final String ABSENT = "ABSENT";
        public static final String RETARD = "RETARD";
    }

    // Statuts de justificatif
    public static final class JustificatifStatus {
        public static final String EN_ATTENTE = "EN_ATTENTE";
        public static final String ACCEPTE = "ACCEPTE";
        public static final String REFUSE = "REFUSE";
    }

    // JWT
    public static final class Jwt {
        public static final String SECRET_KEY_DEFAULT = "votre-cle-secrete-ultra-secure-minimum-256-bits-pour-HS256-algorithm";
        public static final long EXPIRATION_TIME = 86400000; // 24 heures en millisecondes
        public static final String TOKEN_PREFIX = "Bearer ";
        public static final String HEADER_STRING = "Authorization";
    }

    // Pagination
    public static final class Pagination {
        public static final int DEFAULT_PAGE_SIZE = 10;
        public static final int MAX_PAGE_SIZE = 100;
    }

    // Messages
    public static final class Messages {
        public static final String USER_NOT_FOUND = "Utilisateur non trouvé";
        public static final String INVALID_CREDENTIALS = "Identifiants invalides";
        public static final String ACCESS_DENIED = "Accès refusé";
        public static final String UNAUTHORIZED = "Non autorisé";
        public static final String COURS_NOT_FOUND = "Cours non trouvé";
        public static final String MATIERE_NOT_FOUND = "Matière non trouvée";
        public static final String PRESENCE_NOT_FOUND = "Présence non trouvée";
        public static final String JUSTIFICATIF_NOT_FOUND = "Justificatif non trouvé";
    }

    // Alertes
    public static final class Alerts {
        public static final int HEURES_RESTANTES_SEUIL = 12;
        public static final int ABSENCES_CRITIQUES_SEUIL = 3;
    }
}
