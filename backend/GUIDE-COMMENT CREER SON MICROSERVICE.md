# 🚀 Guide Rapide - Créer Votre Microservice

## Pour : Tous les membres SAUF Boubacar (qui a déjà auth-service)

---

## ✅ Étapes à Suivre

### 1. Copier la Structure de Auth-Service

```bash
# Depuis le dossier backend/
cp -r auth-service VOTRE-service
cd VOTRE-service
```

### 2. Modifier le pom.xml

Ouvrez `pom.xml` et changez :

```xml
<!-- AVANT -->
<artifactId>auth-service</artifactId>
<n>Auth Service</n>
<description>Microservice d'authentification pour Cahier Texte TDSI</description>

<!-- APRÈS -->
<artifactId>VOTRE-service</artifactId>
<n>VOTRE Service Name</n>
<description>Microservice de DESCRIPTION pour Cahier Texte TDSI</description>
```

### 3. Modifier application.yml

```yaml
server:
  port: VOTRE_PORT  # Voir le tableau des ports
  servlet:
    context-path: /api/VOTRE_CONTEXT  # Ex: /api/users

spring:
  application:
    name: VOTRE-service
```

**Tableau des ports** :
- user-service : 8082 → `/api/users`
- cours-service : 8083 → `/api/cours`
- presence-service : 8084 → `/api/presences`
- justificatif-service : 8085 → `/api/justificatifs`
- stats-service : 8086 → `/api/stats`

### 4. Renommer les Packages

```bash
# Option 1 : Utiliser votre IDE (IntelliJ, Eclipse)
# Clic droit sur le package "auth" → Refactor → Rename

# Option 2 : Manuellement
# Renommer tous les dossiers et imports :
# com.cahiertexte.auth → com.cahiertexte.VOTRE_SERVICE
```

### 5. Modifier la Classe Principale

Fichier : `src/main/java/com/cahiertexte/VOTRE_SERVICE/VotreServiceApplication.java`

```java
package com.cahiertexte.VOTRE_SERVICE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class VotreServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VotreServiceApplication.java, args);
        System.out.println("\n==============================================");
        System.out.println("🚀 VOTRE SERVICE DÉMARRÉ AVEC SUCCÈS !");
        System.out.println("📝 Swagger UI: http://localhost:VOTRE_PORT/api/VOTRE_CONTEXT/swagger-ui.html");
        System.out.println("==============================================\n");
    }
}
```

### 6. Créer Vos Entités

Exemple pour **User Service** :

```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;
    private String email;
    // ... vos champs
    
    // Getters/Setters
}
```

### 7. Créer Votre Repository

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    // ... vos méthodes
}
```

### 8. Créer Votre Service

```java
@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    // ... vos méthodes
}
```

### 9. Créer Votre Controller

```java
@RestController
@RequestMapping("/")
@Tag(name = "Votre Service", description = "API de gestion de XXXX")
public class VotreController {
    
    @Autowired
    private VotreService service;
    
    @GetMapping("/health")
    public ResponseEntity<?> health() {
        return ResponseEntity.ok(ApiResponseDTO.success("Service running"));
    }
    
    @GetMapping("/votre-endpoint")
    public ResponseEntity<?> getItems() {
        List<Item> items = service.getAllItems();
        return ResponseEntity.ok(ApiResponseDTO.success(items));
    }
    
    // ... vos endpoints
}
```

### 10. Compiler et Tester

```bash
# Installer le module common (si pas déjà fait)
cd ../common
mvn clean install

# Retourner à votre service
cd ../VOTRE-service

# Compiler
mvn clean install

# Démarrer
mvn spring-boot:run

# Tester
# Ouvrir : http://localhost:VOTRE_PORT/api/VOTRE_CONTEXT/swagger-ui.html
```

---

## 📋 Checklist par Service

### User Service (Ousmane)
- [ ] Port : 8082
- [ ] Context : /api/users
- [ ] Entité : User (réutiliser celle de auth-service)
- [ ] Endpoints :
  - [ ] GET /users
  - [ ] GET /users/{id}
  - [ ] POST /users
  - [ ] PUT /users/{id}
  - [ ] DELETE /users/{id}
  - [ ] GET /users/role/{role}

### Cours Service (Abdoulaye)
- [ ] Port : 8083
- [ ] Context : /api/cours
- [ ] Entités : Cours, Matiere
- [ ] Endpoints :
  - [ ] GET /cours
  - [ ] GET /cours/{id}
  - [ ] POST /cours
  - [ ] PUT /cours/{id}
  - [ ] GET /cours/classe/{classe}
  - [ ] PUT /cours/{id}/valider

### Presence Service (Alioune)
- [ ] Port : 8084
- [ ] Context : /api/presences
- [ ] Entité : Presence
- [ ] Endpoints :
  - [ ] GET /presences/cours/{coursId}
  - [ ] POST /presences
  - [ ] GET /presences/etudiant/{etudiantId}
  - [ ] GET /presences/stats/etudiant/{etudiantId}

### Justificatif Service (Fatou)
- [ ] Port : 8085
- [ ] Context : /api/justificatifs
- [ ] Entité : Justificatif
- [ ] Endpoints :
  - [ ] GET /justificatifs
  - [ ] POST /justificatifs
  - [ ] PUT /justificatifs/{id}/valider
  - [ ] GET /justificatifs/etudiant/{etudiantId}

### Stats Service (Cheikh)
- [ ] Port : 8086
- [ ] Context : /api/stats
- [ ] Pas d'entité (appelle les autres services)
- [ ] Endpoints :
  - [ ] GET /stats/classe/{classe}
  - [ ] GET /stats/etudiant/{etudiantId}
  - [ ] GET /stats/matieres/alertes

---

## 🆘 Aide Rapide

### Problème : Module common non trouvé
```bash
cd backend/common
mvn clean install
```

### Problème : Port déjà utilisé
Changez le port dans `application.yml`

### Problème : Erreur SQL
Vérifiez vos identifiants dans `application.yml`

---

**Questions ? Demandez au chef de projet !**
