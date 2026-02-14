package com.cahiertexte.stats.service;

import com.cahiertexte.common.constants.AppConstants;
import com.cahiertexte.common.dto.ApiResponseDTO;
import com.cahiertexte.common.dto.UserDTO;
import com.cahiertexte.stats.client.PresenceServiceClient;
import com.cahiertexte.stats.client.UserServiceClient;
import com.cahiertexte.stats.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service de gestion des statistiques
 * Ce service agrège les données des autres microservices
 * 
 * @author Cheikh Tjian Diaw
 */
@Service
public class StatsService {

    private static final Logger logger = LoggerFactory.getLogger(StatsService.class);

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private PresenceServiceClient presenceServiceClient;

    // TODO: Décommenter quand les autres services seront disponibles
    // @Autowired
    // private CoursServiceClient coursServiceClient;
    // 
    // @Autowired
    // private JustificatifServiceClient justificatifServiceClient;

    /**
     * Récupère les statistiques d'un étudiant
     */
    public StatEtudiantDTO getStatsEtudiant(Long etudiantId, String token) {
        logger.info("Récupération des stats pour l'étudiant ID: {}", etudiantId);

        StatEtudiantDTO stats = new StatEtudiantDTO();
        
        try {
            // 1. Récupérer les infos de l'étudiant
            ApiResponseDTO<UserDTO> userResponse = userServiceClient.getUserById(etudiantId, token);
            if (userResponse.getData() != null) {
                UserDTO user = userResponse.getData();
                stats.setEtudiantId(user.getId());
                stats.setUsername(user.getUsername());
                stats.setPrenom(user.getPrenom());
                stats.setNom(user.getNom());
                stats.setClasse(user.getClasse());
            }

            // 2. Récupérer les présences de l'étudiant
            ApiResponseDTO<List<PresenceDTO>> presencesResponse = presenceServiceClient.getPresencesByEtudiant(etudiantId, token);
            if (presencesResponse.getData() != null) {
                List<PresenceDTO> presences = presencesResponse.getData();
                
                int totalSeances = presences.size();
                int nbPresent = (int) presences.stream()
                        .filter(p -> AppConstants.PresenceType.PRESENT.equals(p.getStatus()))
                        .count();
                int nbAbsent = (int) presences.stream()
                        .filter(p -> AppConstants.PresenceType.ABSENT.equals(p.getStatus()))
                        .count();
                int nbRetard = (int) presences.stream()
                        .filter(p -> AppConstants.PresenceType.RETARD.equals(p.getStatus()))
                        .count();

                stats.setTotalSeances(totalSeances);
                stats.setPresences(nbPresent);
                stats.setAbsences(nbAbsent);
                stats.setRetards(nbRetard);

                // Calculer le taux de présence
                if (totalSeances > 0) {
                    double tauxPresence = (nbPresent * 100.0) / totalSeances;
                    stats.setTauxPresence(Math.round(tauxPresence * 100.0) / 100.0);
                }

                // Alerte si absences >= 3
                stats.setAlerteAbsence(nbAbsent >= AppConstants.Alerts.ABSENCES_CRITIQUES_SEUIL);
            }

            // TODO: Ajouter les stats des justificatifs quand le service sera disponible
            stats.setJustificatifsEnAttente(0);
            stats.setJustificatifsAcceptes(0);
            stats.setJustificatifsRefuses(0);

        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des stats de l'étudiant {}: {}", etudiantId, e.getMessage());
            throw new RuntimeException("Erreur lors de la récupération des statistiques", e);
        }

        return stats;
    }

    /**
     * Récupère les statistiques d'une classe
     */
    public StatClasseDTO getStatsClasse(String classe, String token) {
        logger.info("Récupération des stats pour la classe: {}", classe);

        StatClasseDTO stats = new StatClasseDTO(classe);

        try {
            // 1. Récupérer tous les étudiants de la classe
            ApiResponseDTO<List<UserDTO>> etudiantsResponse = userServiceClient.getUsersByClasse(classe, token);
            
            if (etudiantsResponse.getData() != null) {
                List<UserDTO> etudiants = etudiantsResponse.getData();
                stats.setNombreEtudiants(etudiants.size());

                // 2. Calculer les stats de présence pour chaque étudiant
                List<StatEtudiantDTO> statsEtudiants = new ArrayList<>();
                int totalAbsences = 0;
                int totalRetards = 0;
                double sommeTauxPresence = 0.0;
                int nbEtudiantsAvecPresences = 0;

                for (UserDTO etudiant : etudiants) {
                    try {
                        StatEtudiantDTO statEtudiant = getStatsEtudiant(etudiant.getId(), token);
                        statsEtudiants.add(statEtudiant);
                        
                        totalAbsences += statEtudiant.getAbsences() != null ? statEtudiant.getAbsences() : 0;
                        totalRetards += statEtudiant.getRetards() != null ? statEtudiant.getRetards() : 0;
                        
                        if (statEtudiant.getTauxPresence() != null) {
                            sommeTauxPresence += statEtudiant.getTauxPresence();
                            nbEtudiantsAvecPresences++;
                        }
                    } catch (Exception e) {
                        logger.warn("Erreur lors de la récupération des stats de l'étudiant {}: {}", 
                                   etudiant.getId(), e.getMessage());
                    }
                }

                stats.setTotalAbsences(totalAbsences);
                stats.setTotalRetards(totalRetards);

                // Calculer le taux de présence moyen
                if (nbEtudiantsAvecPresences > 0) {
                    double tauxMoyen = sommeTauxPresence / nbEtudiantsAvecPresences;
                    stats.setTauxPresenceMoyen(Math.round(tauxMoyen * 100.0) / 100.0);
                }

                // Top 5 des étudiants avec le plus d'absences
                List<StatEtudiantDTO> top5 = statsEtudiants.stream()
                        .filter(s -> s.getAbsences() != null && s.getAbsences() > 0)
                        .sorted((s1, s2) -> Integer.compare(s2.getAbsences(), s1.getAbsences()))
                        .limit(5)
                        .collect(Collectors.toList());
                stats.setTop5Absences(top5);
            }

            // TODO: Récupérer les stats des cours quand le service sera disponible
            stats.setNombreMatieres(0);
            stats.setNombreCoursTotal(0);
            stats.setNombreCoursPlanifies(0);
            stats.setNombreCoursValides(0);

            // TODO: Récupérer les matières en alerte
            stats.setMatieresEnAlerte(new ArrayList<>());

        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des stats de la classe {}: {}", classe, e.getMessage());
            throw new RuntimeException("Erreur lors de la récupération des statistiques", e);
        }

        return stats;
    }

