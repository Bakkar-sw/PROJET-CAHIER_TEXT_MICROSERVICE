# 👔 GUIDE DU CHEF DE PROJET

## 📋 Ce Qui A Été Créé

### ✅ Module Common (Partagé par tous)
**Localisation** : `backend/common/`

**Contenu** :
- ✅ DTOs communs (UserDTO, AuthResponseDTO, ApiResponseDTO, etc.)
- ✅ Utilitaires (JwtUtil, PasswordUtil)
- ✅ Exceptions (ResourceNotFoundException, UnauthorizedException, BadRequestException)
- ✅ Constantes (Rôles, Classes, Status, etc.)
- ✅ pom.xml avec toutes les dépendances
- ✅ README complet

**Important** : Ce module DOIT être installé en premier (`mvn clean install`)

---

### ✅ Auth Service (Boubacar Souare)
**Localisation** : `backend/auth-service/`

**État** : ✅ 100% FONCTIONNEL

**Contient** :
- ✅ Configuration complète (pom.xml, application.yml)
- ✅ Entité User
- ✅ UserRepository avec méthodes de recherche
- ✅ AuthService avec logique d'authentification
- ✅ AuthController avec 4 endpoints fonctionnels
- ✅ Configuration Spring Security
- ✅ Configuration Swagger/OpenAPI
- ✅ Gestion des exceptions globale
- ✅ README détaillé avec guide complet

**Endpoints** :
- `POST /login` - Authentification
- `POST /register` - Inscription
- `GET /validate` - Validation de token
- `GET /me` - Infos utilisateur
- `GET /health` - Health check

**Port** : 8081
**Swagger** : http://localhost:8081/api/auth/swagger-ui.html

---

### 📋 Services à Compléter (Templates disponibles)

Les autres membres peuvent copier `auth-service` et l'adapter pour leur service.

| Service | Membre | Port | État |
|---------|--------|------|------|
| user-service | Ousmane | 8082 | 📋 Template à copier |
| cours-service | Abdoulaye | 8083 | 📋 Template à copier |
| presence-service | Alioune | 8084 | 📋 Template à copier |
| justificatif-service | Fatou | 8085 | 📋 Template à copier |
| stats-service | Cheikh | 8086 | 📋 Template à copier |

---

### ✅ Base de Données
**Localisation** : `database/schema.sql`

**Contient** :
- ✅ Script de création de toutes les tables
- ✅ Données de test (utilisateurs, matières, cours)
- ✅ Vues utiles pour les statistiques
- ✅ Index pour optimisation

**Tables créées** :
1. `users` - Utilisateurs du système
2. `matieres` - Matières enseignées
3. `cours` - Séances de cours
4. `presences` - Présences/absences
5. `justificatifs` - Justificatifs d'absence

---

### ✅ Documentation
**Localisation** : Racine du projet

**Fichiers** :
- ✅ `README.md` - Documentation principale complète
- ✅ `QUICK_START.md` - Guide de démarrage rapide
- ✅ `SERVICE_SETUP_GUIDE.md` - Guide pour créer un service
- ✅ `.gitignore` - Fichiers à ignorer par Git

---

## 🚀 Étapes pour Démarrer le Projet en Équipe

### Phase 1 : Setup Initial (Toi - Chef de Projet)

1. **Pusher le projet sur GitHub**
```bash
git init
git add .
git commit -m "Initial commit - Project structure"
git remote add origin https://github.com/VOTRE-USERNAME/cahier-texte-microservices.git
git branch -M main
git push -u origin main
```

2. **Créer les branches**
```bash
git checkout -b develop
git push -u origin develop

# Créer une branche par service
git checkout -b feature/auth-service
git checkout -b feature/user-service
git checkout -b feature/cours-service
git checkout -b feature/presence-service
git checkout -b feature/justificatif-service
git checkout -b feature/stats-service
```

3. **Partager le lien du repo avec l'équipe**

---

### Phase 2 : Setup Environnement (Chaque Membre)

**Envoie ces instructions à ton équipe** :

1. **Cloner le projet**
```bash
git clone https://github.com/VOTRE-USERNAME/cahier-texte-microservices.git
cd cahier-texte-microservices
```

2. **Installer SQL Server et créer la base**
- Exécuter `database/schema.sql`

3. **Installer le module common**
```bash
cd backend/common
mvn clean install
```

4. **Tester avec auth-service**
```bash
cd ../auth-service
mvn spring-boot:run
```
- Ouvrir : http://localhost:8081/api/auth/swagger-ui.html

---

### Phase 3 : Développement (Chaque Membre)

**Workflow pour chaque membre** :

1. **Créer sa branche**
```bash
git checkout -b feature/VOTRE-service
```

2. **Copier auth-service**
```bash
cd backend
cp -r auth-service VOTRE-service
```

3. **Adapter le service**
- Modifier `pom.xml`
- Modifier `application.yml` (port, context-path)
- Renommer les packages
- Implémenter les entités
- Implémenter les endpoints

4. **Tester**
```bash
cd VOTRE-service
mvn spring-boot:run
```

