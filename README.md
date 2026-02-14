# 🎓 Cahier Texte TDSI - Architecture Microservices

## 📋 Informations du Projet

**Établissement** : Institut TDSI  
**Projet** : Refonte de l'application Cahier de Texte en architecture microservices  
**Technologies** : Spring Boot 3, Angular 17, SQL Server, JWT  

---

## 👥 Équipe de Développement

| Membre | Microservice Backend | Feature Angular | Port |
|--------|---------------------|-----------------|------|
| **Boubacar Souare** | Auth Service | `features/auth` | 8081 |
| **Ousmane Deme** | User Service | `features/users` | 8082 |
| **Abdoulaye Guene** | Cours Service | `features/cours` | 8083 |
| **Alioune Kebe** | Presence Service | `features/presence` | 8084 |
| **Fatou Leye** | Justificatif Service | `features/justificatif` | 8085 |
| **Cheikh Tjian Diaw** | Stats Service | `features/statistiques` | 8086 |
| **Équipe** | API Gateway | - | 8080 |

---

## 🏗️ Architecture du Projet

```
cahier-texte-microservices/
├── backend/
│   ├── common/                    # ✅ CRÉÉ - Module partagé
│   ├── auth-service/              # ✅ CRÉÉ - Boubacar
│   ├── user-service/              # 📋 À COMPLÉTER - Ousmane
│   ├── cours-service/             # 📋 À COMPLÉTER - Abdoulaye
│   ├── presence-service/          # 📋 À COMPLÉTER - Alioune
│   ├── justificatif-service/      # 📋 À COMPLÉTER - Fatou
│   ├── stats-service/             # 📋 À COMPLÉTER - Cheikh
│   └── api-gateway/               # 📋 À COMPLÉTER - Équipe
│
├── frontend/                      # 📋 À CRÉER
│   └── angular-app/
│
├── docs/                          # 📚 Documentation
│   ├── SETUP.md
│   ├── ARCHITECTURE.md
│   └── API-CONTRACT.md
│
└── README.md                      # Ce fichier
```

---

## 🚀 Démarrage Rapide pour TOUS

### Prérequis