    /**
     * Récupère la liste des étudiants avec alertes absences (>= 3 absences)
     */
    public List<StatEtudiantDTO> getEtudiantsAvecAbsencesCritiques(String token) {
        logger.info("Récupération des étudiants avec absences critiques");

        try {
            // Récupérer tous les étudiants
            ApiResponseDTO<List<UserDTO>> response = userServiceClient.getUsersByRole(
                    AppConstants.Roles.ETUDIANT, token);

            if (response.getData() == null) {
                return Collections.emptyList();
            }

            // Filtrer les étudiants avec >= 3 absences
            return response.getData().stream()
                    .map(etudiant -> {
                        try {
                            return getStatsEtudiant(etudiant.getId(), token);
                        } catch (Exception e) {
                            logger.warn("Erreur pour l'étudiant {}: {}", etudiant.getId(), e.getMessage());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .filter(stat -> stat.getAbsences() != null && 
                                  stat.getAbsences() >= AppConstants.Alerts.ABSENCES_CRITIQUES_SEUIL)
                    .sorted((s1, s2) -> Integer.compare(s2.getAbsences(), s1.getAbsences()))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des absences critiques: {}", e.getMessage());
            throw new RuntimeException("Erreur lors de la récupération des absences critiques", e);
        }
    }

    /**
     * Récupère les matières en alerte (< 12h restantes)
     */
    public List<MatiereAlerteDTO> getMatieresEnAlerte(String token) {
        logger.info("Récupération des matières en alerte");

        // TODO: Implémenter quand le cours-service sera disponible
        // Pour l'instant, retourner une liste vide avec un log
        logger.warn("Cours Service non disponible - retour d'une liste vide");
        
        return Collections.emptyList();
    }

    /**
     * Dashboard global pour le responsable de formation
     */
    public Map<String, Object> getDashboardGlobal(String token) {
        logger.info("Récupération du dashboard global");

        Map<String, Object> dashboard = new HashMap<>();

        try {
            // Stats globales
            ApiResponseDTO<List<UserDTO>> etudiantsResponse = userServiceClient.getUsersByRole(
                    AppConstants.Roles.ETUDIANT, token);
            ApiResponseDTO<List<UserDTO>> profsResponse = userServiceClient.getUsersByRole(
                    AppConstants.Roles.PROFESSEUR, token);

            dashboard.put("nombreEtudiants", etudiantsResponse.getData() != null ? 
                         etudiantsResponse.getData().size() : 0);
            dashboard.put("nombreProfesseurs", profsResponse.getData() != null ? 
                         profsResponse.getData().size() : 0);

            // Stats par classe
            Map<String, StatClasseDTO> statsClasses = new HashMap<>();
            for (String classe : new String[]{AppConstants.Classes.CI_M1, AppConstants.Classes.CI_M2, 
                                              AppConstants.Classes.MCS_M1, AppConstants.Classes.MCS_M2}) {
                try {
                    statsClasses.put(classe, getStatsClasse(classe, token));
                } catch (Exception e) {
                    logger.warn("Erreur pour la classe {}: {}", classe, e.getMessage());
                }
            }
            dashboard.put("statsParClasse", statsClasses);

            // Étudiants avec absences critiques
            dashboard.put("absencesCritiques", getEtudiantsAvecAbsencesCritiques(token));

            // Matières en alerte
            dashboard.put("matieresEnAlerte", getMatieresEnAlerte(token));

        } catch (Exception e) {
            logger.error("Erreur lors de la récupération du dashboard: {}", e.getMessage());
            throw new RuntimeException("Erreur lors de la récupération du dashboard", e);
        }

        return dashboard;
    }
}
