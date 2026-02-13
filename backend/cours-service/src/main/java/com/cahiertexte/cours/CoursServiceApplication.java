package com.cahiertexte.cours;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Point d'entrée du microservice Cours Service.
 * Port 8083 - Context /api/cours
 *
 * @author Abdoulaye Guene
 * @version 1.0.0
 */
@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = {
    "com.cahiertexte.cours",
    "com.cahiertexte.common"
})
public class CoursServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoursServiceApplication.class, args);
        System.out.println("\n==============================================");
        System.out.println("🚀 COURS SERVICE DÉMARRÉ AVEC SUCCÈS !");
        System.out.println("📝 Swagger UI: http://localhost:8083/api/cours/swagger-ui.html");
        System.out.println("📚 API Docs: http://localhost:8083/api/cours/api-docs");
        System.out.println("==============================================\n");
    }
}
