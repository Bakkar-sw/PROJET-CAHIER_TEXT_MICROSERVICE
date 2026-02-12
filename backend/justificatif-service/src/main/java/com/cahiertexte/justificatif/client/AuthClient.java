package com.cahiertexte.justificatif.client;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthClient {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String AUTH_SERVICE_URL = "http://localhost:8081/api/auth/validate";

    public boolean validateToken(String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Boolean> response = restTemplate.exchange(
                    AUTH_SERVICE_URL,
                    HttpMethod.GET,
                    entity,
                    Boolean.class
            );

            return Boolean.TRUE.equals(response.getBody());

        } catch (Exception e) {
            return false;
        }
    }
}
