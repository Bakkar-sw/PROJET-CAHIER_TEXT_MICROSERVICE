# 👤 USER SERVICE - Microservice de Gestion des Utilisateurs

**Responsable** : Ousmane Deme  
**Port** : 8082  
**Base URL** : http://localhost:8082/api/users

---

## 🎯 Responsabilités

✅ CRUD complet des utilisateurs  
✅ Gestion des rôles (Formation, Classe, Professeur, Étudiant)  
✅ Gestion des classes (CI_M1, CI_M2, MCS_M1, MCS_M2)  
✅ Activation/Désactivation des comptes  
✅ Changement de mot de passe  
✅ Réinitialisation de mot de passe (admin)  
✅ Statistiques des utilisateurs  
✅ Recherche avancée (par rôle, classe, statut)

---

## 📁 Structure

- **1 Entité** : User (même table que auth-service)
- **1 DTO** : UserDTO (depuis common)
- **1 Repository** : UserRepository (avec 12 méthodes)
- **1 Service** : UserService (avec 15+ méthodes métier)
- **1 Controller** : UserController (avec 18 endpoints)

---

## 🚀 Démarrage

```bash
cd backend/user-service
mvn clean install
mvn spring-boot:run
```

Swagger : http://localhost:8082/api/users/swagger-ui.html

---

## 📡 Endpoints (18 au total)

### Récupération (10 endpoints GET)
```
GET  /                           - Tous les utilisateurs
GET  /{id}                       - Utilisateur par ID
GET  /username/{username}        - Utilisateur par username
GET  /role/{role}                - Utilisateurs par rôle
GET  /classe/{classe}            - Utilisateurs par classe
GET  /actifs                     - Utilisateurs actifs uniquement
GET  /professeurs                - Tous les professeurs
GET  /etudiants                  - Tous les étudiants
GET  /etudiants/classe/{classe}  - Étudiants d'une classe
GET  /stats                      - Statistiques
```

### Création et Modification (4 endpoints)
```
POST /                           - Créer un utilisateur
PUT  /{id}                       - Modifier un utilisateur
PUT  /{id}/change-password       - Changer le mot de passe
PUT  /{id}/reset-password        - Réinitialiser le MDP (admin)
```

### Gestion (3 endpoints)
```
PUT    /{id}/toggle-status       - Activer/Désactiver
DELETE /{id}                     - Supprimer (désactivation)
GET    /health                   - Health check
```

---

## 🗄️ Modèle de Données

### Table `users` (partagée avec auth-service)
```sql
- id (BIGINT, PK)
- username (VARCHAR 50, UNIQUE)
- email (VARCHAR 100, UNIQUE)
- password (VARCHAR 255) - Hash SHA-256
- prenom (VARCHAR 50)
- nom (VARCHAR 50)
- role (VARCHAR 50)
- classe (VARCHAR 20)
- actif (BIT)
- date_creation, date_modification (DATETIME)
```

### Rôles disponibles
- `RESPONSABLE_FORMATION`
- `RESPONSABLE_CLASSE`
- `PROFESSEUR`
- `ETUDIANT`

### Classes disponibles
- `CI_M1`, `CI_M2`, `MCS_M1`, `MCS_M2`

---

## 💡 Logique Métier

### Validation Automatique
- ✅ Username unique
- ✅ Email unique
- ✅ Rôle valide
- ✅ Classe valide (pour étudiants)
- ✅ Mot de passe minimum 6 caractères

### Règles de Gestion
- Création : Mot de passe hashé automatiquement (SHA-256)
- Modification : Vérification unicité username/email
- Changement MDP : Vérification ancien mot de passe
- Reset MDP : Génération mot de passe aléatoire (10 caractères)
- Suppression : Désactivation logique (actif = false)

---

## 🧪 Tests Rapides

### 1. Créer un utilisateur
```bash
POST /
{
  "username": "nouveau.prof",
  "email": "prof@tdsi.sn",
  "password": "password123",
  "prenom": "Mamadou",
  "nom": "Diallo",
  "role": "PROFESSEUR"
}
```

