package com.cahiertexte.stats.client;

import com.cahiertexte.common.dto.ApiResponseDTO;
import com.cahiertexte.stats.dto.PresenceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * Client Feign pour communiquer avec le Presence Service
 */
@FeignClient(name = "presence-service", url = "${feign.client.config.presence-service.url:http://localhost:8084}/api/presences")
public interface PresenceServiceClient {

    /**
     * Récupère les présences d'un cours
     */
    @GetMapping("/cours/{coursId}")
    ApiResponseDTO<List<PresenceDTO>> getPresencesByCours(@PathVariable Long coursId, @RequestHeader("Authorization") String token);

    /**
     * Récupère les présences d'un étudiant
     */
    @GetMapping("/etudiant/{etudiantId}")
    ApiResponseDTO<List<PresenceDTO>> getPresencesByEtudiant(@PathVariable Long etudiantId, @RequestHeader("Authorization") String token);
}