5. **Commit réguliers**
```bash
git add .
git commit -m "feat(votre-service): ajout endpoint XXX"
git push origin feature/VOTRE-service
```

6. **Créer une Pull Request**

---

### Phase 4 : Review et Intégration (Toi - Chef de Projet)

**Pour chaque Pull Request** :

1. **Review le code**
- Vérifier que le service démarre
- Tester les endpoints avec Swagger
- Vérifier la qualité du code

2. **Merger dans develop**
```bash
git checkout develop
git merge feature/VOTRE-service
```

3. **Tester l'intégration**
- Démarrer plusieurs services ensemble
- Vérifier qu'il n'y a pas de conflits de ports

---

## 🎯 Responsabilités par Service

### 🔐 Auth Service - Boubacar Souare
**État** : ✅ TERMINÉ

**À faire** : Rien ! C'est le service de référence.

**Optionnel** :
- [ ] Ajouter refresh token
- [ ] Ajouter rate limiting
- [ ] Ajouter forgot password

---

### 👤 User Service - Ousmane Deme
**État** : 📋 À commencer

**Endpoints à implémenter** :
- [ ] GET /users - Liste des utilisateurs
- [ ] GET /users/{id} - Détails d'un utilisateur
- [ ] POST /users - Créer un utilisateur
- [ ] PUT /users/{id} - Modifier un utilisateur
- [ ] DELETE /users/{id} - Supprimer un utilisateur
- [ ] GET /users/role/{role} - Utilisateurs par rôle
- [ ] GET /users/classe/{classe} - Utilisateurs par classe

**Entité** : User (copier de auth-service)

**Dépendances** : Auth Service (pour validation token)

---

### 📚 Cours Service - Abdoulaye Guene
**État** : 📋 À commencer

**Endpoints à implémenter** :
- [ ] GET /cours - Liste des cours
- [ ] GET /cours/{id} - Détails d'un cours
- [ ] POST /cours - Planifier un cours
- [ ] PUT /cours/{id} - Modifier un cours
- [ ] DELETE /cours/{id} - Annuler un cours
- [ ] GET /cours/classe/{classe} - Cours par classe
- [ ] GET /cours/professeur/{profId} - Cours par professeur
- [ ] PUT /cours/{id}/valider - Valider un cours
- [ ] PUT /cours/{id}/cahier-texte - Saisir le cahier de texte

**Entités** :
- Cours (id, matiere_id, professeur_id, classe, date, heures, salle, cahier_texte, status)
- Matiere (id, nom, code, volume_horaire, professeur_id, classe)

**Dépendances** : User Service (pour récupérer les profs)

---

### ✅ Presence Service - Alioune Kebe
**État** : 📋 À commencer

**Endpoints à implémenter** :
- [ ] GET /presences/cours/{coursId} - Présences d'un cours
- [ ] POST /presences - Enregistrer les présences (batch)
- [ ] PUT /presences/{id} - Modifier une présence
- [ ] GET /presences/etudiant/{etudiantId} - Présences d'un étudiant
- [ ] GET /presences/stats/etudiant/{etudiantId} - Stats de présence

**Entité** :
- Presence (id, cours_id, etudiant_id, status, remarque)

