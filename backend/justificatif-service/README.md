# 🔐 AUTH SERVICE - Microservice d'Authentification

**Responsable** : Boubacar Souare  
**Port** : 8081  
**Base URL** : http://localhost:8081/api/auth

---

## 🎯 Responsabilités

Ce microservice gère :
- ✅ L'authentification des utilisateurs (login)
- ✅ La génération de tokens JWT
- ✅ La validation des tokens JWT
- ✅ L'inscription de nouveaux utilisateurs (optionnel)
- ✅ La récupération des informations utilisateur depuis le token

---

## 📁 Structure du Projet

```
auth-service/
├── src/main/java/com/cahiertexte/auth/
│   ├── AuthServiceApplication.java  # Point d'entrée
│   ├── config/
│   │   ├── SecurityConfig.java      # Configuration Spring Security
│   │   └── OpenApiConfig.java       # Configuration Swagger
│   ├── controller/
│   │   └── AuthController.java      # Endpoints REST
│   ├── service/
│   │   └── AuthService.java         # Logique métier
│   ├── repository/
│   │   └── UserRepository.java      # Accès données
│   ├── model/
│   │   └── User.java                # Entité User
│   └── exception/
│       └── GlobalExceptionHandler.java
├── src/main/resources/
│   └── application.yml              # Configuration
└── pom.xml                          # Dépendances Maven
```

---

## 🚀 Démarrage Rapide

### 1. Prérequis
- ✅ Java 21 installé
- ✅ Maven installé
- ✅ SQL Server en cours d'exécution
- ✅ Base de données `cahier_texte_db` créée
- ✅ Module `common` installé (`mvn clean install` dans `/backend/common`)

### 2. Configuration de la Base de Données

Modifiez `src/main/resources/application.yml` :

```yaml
spring:
  datasource:
    url: jdbc:sqlserver://localhost:1433;databaseName=cahier_texte_db;encrypt=true;trustServerCertificate=true
    username: admincahiertxt
    password: VotreMotDePasse  # ⚠️ CHANGEZ-MOI
```

### 3. Installer le module common (si pas déjà fait)

```bash
cd ../common
mvn clean install
```

### 4. Démarrer le service

```bash
cd ../auth-service
mvn clean install
mvn spring-boot:run
```

### 5. Vérifier que ça fonctionne

Ouvrez votre navigateur :
- 📚 Swagger UI : http://localhost:8081/api/auth/swagger-ui.html
- 💚 Health Check : http://localhost:8081/api/auth/health

---

## 📡 API Endpoints

### 1. POST `/login` - Connexion
Authentifie un utilisateur et retourne un token JWT.

**Request Body** :
```json
{
  "username": "prof.samb",
  "password": "password123"
}
```

**Response** :
```json
{
  "success": true,
  "message": "Connexion réussie",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "type": "Bearer",
    "userId": 1,
    "username": "prof.samb",
    "email": "prof.samb@tdsi.sn",
    "prenom": "Amadou",
    "nom": "Samb",
    "role": "PROFESSEUR",
    "classe": null,
    "expiresIn": 86400000
  },
  "timestamp": "2026-02-04T10:30:00"
}
```

### 2. POST `/register` - Inscription (Optionnel)
Crée un nouveau compte utilisateur.

**Request Body** :
```json
{
  "username": "nouvel.user",
  "email": "user@tdsi.sn",
  "password": "motdepasse123",
  "prenom": "Prénom",
  "nom": "Nom",
  "role": "ETUDIANT",
  "classe": "CI_M1"
}
```

### 3. GET `/validate` - Valider un Token
Vérifie si un token JWT est valide.

**Headers** :
```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**Response** :
```json
{
  "success": true,
  "message": "Token valide",
  "data": true,
  "timestamp": "2026-02-04T10:30:00"
}
```

### 4. GET `/me` - Obtenir l'Utilisateur Connecté
Retourne les informations de l'utilisateur à partir du token.

**Headers** :
```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**Response** :
```json
{
  "success": true,
  "data": {
    "id": 1,
    "username": "prof.samb",
    "email": "prof.samb@tdsi.sn",
    "prenom": "Amadou",
    "nom": "Samb",
    "role": "PROFESSEUR",
    "classe": null,
    "actif": true
  },
  "timestamp": "2026-02-04T10:30:00"
}
```

### 5. GET `/health` - Health Check
Vérifie que le service est opérationnel.

---

## 🧪 Tests avec Postman/Insomnia

### Collection de tests

1. **Login**
```
POST http://localhost:8081/api/auth/login
Content-Type: application/json

{
  "username": "prof.samb",
  "password": "password123"
}
```

2. **Validate Token**
```
GET http://localhost:8081/api/auth/validate
Authorization: Bearer <votre-token>
```

3. **Get Current User**
```
GET http://localhost:8081/api/auth/me
Authorization: Bearer <votre-token>
```