1. **Java JDK 21** : [Télécharger ici](https://adoptium.net/)
2. **Maven 3.9+** : [Télécharger ici](https://maven.apache.org/download.cgi)
3. **SQL Server 2019+** : [Télécharger ici](https://www.microsoft.com/sql-server)
4. **Node.js 20+** (pour Angular) : [Télécharger ici](https://nodejs.org/)
5. **Git** : [Télécharger ici](https://git-scm.com/)

### Vérifications

```bash
# Vérifier Java
java --version
# Devrait afficher : openjdk version "21.x.x"

# Vérifier Maven
mvn --version
# Devrait afficher : Apache Maven 3.9.x

# Vérifier Node
node --version
# Devrait afficher : v20.x.x

# Vérifier npm
npm --version
```

---

## 📦 Installation et Configuration

### Étape 1 : Cloner le Projet

```bash
git clone https://github.com/votre-repo/cahier-texte-microservices.git
cd cahier-texte-microservices
```

### Étape 2 : Configuration SQL Server

#### 2.1 Créer la base de données

```sql
CREATE DATABASE cahier_texte_db;
GO

USE cahier_texte_db;
GO
```

#### 2.2 Créer l'utilisateur

```sql
CREATE LOGIN admincahiertxt WITH PASSWORD = 'MotDePasse123!';
GO

USE cahier_texte_db;
GO

CREATE USER admincahiertxt FOR LOGIN admincahiertxt;
GO

ALTER ROLE db_owner ADD MEMBER admincahiertxt;
GO
```

#### 2.3 Insérer des données de test

```sql
-- Table users
CREATE TABLE users (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    nom VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    classe VARCHAR(20),
    actif BIT NOT NULL DEFAULT 1,
    date_creation DATETIME NOT NULL DEFAULT GETDATE(),
    date_modification DATETIME NOT NULL DEFAULT GETDATE()
);

-- Insérer un utilisateur de test (mot de passe: "password123" en SHA-256)
INSERT INTO users (username, email, password, prenom, nom, role, classe, actif)
VALUES 
('prof.samb', 'prof.samb@tdsi.sn', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'Amadou', 'Samb', 'PROFESSEUR', NULL, 1),
('etud.ndiaye', 'etud.ndiaye@tdsi.sn', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'Fatou', 'Ndiaye', 'ETUDIANT', 'CI_M1', 1);
```

### Étape 3 : Installer le Module Common (OBLIGATOIRE)

```bash
cd backend/common
mvn clean install
```

✅ **Important** : Ce module doit être installé AVANT de démarrer n'importe quel microservice !

### Étape 4 : Configurer Votre Microservice

Dans `src/main/resources/application.yml` de **VOTRE** service, modifiez :

```yaml
spring:
  datasource:
    url: jdbc:sqlserver://localhost:1433;databaseName=cahier_texte_db;encrypt=true;trustServerCertificate=true
    username: admincahiertxt
    password: MotDePasse123!  # ⚠️ Mettez VOTRE mot de passe
```

### Étape 5 : Démarrer Votre Service

```bash
cd backend/VOTRE-service
mvn clean install
mvn spring-boot:run
```

### Étape 6 : Tester Votre Service

Ouvrez votre navigateur :
- **Swagger UI** : `http://localhost:PORT/api/SERVICE_NAME/swagger-ui.html`
- **Health Check** : `http://localhost:PORT/api/SERVICE_NAME/health`

Remplacez `PORT` et `SERVICE_NAME` par vos valeurs.

---

## 📋 Responsabilités par Service

### 🔐 Auth Service (Boubacar - Port 8081)
**Endpoints** :
- `POST /login` - Connexion
- `POST /register` - Inscription
- `GET /validate` - Valider un token
- `GET /me` - Infos utilisateur depuis token

**Entités** : `User`

---

### 👤 User Service (Ousmane - Port 8082)
**Endpoints à implémenter** :
- `GET /users` - Liste des utilisateurs
- `GET /users/{id}` - Détails d'un utilisateur
- `POST /users` - Créer un utilisateur
- `PUT /users/{id}` - Modifier un utilisateur
- `DELETE /users/{id}` - Supprimer un utilisateur
- `GET /users/role/{role}` - Utilisateurs par rôle
- `GET /users/classe/{classe}` - Utilisateurs par classe

**Entités** : `User`

**À faire** :
1. Copier la structure de `auth-service`
2. Modifier le port dans `application.yml` → `8082`
3. Modifier `context-path` → `/api/users`
4. Implémenter les endpoints CRUD
5. Ajouter un `FeignClient` vers `auth-service` pour valider les tokens

---

### 📚 Cours Service (Abdoulaye - Port 8083)
**Endpoints à implémenter** :
- `GET /cours` - Liste des cours
- `GET /cours/{id}` - Détails d'un cours
- `POST /cours` - Planifier un cours
- `PUT /cours/{id}` - Modifier un cours
- `DELETE /cours/{id}` - Annuler un cours
- `GET /cours/classe/{classe}` - Cours par classe
- `GET /cours/professeur/{profId}` - Cours par professeur
- `PUT /cours/{id}/valider` - Valider un cours

**Entités** :
- `Cours` (id, matiere_id, professeur_id, classe, date, heure_debut, heure_fin, salle, status, cahier_texte)
- `Matiere` (id, nom, code, volume_horaire, professeur_id)

**Relations** :
- Un cours appartient à une matière
- Un cours est donné par un professeur
- Un cours concerne une classe

---

### ✅ Presence Service (Alioune - Port 8084)
**Endpoints à implémenter** :
- `GET /presences/cours/{coursId}` - Présences d'un cours
- `POST /presences` - Enregistrer les présences
- `PUT /presences/{id}` - Modifier une présence
- `GET /presences/etudiant/{etudiantId}` - Présences d'un étudiant
- `GET /presences/stats/etudiant/{etudiantId}` - Statistiques de présence

**Entités** :
- `Presence` (id, cours_id, etudiant_id, status, date, remarque)

**Statuts** : `PRESENT`, `ABSENT`, `RETARD`

**Relations** :
- Une présence appartient à un cours
- Une présence concerne un étudiant

---

### 📄 Justificatif Service (Fatou - Port 8085)
**Endpoints à implémenter** :
- `GET /justificatifs` - Liste des justificatifs
- `GET /justificatifs/{id}` - Détails d'un justificatif
- `POST /justificatifs` - Soumettre un justificatif
- `PUT /justificatifs/{id}/valider` - Valider un justificatif
- `PUT /justificatifs/{id}/refuser` - Refuser un justificatif
- `GET /justificatifs/etudiant/{etudiantId}` - Justificatifs d'un étudiant
- `GET /justificatifs/en-attente` - Justificatifs en attente

**Entités** :
- `Justificatif` (id, etudiant_id, cours_id, motif, fichier, status, date_soumission, date_traitement)

**Statuts** : `EN_ATTENTE`, `ACCEPTE`, `REFUSE`

---

### 📊 Stats Service (Cheikh - Port 8086)
**Endpoints à implémenter** :
- `GET /stats/classe/{classe}` - Statistiques par classe
- `GET /stats/etudiant/{etudiantId}` - Statistiques d'un étudiant
- `GET /stats/professeur/{profId}` - Statistiques d'un professeur
- `GET /stats/matieres/alertes` - Matières avec heures < 12h
- `GET /stats/absences/critiques` - Étudiants avec ≥3 absences
- `GET /stats/dashboard/formation` - Dashboard responsable formation
- `GET /stats/taux-presence` - Taux de présence global

**Ce service** :
- N'a PAS de table propre
- Appelle les autres services (Cours, Presence, Justificatif) via Feign
- Agrège les données
- Calcule les statistiques

---

### 🌐 API Gateway (Équipe - Port 8080)
**Responsabilités** :
- Routage vers les microservices
- Authentification centralisée (vérification JWT)
- CORS
- Rate Limiting (optionnel)

**Configuration des routes** :
- `/api/auth/**` → auth-service (8081)
- `/api/users/**` → user-service (8082)
- `/api/cours/**` → cours-service (8083)
- `/api/presences/**` → presence-service (8084)
- `/api/justificatifs/**` → justificatif-service (8085)
- `/api/stats/**` → stats-service (8086)

---

## 🔧 Configuration Rapide par Service

### Template pour créer un nouveau service

```bash
# 1. Copier la structure de auth-service
cp -r backend/auth-service backend/VOTRE-service

# 2. Modifier le pom.xml
# Changez :
# <artifactId>auth-service</artifactId> 
# en 
# <artifactId>VOTRE-service</artifactId>

# 3. Modifier application.yml
# Changez le port et le context-path

# 4. Renommer les packages
# auth → votre_nom_service

# 5. Installer le module common
cd backend/common
mvn clean install

# 6. Compiler votre service
cd ../VOTRE-service
mvn clean install

# 7. Démarrer
mvn spring-boot:run
```

---

## 🔗 Communication entre Services (OpenFeign)

### Exemple : Appeler Auth Service depuis User Service

```java
@FeignClient(name = "auth-service", url = "http://localhost:8081/api/auth")
public interface AuthServiceClient {
    
    @GetMapping("/validate")
    ApiResponseDTO<Boolean> validateToken(@RequestHeader("Authorization") String token);
    
    @GetMapping("/me")
    ApiResponseDTO<UserDTO> getCurrentUser(@RequestHeader("Authorization") String token);
}
```

### Utilisation dans le controller

```java
@RestController
public class UserController {
    
    @Autowired
    private AuthServiceClient authClient;
    
    @GetMapping("/users")
    public ResponseEntity<?> getUsers(@RequestHeader("Authorization") String token) {
        // Valider le token
        ApiResponseDTO<Boolean> validation = authClient.validateToken(token);
        
        if (!validation.getData()) {
            return ResponseEntity.status(401).body("Token invalide");
        }
        
        // Continuer...
    }
}
```

---

## 📚 Bonnes Pratiques

### 1. Structure des Packages
```
com.cahiertexte.SERVICE/
├── config/          # Configurations (Security, Swagger, etc.)
├── controller/      # Controllers REST
├── service/         # Logique métier
├── repository/      # Accès données (JPA)
├── model/           # Entités JPA
├── dto/             # Data Transfer Objects
└── exception/       # Gestion des exceptions
```

### 2. Nommage
- **Entités** : Singulier (User, Cours, Presence)
- **Tables** : Pluriel (users, cours, presences)
- **Controllers** : SuffixController (UserController)
- **Services** : SuffixService (UserService)
- **Repositories** : SuffixRepository (UserRepository)

### 3. Réponses API
Toujours utiliser `ApiResponseDTO` :

```java
// ✅ BON
return ResponseEntity.ok(ApiResponseDTO.success("Opération réussie", data));

// ❌ MAUVAIS
return ResponseEntity.ok(data);
```

### 4. Gestion des Erreurs
```java
// Dans GlobalExceptionHandler
@ExceptionHandler(ResourceNotFoundException.class)
public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex) {
    return ResponseEntity.status(404)
        .body(ApiResponseDTO.error(ex.getMessage()));
}
```

### 5. Validation
```java
@PostMapping("/users")
public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO user) {
    // Spring valide automatiquement avec @Valid
}
```

---

## 🧪 Tests

### Tester avec Swagger
1. Ouvrez `http://localhost:PORT/api/SERVICE/swagger-ui.html`
2. Cliquez sur un endpoint
3. Cliquez sur "Try it out"
4. Remplissez les paramètres
5. Cliquez sur "Execute"

### Tester avec Postman
Collection disponible dans `/docs/postman_collection.json`

---

## 🐛 Problèmes Courants

### Erreur : Module common non trouvé
```bash
cd backend/common
mvn clean install
```

### Erreur : Port déjà utilisé
Modifiez le port dans `application.yml`

### Erreur : Connexion SQL Server
Vérifiez :
1. SQL Server est démarré
2. Les identifiants sont corrects
3. La base de données existe

---

## 📞 Workflow Git

### Branches
- `main` : Production
- `develop` : Développement
- `feature/auth-service` : Feature Boubacar
- `feature/user-service` : Feature Ousmane
- etc.

### Workflow
```bash
# 1. Créer votre branche
git checkout -b feature/votre-service

# 2. Travailler sur votre service
# ... code code code ...

# 3. Commit réguliers
git add .
git commit -m "feat(votre-service): ajout endpoint XXX"

# 4. Push
git push origin feature/votre-service

# 5. Créer une Pull Request sur GitHub
```

### Convention de commits
- `feat(service): description` - Nouvelle fonctionnalité
- `fix(service): description` - Correction de bug
- `docs(service): description` - Documentation
- `refactor(service): description` - Refactoring

---

## 📅 Planning Suggéré

### Semaine 1
- [x] Setup projet (Chef de projet)
- [ ] Chaque membre configure son environnement
- [ ] Installation du module common
- [ ] Test de connexion SQL Server

### Semaine 2
- [ ] Développement des services (chacun son service)
- [ ] Tests unitaires de base
- [ ] Documentation des endpoints

### Semaine 3
- [ ] Intégration des services
- [ ] Développement API Gateway
- [ ] Tests d'intégration

### Semaine 4
- [ ] Développement Frontend Angular
- [ ] Tests end-to-end
- [ ] Documentation finale

---

## 🎯 Critères de Succès

Pour qu'un service soit considéré comme "terminé" :
- ✅ Le service démarre sans erreur
- ✅ Swagger UI est accessible
- ✅ Tous les endpoints sont implémentés
- ✅ Les endpoints retournent les bonnes réponses
- ✅ Les erreurs sont gérées proprement
- ✅ Le README du service est à jour
- ✅ Au moins 1 test par endpoint

---

## 📖 Ressources

- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [OpenFeign](https://spring.io/projects/spring-cloud-openfeign)
- [Swagger/OpenAPI](https://springdoc.org/)

---

**Bon courage à toute l'équipe ! 💪🚀**