**Dépendances** : 
- Cours Service (pour vérifier que le cours existe)
- User Service (pour vérifier que l'étudiant existe)

---

### 📄 Justificatif Service - Fatou Leye
**État** : 📋 À commencer

**Endpoints à implémenter** :
- [ ] GET /justificatifs - Liste des justificatifs
- [ ] GET /justificatifs/{id} - Détails d'un justificatif
- [ ] POST /justificatifs - Soumettre un justificatif
- [ ] PUT /justificatifs/{id}/valider - Valider un justificatif
- [ ] PUT /justificatifs/{id}/refuser - Refuser un justificatif
- [ ] GET /justificatifs/etudiant/{etudiantId} - Justificatifs d'un étudiant
- [ ] GET /justificatifs/en-attente - Justificatifs en attente

**Entité** :
- Justificatif (id, etudiant_id, cours_id, motif, fichier, status, dates)

**Dépendances** :
- Cours Service
- Presence Service (pour lier à une absence)

---

### 📊 Stats Service - Cheikh Tjian Diaw
**État** : 📋 À commencer

**Endpoints à implémenter** :
- [ ] GET /stats/classe/{classe} - Stats par classe
- [ ] GET /stats/etudiant/{etudiantId} - Stats d'un étudiant
- [ ] GET /stats/professeur/{profId} - Stats d'un professeur
- [ ] GET /stats/matieres/alertes - Matières < 12h restantes
- [ ] GET /stats/absences/critiques - Étudiants avec ≥3 absences
- [ ] GET /stats/dashboard/formation - Dashboard global

**Important** : Ce service N'A PAS de table propre. Il appelle les autres services et agrège les données.

**Dépendances** :
- Cours Service
- Presence Service
- User Service

**Utiliser OpenFeign pour les appels** :
```java
@FeignClient(name = "cours-service", url = "http://localhost:8083/api/cours")
public interface CoursServiceClient {
    @GetMapping("/cours/classe/{classe}")
    List<CoursDTO> getCoursByClasse(@PathVariable String classe);
}
```

---

## 🌐 API Gateway (À faire en dernier)

**Quand** : Après que tous les services fonctionnent individuellement

**Technologies** : Spring Cloud Gateway

**Configuration des routes** :
```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: auth-service
          uri: http://localhost:8081
          predicates:
            - Path=/api/auth/**
        
        - id: user-service
          uri: http://localhost:8082
          predicates:
            - Path=/api/users/**
        
        # etc...
```

**Responsabilité** :
- Routage vers les microservices
- Authentification centralisée (validation JWT)
- CORS
- Rate limiting (optionnel)

---

## 📅 Planning Suggéré

### Semaine 1 : Setup
- [x] Jour 1-2 : Chef de projet crée la structure de base ✅
- [ ] Jour 3 : Chaque membre configure son environnement
- [ ] Jour 4 : Chaque membre teste auth-service
- [ ] Jour 5 : Chaque membre crée son service (structure vide)

### Semaine 2 : Développement
- [ ] Jour 1-2 : Chaque membre implémente ses entités et repositories
- [ ] Jour 3-4 : Chaque membre implémente ses services et controllers
- [ ] Jour 5 : Tests individuels

### Semaine 3 : Intégration
- [ ] Jour 1-2 : API Gateway
- [ ] Jour 3-4 : Tests d'intégration
- [ ] Jour 5 : Documentation

### Semaine 4 : Frontend + Finalisation
- [ ] Frontend Angular
- [ ] Tests end-to-end
- [ ] Déploiement

---

## 🧪 Tests d'Intégration

**Comment tester que tout fonctionne ensemble** :

1. **Démarrer tous les services**
```bash
# Terminal 1
cd backend/auth-service && mvn spring-boot:run

# Terminal 2
cd backend/user-service && mvn spring-boot:run

# Terminal 3
cd backend/cours-service && mvn spring-boot:run

# etc...
```

2. **Scénario de test complet** :

```bash
# 1. S'authentifier
POST http://localhost:8081/api/auth/login
{
  "username": "prof.samb",
  "password": "password123"
}
# Récupérer le token

# 2. Créer un cours (avec le token)
POST http://localhost:8083/api/cours
Authorization: Bearer <token>
{
  "matiere_id": 1,
  "classe": "CI_M1",
  "date": "2026-02-15",
  ...
}

# 3. Enregistrer les présences
POST http://localhost:8084/api/presences
Authorization: Bearer <token>
{
  "cours_id": 1,
  "presences": [...]
}

# 4. Voir les stats
GET http://localhost:8086/api/stats/classe/CI_M1
Authorization: Bearer <token>
```

---

## 🐛 Résolution de Problèmes Courants

### Problème : Services ne communiquent pas

**Vérifier** :
1. Tous les services sont démarrés
2. Les ports sont corrects
3. Les URLs dans les FeignClient sont bonnes

### Problème : Conflit de ports

**Solution** : Changer les ports dans application.yml

### Problème : Base de données

**Vérifier** :
1. SQL Server est démarré
2. La base existe
3. Les tables sont créées
4. Les identifiants sont corrects

---

## 📊 Métriques de Succès

**Un service est "terminé" quand** :
- ✅ Il démarre sans erreur
- ✅ Swagger UI est accessible
- ✅ Tous les endpoints retournent les bonnes réponses
- ✅ Les erreurs sont gérées proprement
- ✅ Le README est à jour
- ✅ Le code est clean et commenté

---

## 🎓 Ressources pour l'Équipe

**À partager avec ton équipe** :

1. **Spring Boot** : https://spring.io/guides
2. **JPA** : https://www.baeldung.com/learn-jpa-hibernate
3. **OpenFeign** : https://spring.io/projects/spring-cloud-openfeign
4. **Swagger** : https://springdoc.org/

---

## 💬 Communication d'Équipe

**Canaux recommandés** :

1. **WhatsApp/Telegram** : Communication quotidienne
2. **GitHub Issues** : Bugs et features
3. **GitHub Pull Requests** : Review de code
4. **Réunions hebdomadaires** : Point d'avancement

**Meetings suggérés** :
- Lundi : Planning de la semaine
- Mercredi : Point mi-semaine
- Vendredi : Review et démo

---

## 🏆 Conseils de Gestion

1. **Daily standups (5-10 min)** :
   - Qu'as-tu fait hier ?
   - Que vas-tu faire aujourd'hui ?
   - As-tu des blocages ?

2. **Code reviews obligatoires**
   - Personne ne merge son propre code
   - Au moins 1 review avant merge

3. **Documentation continue**
   - Chaque endpoint doit être documenté
   - Chaque service doit avoir un README

4. **Tests réguliers**
   - Tester AVANT de commit
   - Ne jamais push du code qui ne compile pas

---

**Bon courage Chef ! Tu as tout ce qu'il faut pour réussir ce projet ! 💪🚀**
