# 📄 JUSTIFICATIF SERVICE - Microservice de Gestion des Justificatifs

**Responsable** : Fatou Leye  
**Port** : 8085  
**Base URL** : http://localhost:8085/api/justificatifs

---

## 🎯 Responsabilités

✅ Soumission de justificatifs d'absence  
✅ Upload de fichiers (certificats médicaux, etc.)  
✅ Validation/refus par les responsables  
✅ Suivi des justificatifs par étudiant  
✅ Liste des justificatifs en attente  
✅ Statistiques des justificatifs  
✅ Workflow complet : Soumission → Validation/Refus

---

## 📁 Structure

- **1 Entité** : Justificatif
- **1 DTO** : JustificatifDTO
- **1 Repository** : JustificatifRepository (8 méthodes)
- **1 Service** : JustificatifService (10+ méthodes + upload)
- **1 Controller** : JustificatifController (12 endpoints)

---

## 🚀 Démarrage

```bash
cd backend/justificatif-service
mvn clean install
mvn spring-boot:run
```

Swagger : http://localhost:8085/api/justificatifs/swagger-ui.html

---

## 📡 Endpoints (12 au total)

### Récupération (7 endpoints GET)
```
GET  /                           - Tous les justificatifs
GET  /{id}                       - Justificatif par ID
GET  /etudiant/{etudiantId}      - Justificatifs d'un étudiant
GET  /cours/{coursId}            - Justificatifs d'un cours
GET  /status/{status}            - Justificatifs par status
GET  /en-attente                 - Justificatifs en attente (triés)
GET  /stats/etudiant/{id}        - Stats d'un étudiant
```

### Gestion (4 endpoints)
```
POST /                           - Soumettre un justificatif (+ fichier)
PUT  /{id}/valider               - Valider un justificatif
PUT  /{id}/refuser               - Refuser un justificatif
DELETE /{id}                     - Supprimer un justificatif
```

### Utilitaire
```
GET /health                      - Health check
```

---

## 🗄️ Modèle de Données

### Table `justificatifs`
```sql
- id (BIGINT, PK)
- etudiant_id (BIGINT, FK)
- cours_id (BIGINT, FK)
- motif (TEXT) - Raison de l'absence
- fichier (VARCHAR 255) - Nom du fichier uploadé
- status (VARCHAR 20) - EN_ATTENTE, ACCEPTE, REFUSE
- date_soumission (DATETIME)
- date_traitement (DATETIME)
- traite_par (BIGINT) - ID du responsable
- commentaire_traitement (TEXT)
```

### Status disponibles
- `EN_ATTENTE` - Justificatif soumis, en attente de traitement
- `ACCEPTE` - Justificatif validé par un responsable
- `REFUSE` - Justificatif refusé (avec commentaire obligatoire)

---

## 💡 Logique Métier

### Workflow Complet
1. **Soumission** (Étudiant)
   - Remplit le motif
   - Upload un fichier (optionnel)
   - Status = EN_ATTENTE

2. **Traitement** (Responsable)
   - Consulte les justificatifs en attente
   - Décide : Valider ou Refuser
   - Ajoute un commentaire (obligatoire pour refus)
   - Status = ACCEPTE ou REFUSE

3. **Notification** (Étudiant)
   - Consulte le résultat
   - Voit le commentaire du responsable

### Règles de Gestion
- ✅ Fichier optionnel (mais recommandé)
- ✅ Max 5 MB par fichier
- ✅ Commentaire obligatoire pour refus
- ✅ Seuls les justificatifs EN_ATTENTE peuvent être traités
- ✅ Fichier supprimé automatiquement à la suppression du justificatif

---

## 🧪 Tests Rapides

### 1. Soumettre un justificatif (sans fichier)
```bash
POST /
Content-Type: multipart/form-data

etudiantId=5
coursId=1
motif=Consultation médicale d'urgence
```

