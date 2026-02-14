# ✅ PRESENCE SERVICE - Microservice de Gestion des Présences

**Responsable** : Alioune Kebe  
**Port** : 8084  
**Base URL** : http://localhost:8084/api/presences

---

## 🎯 Responsabilités

✅ Enregistrement des présences/absences/retards  
✅ Liste d'émargement par cours  
✅ Suivi des présences par étudiant  
✅ Enregistrement en batch (tout un cours en une fois)  
✅ Statistiques de présence  
✅ Alertes absences critiques (>= 3)  
✅ Modification de présences  

---

## 📁 Structure

- **1 Entité** : Presence
- **2 DTOs** : PresenceDTO, PresenceBatchDTO
- **1 Repository** : PresenceRepository (10 méthodes)
- **1 Service** : PresenceService (10+ méthodes)
- **1 Controller** : PresenceController (11 endpoints)

---

## 🚀 Démarrage

```bash
cd backend/presence-service
mvn clean install
mvn spring-boot:run
```

Swagger : http://localhost:8084/api/presences/swagger-ui.html

---

## 📡 Endpoints (11 au total)

### Récupération (6 endpoints GET)
```
GET  /                           - Toutes les présences
GET  /{id}                       - Présence par ID
GET  /cours/{coursId}            - Présences d'un cours (émargement)
GET  /etudiant/{etudiantId}      - Présences d'un étudiant
GET  /status/{status}            - Présences par status
GET  /stats/etudiant/{id}        - Stats d'un étudiant
```

### Création et Modification (3 endpoints)
```
POST /                           - Créer une présence
POST /batch                      - Créer en batch (tout un cours)
PUT  /{id}                       - Modifier une présence
```

### Suppression (2 endpoints)
```
DELETE /{id}                     - Supprimer une présence
DELETE /cours/{coursId}          - Supprimer toutes les présences d'un cours
```

### Utilitaire
```
GET /health                      - Health check
```

---

## 🗄️ Modèle de Données

### Table `presences`
```sql
- id (BIGINT, PK)
- cours_id (BIGINT, FK)
- etudiant_id (BIGINT, FK)
- status (VARCHAR 20) - PRESENT, ABSENT, RETARD
- remarque (VARCHAR 255)
- date_creation, date_modification (DATETIME)
- UNIQUE(cours_id, etudiant_id) - Un étudiant = une présence par cours
```

### Status disponibles
- `PRESENT` - Étudiant présent
- `ABSENT` - Étudiant absent
- `RETARD` - Étudiant en retard

---

## 💡 Logique Métier

### Contrainte Unique
- **Un étudiant ne peut avoir qu'UNE présence par cours**
- Tentative de doublon → Erreur

### Validation
- ✅ Status valide (PRESENT, ABSENT, RETARD)
- ✅ Cours et étudiant obligatoires
- ✅ Vérification doublon automatique

### Enregistrement Batch
- Enregistre plusieurs étudiants en une seule requête
- Skip les doublons automatiquement
- Continue même si erreur sur un étudiant

---

## 🧪 Tests Rapides

### 1. Enregistrer une présence unique
```bash
POST /
{
  "coursId": 1,
  "etudiantId": 5,
  "status": "PRESENT",
  "remarque": "RAS"
}
```

### 2. Enregistrer en batch (tout un cours)
```bash
POST /batch
{
  "coursId": 1,
  "presences": [
    {
      "etudiantId": 5,
      "status": "PRESENT"
    },
    {
      "etudiantId": 6,
      "status": "ABSENT",
      "remarque": "Malade"
    },
    {
      "etudiantId": 7,
      "status": "RETARD",
      "remarque": "Arrivé 15min en retard"
    }
  ]
}
```

### 3. Liste d'émargement d'un cours
```bash
GET /cours/1
→ Retourne toutes les présences du cours 1
```

### 4. Historique d'un étudiant
```bash
GET /etudiant/5
→ Retourne toutes les présences de l'étudiant 5
```

### 5. Statistiques d'un étudiant
```bash
GET /stats/etudiant/5
→ Retourne :
{
  "etudiantId": 5,
  "totalSeances": 10,
  "presences": 8,
  "absences": 2,
  "retards": 0,
  "tauxPresence": 80.0,
  "alerteAbsence": false
}
```

### 6. Modifier une présence
```bash
PUT /3
{
  "status": "PRESENT",
  "remarque": "Justificatif accepté"
}
```

---

## 🔗 Workflow Typique

