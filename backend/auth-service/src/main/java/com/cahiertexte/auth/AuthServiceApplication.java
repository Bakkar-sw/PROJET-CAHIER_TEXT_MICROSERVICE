package com.cahiertexte.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
<<<<<<< HEAD

=======
import org.springframework.context.annotation.ComponentScan;
>>>>>>> 80ada71f59300ce8cfe486db3ce4e061bcef5298
/**
 * Point d'entrée du microservice d'authentification
 * 
 * Ce service gère :
 * - L'authentification des utilisateurs
 * - La génération et validation des tokens JWT
 * - La gestion des sessions
 * 
 * @author Boubacar Souare
 * @version 1.0.0
 */
@SpringBootApplication
@EnableFeignClients
<<<<<<< HEAD
=======
@ComponentScan(basePackages = {
    "com.cahiertexte.auth",
    "com.cahiertexte.common"
})
>>>>>>> 80ada71f59300ce8cfe486db3ce4e061bcef5298
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
        System.out.println("\n==============================================");
        System.out.println("🚀 AUTH SERVICE DÉMARRÉ AVEC SUCCÈS !");
        System.out.println("📝 Swagger UI: http://localhost:8081/api/auth/swagger-ui.html");
        System.out.println("📚 API Docs: http://localhost:8081/api/auth/api-docs");
        System.out.println("==============================================\n");
    }
}