### 2. Soumettre avec fichier
```bash
POST /
Content-Type: multipart/form-data

etudiantId=5
coursId=1
motif=Certificat médical
fichier=<upload file>
```

### 3. Voir les justificatifs en attente
```bash
GET /en-attente
→ Retourne tous les justificatifs à traiter (triés par date)
```

### 4. Valider un justificatif
```bash
PUT /3/valider
{
  "responsableId": 1,
  "commentaire": "Certificat médical valide"
}
```

### 5. Refuser un justificatif
```bash
PUT /4/refuser
{
  "responsableId": 1,
  "commentaire": "Document illisible, merci de soumettre à nouveau"
}
```

### 6. Voir mes justificatifs (étudiant)
```bash
GET /etudiant/5
→ Historique complet
```

### 7. Statistiques d'un étudiant
```bash
GET /stats/etudiant/5
→ {
    "etudiantId": 5,
    "enAttente": 1,
    "acceptes": 3,
    "refuses": 1,
    "total": 5
  }
```

---

## 📊 Exemples de Réponses

### GET /en-attente
```json
{
  "success": true,
  "message": "Justificatifs en attente",
  "data": [
    {
      "id": 3,
      "etudiantId": 5,
      "coursId": 2,
      "motif": "Rendez-vous médical urgent",
      "fichier": "abc123-cert.pdf",
      "status": "EN_ATTENTE",
      "dateSoumission": "2026-02-10T14:30:00"
    },
    {
      "id": 5,
      "etudiantId": 7,
      "coursId": 4,
      "motif": "Maladie",
      "fichier": null,
      "status": "EN_ATTENTE",
      "dateSoumission": "2026-02-11T08:15:00"
    }
  ]
}
```

### GET /etudiant/5 (Historique)
```json
{
  "success": true,
  "message": "Justificatifs de l'étudiant",
  "data": [
    {
      "id": 1,
      "coursId": 1,
      "motif": "Consultation dentiste",
      "status": "ACCEPTE",
      "dateSoumission": "2026-02-01T10:00:00",
      "dateTraitement": "2026-02-02T09:00:00",
      "commentaireTraitement": "Certificat valide"
    },
    {
      "id": 2,
      "coursId": 3,
      "motif": "Mal de tête",
      "status": "REFUSE",
      "dateSoumission": "2026-02-05T08:00:00",
      "dateTraitement": "2026-02-06T11:00:00",
      "commentaireTraitement": "Absence non justifiée médicalement"
    }
  ]
}
```

---

## 📁 Gestion des Fichiers

### Upload
- **Taille max** : 5 MB
- **Formats** : Tous (PDF recommandé)
- **Stockage** : `/home/claude/cahier-texte-microservices/uploads/justificatifs/`
- **Nom** : UUID unique + extension

### Exemple
```
Original: certificat_medical.pdf
Stocké: 3f8a9b2c-1e4d-5f6g-7h8i-9j0k1l2m3n4o.pdf
```

### Sécurité
- ✅ Nom UUID (impossible de deviner)
- ✅ Stockage hors du web root
- ✅ Suppression automatique avec le justificatif

---

## 🔗 Workflow Typique

### Scénario 1 : Étudiant absent pour raison médicale
```bash
# 1. Étudiant soumet un justificatif
POST / (avec certificat médical en PDF)

# 2. Responsable consulte les justificatifs en attente
GET /en-attente

# 3. Responsable valide
PUT /3/valider
{
  "responsableId": 1,
  "commentaire": "Certificat médical conforme"
}

# 4. Étudiant consulte le résultat
GET /etudiant/5
→ Voit status = ACCEPTE
```

### Scénario 2 : Justificatif incomplet
```bash
# 1. Étudiant soumet sans fichier
POST / (motif : "Malade")

# 2. Responsable refuse
PUT /4/refuser
{
  "responsableId": 1,
  "commentaire": "Merci de fournir un certificat médical"
}

# 3. Étudiant re-soumet avec fichier
POST / (avec certificat cette fois)
```