### Scénario 1 : Professeur fait l'appel
```bash
# 1. Récupérer les étudiants de la classe (depuis user-service)
GET http://localhost:8082/api/users/etudiants/classe/CI_M1

# 2. Enregistrer toutes les présences en une fois
POST /batch
{
  "coursId": 1,
  "presences": [
    { "etudiantId": 5, "status": "PRESENT" },
    { "etudiantId": 6, "status": "ABSENT" },
    { "etudiantId": 7, "status": "PRESENT" }
    // ... tous les étudiants
  ]
}
```

### Scénario 2 : Correction d'une erreur
```bash
# Étudiant arrive après l'appel
PUT /15
{
  "status": "RETARD",
  "remarque": "Arrivé à 8h15"
}
```

### Scénario 3 : Étudiant consulte ses absences
```bash
# Voir toutes ses présences
GET /etudiant/5

# Voir ses stats
GET /stats/etudiant/5
```

---

## 📊 Exemples de Réponses

### GET /cours/1 (Liste d'émargement)
```json
{
  "success": true,
  "message": "Présences du cours",
  "data": [
    {
      "id": 1,
      "coursId": 1,
      "etudiantId": 5,
      "status": "PRESENT",
      "remarque": null
    },
    {
      "id": 2,
      "coursId": 1,
      "etudiantId": 6,
      "status": "ABSENT",
      "remarque": "Malade"
    }
  ]
}
```

### GET /stats/etudiant/5
```json
{
  "success": true,
  "message": "Statistiques récupérées",
  "data": {
    "etudiantId": 5,
    "totalSeances": 10,
    "presences": 8,
    "absences": 1,
    "retards": 1,
    "tauxPresence": 80.0,
    "alerteAbsence": false
  }
}
```

### POST /batch (Enregistrement multiple)
```json
{
  "success": true,
  "message": "25 présences enregistrées",
  "data": [
    { "id": 1, "coursId": 1, "etudiantId": 5, "status": "PRESENT" },
    { "id": 2, "coursId": 1, "etudiantId": 6, "status": "ABSENT" },
    // ... 23 autres
  ]
}
```

---

## 🔗 Intégration avec Autres Services

### Utilisé par :
- **Stats Service** : Calcule les taux de présence
- **Justificatif Service** : Lie les justificatifs aux absences

### Appelle :
- **User Service** : Pour enrichir avec les noms des étudiants (TODO)
- **Cours Service** : Pour vérifier que le cours existe (TODO)

---

## ⚠️ Configuration

**Database** : SQL Server (table `presences`)  
**Port** : 8084  
**Context-path** : `/api/presences`

Modifiez `application.yml` :
```yaml
spring:
  datasource:
    password: VotreMotDePasse  # ⚠️ CHANGEZ-MOI
```

---

## 🎯 Cas d'Usage Réels

### Pour le Responsable de Classe
```bash
# Faire l'appel pour un cours
POST /batch

# Corriger une présence
PUT /{id}

# Voir la liste d'émargement
GET /cours/{coursId}
```

### Pour un Étudiant
```bash
# Voir mes présences
GET /etudiant/{monId}

# Voir mes stats
GET /stats/etudiant/{monId}
```

### Pour le Responsable de Formation
```bash
# Voir tous les absents d'un jour
GET /status/ABSENT
```

---

## 💡 Points Techniques

### 1. **Contrainte d'Unicité**
```sql
UNIQUE(cours_id, etudiant_id)
```
→ Impossible d'enregistrer 2 fois le même étudiant pour le même cours

### 2. **Enregistrement Batch Robuste**
- Continue même si erreur sur un item
- Skip les doublons automatiquement
- Retourne le nombre d'enregistrements réussis

### 3. **Calcul Automatique des Stats**
```java
tauxPresence = (presences * 100.0) / totalSeances
alerteAbsence = absences >= 3
```

### 4. **Validation des Status**
- Seuls PRESENT, ABSENT, RETARD acceptés
- Autres valeurs → Erreur 400

---

## ✅ Checklist

- [ ] Service démarre sans erreur
- [ ] Swagger UI accessible
- [ ] Enregistrer une présence fonctionne
- [ ] Enregistrement batch fonctionne
- [ ] Contrainte unicité est respectée
- [ ] Stats s'affichent correctement
- [ ] Modification de présence fonctionne
- [ ] Alertes absences >= 3

---

## 📈 Performance

### Optimisations
- Index sur `cours_id` pour recherches rapides
- Index sur `etudiant_id` pour historique
- Contrainte unique en DB (pas en code)
- Batch pour éviter N requêtes

### Limites
- Pas de pagination (à ajouter si > 1000 présences)
- Pas de cache (à ajouter en production)

---

**Service 100% opérationnel ! 🚀**

11 endpoints fonctionnels avec enregistrement batch optimisé !