---

## 📊 Modèle de Données

### Table `users`

| Colonne           | Type         | Description                |
|-------------------|--------------|----------------------------|
| id                | BIGINT       | Clé primaire (auto-incr)   |
| username          | VARCHAR(50)  | Nom d'utilisateur (unique) |
| email             | VARCHAR(100) | Email (unique)             |
| password          | VARCHAR(255) | Hash SHA-256 du mot de passe |
| prenom            | VARCHAR(50)  | Prénom                     |
| nom               | VARCHAR(50)  | Nom                        |
| role              | VARCHAR(50)  | Rôle utilisateur           |
| classe            | VARCHAR(20)  | Classe (pour étudiants)    |
| actif             | BIT          | Compte actif ou non        |
| date_creation     | DATETIME     | Date de création           |
| date_modification | DATETIME     | Dernière modification      |

### Rôles disponibles
- `RESPONSABLE_FORMATION`
- `RESPONSABLE_CLASSE`
- `PROFESSEUR`
- `ETUDIANT`

### Classes disponibles
- `CI_M1` - Cyber-sécurité M1
- `CI_M2` - Cyber-sécurité M2
- `MCS_M1` - Management Cyber-sécurité M1
- `MCS_M2` - Management Cyber-sécurité M2

---

## 🔒 Sécurité

### JWT (JSON Web Token)
- **Algorithme** : HS256
- **Durée de validité** : 24 heures (86400000 ms)
- **Clé secrète** : À configurer dans `application.yml`

⚠️ **IMPORTANT** : Changez la clé secrète JWT en production !

```yaml
jwt:
  secret: votre-cle-ultra-securisee-minimum-256-bits-changez-moi-en-production
```

### Hashage des Mots de Passe
- **Algorithme** : SHA-256
- **Note** : Pour la production, envisagez BCrypt

---

## ✅ TODO List - Ce que tu dois faire

### Phase 1 : Configuration (10 min)
- [ ] Modifier `application.yml` avec tes identifiants SQL Server
- [ ] Changer la clé secrète JWT
- [ ] Tester la connexion à la base de données

### Phase 2 : Tests (20 min)
- [ ] Démarrer le service
- [ ] Ouvrir Swagger UI
- [ ] Tester l'endpoint `/health`
- [ ] Tester l'endpoint `/login` avec un utilisateur de test
- [ ] Vérifier que le token JWT est généré

### Phase 3 : Développement (optionnel)
- [ ] Ajouter un endpoint `/refresh` pour renouveler le token
- [ ] Ajouter un endpoint `/logout` (blacklist de tokens)
- [ ] Ajouter un endpoint `/forgot-password`
- [ ] Implémenter un système de refresh tokens
- [ ] Ajouter des logs détaillés

### Phase 4 : Améliorations (optionnel)
- [ ] Ajouter des tests unitaires
- [ ] Ajouter un rate limiting (limitation du nombre de tentatives)
- [ ] Implémenter une stratégie de blocage après X tentatives échouées
- [ ] Ajouter une authentification à deux facteurs (2FA)

---

## 🐛 Problèmes Courants

### ❌ Erreur : "Cannot resolve dependency 'common'"
**Solution** : Installez le module common
```bash
cd ../common
mvn clean install
```

### ❌ Erreur : "Cannot connect to SQL Server"
**Solution** : Vérifiez :
1. SQL Server est démarré
2. Les identifiants dans `application.yml` sont corrects
3. La base de données existe
4. Le firewall autorise la connexion

### ❌ Erreur : "Port 8081 already in use"
**Solution** : Changez le port dans `application.yml`
```yaml
server:
  port: 8082  # ou un autre port disponible
```

---

## 📞 Communication avec les Autres Services

Ce service sera appelé par :
- ✅ **API Gateway** : Pour valider les tokens
- ✅ **Tous les autres services** : Pour obtenir les infos utilisateur

Exemple d'appel depuis un autre service (avec OpenFeign) :
```java
@FeignClient(name = "auth-service", url = "http://localhost:8081/api/auth")
public interface AuthServiceClient {
    @GetMapping("/validate")
    ApiResponseDTO<Boolean> validateToken(@RequestHeader("Authorization") String token);
}
```

---

## 📚 Ressources Utiles

- [Documentation Spring Boot](https://spring.io/projects/spring-boot)
- [Documentation JWT](https://jwt.io/)
- [Documentation Spring Security](https://spring.io/projects/spring-security)
- [Documentation Swagger/OpenAPI](https://springdoc.org/)

---

## 👨‍💻 Support

Si tu as des questions ou des problèmes :
1. Vérifie d'abord ce README
2. Regarde les logs du service
3. Teste avec Swagger UI
4. Demande de l'aide au chef de projet

---

**Bon courage Boubacar ! 💪🚀**