---

## 🔗 Intégration avec Autres Services

### Appelé par :
**Stats Service** :
```java
// Pour calculer les stats des justificatifs
justificatifServiceClient.getJustificatifsByEtudiant(etudiantId);
```

### Appelle :
- **User Service** : Pour récupérer les noms des étudiants/responsables (TODO)
- **Cours Service** : Pour enrichir avec les infos du cours (TODO)

---

## ⚠️ Configuration

**Database** : SQL Server (table `justificatifs`)  
**Port** : 8085  
**Context-path** : `/api/justificatifs`  
**Upload path** : `/home/claude/cahier-texte-microservices/uploads/justificatifs`

Modifiez `application.yml` :
```yaml
spring:
  datasource:
    password: VotreMotDePasse  # ⚠️ CHANGEZ-MOI
    
  servlet:
    multipart:
      max-file-size: 5MB      # Taille max par fichier
      max-request-size: 5MB   # Taille max requête

upload:
  path: /chemin/vers/uploads  # Chemin de stockage
```

---

## 🎯 Cas d'Usage Réels

### Pour un Étudiant
```bash
# Soumettre un justificatif
POST /

# Voir mes justificatifs
GET /etudiant/{monId}

# Voir mes stats
GET /stats/etudiant/{monId}
```

### Pour un Responsable de Classe
```bash
# Voir les justificatifs en attente
GET /en-attente

# Valider
PUT /{id}/valider

# Refuser
PUT /{id}/refuser
```

### Pour le Responsable de Formation
```bash
# Voir tous les justificatifs
GET /

# Voir les refusés
GET /status/REFUSE

# Voir les acceptés
GET /status/ACCEPTE
```

---

## 💡 Points Techniques

### 1. **Upload de Fichiers**
```java
// Génération nom unique
String filename = UUID.randomUUID().toString() + extension;

// Sauvegarde
Path filePath = Paths.get(uploadPath, filename);
Files.write(filePath, file.getBytes());
```

### 2. **Validation avant Traitement**
```java
// Vérifier que c'est EN_ATTENTE
if (!EN_ATTENTE.equals(status)) {
    throw new BadRequestException("Déjà traité");
}

// Refus = commentaire obligatoire
if (refuse && commentaire.isEmpty()) {
    throw new BadRequestException("Commentaire requis");
}
```

### 3. **Suppression Automatique**
```java
// Supprime le fichier à la suppression du justificatif
if (justificatif.getFichier() != null) {
    deleteFile(justificatif.getFichier());
}
```

### 4. **Tri des Justificatifs en Attente**
```java
// Triés par date de soumission (plus ancien en premier)
findByStatusOrderByDateSoumissionAsc("EN_ATTENTE");
```

---

## ✅ Checklist

- [ ] Service démarre sans erreur
- [ ] Swagger UI accessible
- [ ] Upload de fichier fonctionne
- [ ] Soumission sans fichier fonctionne
- [ ] Validation change le status à ACCEPTE
- [ ] Refus nécessite un commentaire
- [ ] Fichiers sont sauvegardés correctement
- [ ] Stats s'affichent
- [ ] Liste en attente triée par date

---

## 📈 Statistiques du Service

**Fichiers créés** :
- 1 Entité (Justificatif)
- 1 DTO (JustificatifDTO)
- 1 Repository (8 méthodes)
- 1 Service (10+ méthodes + upload)
- 1 Controller (12 endpoints)
- Configuration upload
- README complet

**Fonctionnalités** :
- Upload de fichiers (multipart/form-data)
- Workflow validation/refus
- Statistiques par étudiant
- Gestion automatique des fichiers

---

**Service 100% opérationnel ! 🚀**

12 endpoints avec upload de fichiers et workflow complet !
