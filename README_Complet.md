# 🌐 API GATEWAY - Point d'Entrée Centralisé

**Équipe** : TDSI  
**Port** : 8080  
**Base URL** : http://localhost:8080

---

## 🎯 Responsabilités

✅ **Point d'entrée unique** pour tous les clients  
✅ **Routage intelligent** vers les microservices  
✅ **Authentification centralisée** (validation JWT)  
✅ **Configuration CORS** globale  
✅ **Load balancing** (future)  
✅ **Rate limiting** (future)  
✅ **Monitoring centralisé**

---

## 🏗️ Architecture

```
Client (Frontend Angular)
         |
         | HTTP Request
         ↓
   API GATEWAY (Port 8080)
         |
    JWT Validation
         |
    ┌────┴────┬────────┬─────────┬─────────┬──────────┬────────┐
    ↓         ↓        ↓         ↓         ↓          ↓        ↓
  Auth     User    Cours   Presence  Justif    Stats   ...
  8081     8082    8083      8084      8085      8086
```

---

## 🚀 Démarrage

### Prérequis
**TOUS les microservices doivent être démarrés AVANT la gateway !**

```bash
# 1. Démarrer tous les services
cd backend/auth-service && mvn spring-boot:run &
cd backend/user-service && mvn spring-boot:run &
cd backend/cours-service && mvn spring-boot:run &
cd backend/presence-service && mvn spring-boot:run &
cd backend/justificatif-service && mvn spring-boot:run &
cd backend/stats-service && mvn spring-boot:run &

# 2. Démarrer la gateway
cd backend/api-gateway
mvn clean install
mvn spring-boot:run
```

---

## 📡 Routes Disponibles

### Routes Publiques (sans authentification)
```
POST /api/auth/login           → Auth Service
POST /api/auth/register        → Auth Service
GET  /health                   → Gateway Health
GET  /routes                   → Liste des routes
GET  /info                     → Informations gateway
```

### Routes Protégées (authentification requise)
```
# Users
GET    /api/users/**           → User Service
POST   /api/users/**           → User Service
PUT    /api/users/**           → User Service
DELETE /api/users/**           → User Service

# Cours
GET    /api/cours/**           → Cours Service
POST   /api/cours/**           → Cours Service
PUT    /api/cours/**           → Cours Service
DELETE /api/cours/**           → Cours Service

# Matières
GET    /api/matieres/**        → Cours Service (matieres)
POST   /api/matieres/**        → Cours Service (matieres)
PUT    /api/matieres/**        → Cours Service (matieres)
DELETE /api/matieres/**        → Cours Service (matieres)

# Présences
GET    /api/presences/**       → Presence Service
POST   /api/presences/**       → Presence Service
PUT    /api/presences/**       → Presence Service
DELETE /api/presences/**       → Presence Service

# Justificatifs
GET    /api/justificatifs/**   → Justificatif Service
POST   /api/justificatifs/**   → Justificatif Service
PUT    /api/justificatifs/**   → Justificatif Service
DELETE /api/justificatifs/**   → Justificatif Service

# Stats
GET    /api/stats/**           → Stats Service
```

---

## 🔐 Authentification

### Workflow
1. **Login** : Client → `POST /api/auth/login`
2. **Token JWT** : Serveur retourne un token
3. **Requêtes** : Client envoie le token dans le header
4. **Validation** : Gateway valide le token avant de router

### Exemple d'utilisation
```bash
# 1. Se connecter
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"prof.samb","password":"password123"}'

# Response:
{
  "success": true,
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "user": {...}
  }
}

# 2. Utiliser le token pour les autres requêtes
curl -X GET http://localhost:8080/api/users \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

---

## 🛡️ Sécurité

### Filtre JWT
```java
JwtAuthenticationFilter:
1. Extrait le token du header Authorization
2. Valide le token (signature, expiration)
3. Extrait username et role
4. Ajoute X-User-Username et X-User-Role aux headers
5. Route vers le service
```

### Headers Ajoutés
```
X-User-Username: prof.samb
X-User-Role: PROFESSEUR
```

Les services en aval peuvent utiliser ces headers pour identifier l'utilisateur.

---

## 🔧 Configuration

### application.yml
```yaml
server:
  port: 8080