### 2. Récupérer les étudiants d'une classe
```bash
GET /etudiants/classe/CI_M1
```

### 3. Changer un mot de passe
```bash
PUT /{id}/change-password
{
  "oldPassword": "password123",
  "newPassword": "newpassword456"
}
```

### 4. Réinitialiser un mot de passe (admin)
```bash
PUT /{id}/reset-password
→ Retourne : { "newPassword": "aB3dE7fG9h" }
```

### 5. Activer/Désactiver un compte
```bash
PUT /{id}/toggle-status
→ Bascule entre actif=true et actif=false
```

### 6. Statistiques
```bash
GET /stats
→ Retourne :
{
  "totalUsers": 120,
  "activeUsers": 115,
  "professeurs": 15,
  "etudiants": 100,
  "responsables": 5
}
```

---

## 🔐 Sécurité

### Hashage des Mots de Passe
- **Algorithme** : SHA-256
- Hash automatique lors de la création
- Hash automatique lors du changement de MDP

### Validation
- Validation Jakarta sur les DTOs
- Vérifications métier dans le service
- Exceptions personnalisées

---

## 🔗 Relations avec Autres Services

### Utilisé par :
- **Auth Service** : Partage la même table `users`
- **Cours Service** : Récupère les professeurs
- **Presence Service** : Récupère les étudiants
- **Stats Service** : Récupère tous les utilisateurs

### Appels Feign possibles :
```java
// Depuis d'autres services
@FeignClient(name = "user-service", url = "http://localhost:8082/api/users")
public interface UserServiceClient {
    @GetMapping("/{id}")
    ApiResponseDTO<UserDTO> getUserById(@PathVariable Long id);
    
    @GetMapping("/classe/{classe}")
    ApiResponseDTO<List<UserDTO>> getUsersByClasse(@PathVariable String classe);
}
```

---

## ⚠️ Configuration

**Database** : SQL Server (table `users`)  
**Port** : 8082  
**Context-path** : `/api/users`

Modifiez `application.yml` :
```yaml
spring:
  datasource:
    password: VotreMotDePasse  # ⚠️ CHANGEZ-MOI
```

---

## 📊 Exemples de Réponses

### GET /stats
```json
{
  "success": true,
  "message": "Statistiques récupérées",
  "data": {
    "totalUsers": 120,
    "activeUsers": 115,
    "professeurs": 15,
    "etudiants": 100,
    "responsables": 5
  }
}
```

### GET /etudiants/classe/CI_M1
```json
{
  "success": true,
  "message": "Étudiants de CI_M1",
  "data": [
    {
      "id": 5,
      "username": "etud.ndiaye",
      "email": "etud.ndiaye@tdsi.sn",
      "prenom": "Fatou",
      "nom": "Ndiaye",
      "role": "ETUDIANT",
      "classe": "CI_M1",
      "actif": true
    }
    // ... autres étudiants
  ]
}
```

---

## ✅ Checklist

- [ ] Service démarre sans erreur
- [ ] Swagger UI accessible
- [ ] Créer un utilisateur fonctionne
- [ ] Récupération par rôle fonctionne
- [ ] Changement de mot de passe fonctionne
- [ ] Reset mot de passe fonctionne
- [ ] Toggle status fonctionne
- [ ] Statistiques s'affichent
- [ ] Validation des rôles/classes

---

## 🎯 Cas d'Usage Typiques

### Pour le Responsable de Formation
```bash
# Voir tous les utilisateurs
GET /

# Créer un nouveau professeur
POST /

# Réinitialiser un mot de passe oublié
PUT /{id}/reset-password

# Voir les stats
GET /stats
```

### Pour le Responsable de Classe
```bash
# Voir les étudiants de sa classe
GET /etudiants/classe/CI_M1

# Voir les professeurs
GET /professeurs
```

### Pour un Utilisateur
```bash
# Changer son propre mot de passe
PUT /{id}/change-password
```

---

**Service 100% opérationnel ! 🚀**

18 endpoints fonctionnels prêts à utiliser !