spring:
  cloud:
    gateway:
      routes:
        - id: auth-service
          uri: http://localhost:8081
          predicates:
            - Path=/api/auth/**
          # Pas de filtre JWT pour auth

        - id: user-service
          uri: http://localhost:8082
          predicates:
            - Path=/api/users/**
          filters:
            - JwtAuthenticationFilter  # ✅ Protégé
```

### Routes Exceptées du Filtre JWT
- `/api/auth/login`
- `/api/auth/register`
- `/**/health`

---

## 📊 Endpoints de la Gateway

### GET /health
```json
{
  "success": true,
  "message": "API Gateway is running",
  "port": 8080
}
```

### GET /routes
```json
{
  "success": true,
  "gateway": "http://localhost:8080",
  "services": {
    "auth": "http://localhost:8081/api/auth",
    "users": "http://localhost:8082/api/users",
    "cours": "http://localhost:8083/api/cours",
    "matieres": "http://localhost:8083/api/cours/matieres",
    "presences": "http://localhost:8084/api/presences",
    "justificatifs": "http://localhost:8085/api/justificatifs",
    "stats": "http://localhost:8086/api/stats"
  }
}
```

### GET /info
```json
{
  "application": "Cahier de Texte - API Gateway",
  "version": "1.0.0",
  "description": "Point d'entrée centralisé",
  "services": 6
}
```

---

## 🧪 Tests

### 1. Vérifier que la gateway fonctionne
```bash
curl http://localhost:8080/health
```

### 2. Lister les routes
```bash
curl http://localhost:8080/routes
```

### 3. Tester l'authentification
```bash
# Se connecter via la gateway
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"prof.samb","password":"password123"}'
```

### 4. Tester une route protégée
```bash
# Sans token (doit échouer)
curl http://localhost:8080/api/users

# Avec token (doit réussir)
curl http://localhost:8080/api/users \
  -H "Authorization: Bearer <TOKEN>"
```

---

## 🔄 Workflow Complet

```
1. Client envoie: POST /api/auth/login
   ↓
2. Gateway route vers Auth Service (sans validation JWT)
   ↓
3. Auth Service retourne un token JWT
   ↓
4. Client envoie: GET /api/users (avec token)
   ↓
5. Gateway:
   a. Extrait le token
   b. Valide le token
   c. Ajoute X-User-Username et X-User-Role
   d. Route vers User Service
   ↓
6. User Service traite la requête
   ↓
7. Gateway retourne la réponse au client
```

---

## ⚠️ Points Importants

### 1. **Tous les services doivent être démarrés**
La gateway ne peut pas fonctionner si les services sont down.

### 2. **Un seul point d'entrée pour le frontend**
Le frontend Angular ne communique QU'AVEC la gateway (port 8080).

### 3. **CORS configuré sur la gateway**
Les services n'ont plus besoin de gérer CORS individuellement.

### 4. **Token validé une seule fois**
La gateway valide le JWT, les services font confiance aux headers X-User-*.

---

## 💡 Avantages de la Gateway

### ✅ Pour le Frontend
- **Une seule URL** : `http://localhost:8080`
- **Pas de gestion de multiples URLs**
- **CORS simplifié**

### ✅ Pour les Microservices
- **Délégation de l'auth** : Services n'ont pas à valider JWT
- **Headers enrichis** : username et role déjà disponibles
- **Isolation** : Services ne sont pas exposés directement

### ✅ Pour l'Équipe
- **Monitoring centralisé**
- **Configuration globale** (CORS, JWT)
- **Évolutivité** : Facile d'ajouter de nouveaux services

---

## 🎯 Cas d'Usage

### Frontend Angular
```typescript
// Configuration dans environment.ts
export const environment = {
  apiUrl: 'http://localhost:8080'  // ✅ Une seule URL
};

// Service HTTP
login(credentials) {
  return this.http.post(`${environment.apiUrl}/api/auth/login`, credentials);
}

getUsers() {
  // Token ajouté automatiquement par l'interceptor
  return this.http.get(`${environment.apiUrl}/api/users`);
}
```

---

## 📈 Statistiques

**Routes configurées** : 7 services  
**Port** : 8080  
**Filtres** : 1 (JWT Authentication)  
**Services routés** : 6 microservices  

---

## ✅ Checklist

- [ ] Gateway démarre sans erreur
- [ ] `/health` retourne OK
- [ ] `/routes` liste tous les services
- [ ] Login via gateway fonctionne
- [ ] Token est validé correctement
- [ ] Routes protégées bloquent sans token
- [ ] Routes protégées fonctionnent avec token
- [ ] CORS configuré pour Angular

---

## 🚨 Troubleshooting

### Erreur: "Connection refused"
→ Un service n'est pas démarré. Vérifier tous les services.

### Erreur: "Unauthorized" avec token valide
→ Vérifier que la clé JWT est la même partout.

### Erreur: CORS
→ Vérifier `allowed-origins` dans application.yml.

---

**API Gateway opérationnelle ! 🚀**

**Point d'entrée unique pour l'ensemble du système !**
