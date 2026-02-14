# 📚 COURS SERVICE - Microservice de Gestion des Cours et Matières

**Responsable** : Abdoulaye Guene  
**Port** : 8083  
**Base URL** : http://localhost:8083/api/cours

---

## 🎯 Responsabilités

✅ Gestion des matières (CRUD complet)  
✅ Gestion des cours/séances (planification, modification, validation)  
✅ Saisie du cahier de texte  
✅ Suivi du volume horaire des matières  
✅ Alertes matières (< 12h restantes)  
✅ Validation des cours par les professeurs

---

## 📁 Structure

- **2 Entités** : Matiere, Cours
- **2 DTOs** : MatiereDTO, CoursDTO
- **2 Repositories** : MatiereRepository, CoursRepository
- **2 Services** : MatiereService, CoursService
- **2 Controllers** : MatiereController, CoursController

---

## 🚀 Démarrage

```bash
cd backend/cours-service
mvn clean install
mvn spring-boot:run
```

Swagger : http://localhost:8083/api/cours/swagger-ui.html

---

## 📡 Endpoints Principaux

### MATIÈRES (11 endpoints)
- GET `/matieres` - Liste toutes les matières
- GET `/matieres/{id}` - Détails d'une matière
- GET `/matieres/classe/{classe}` - Matières d'une classe
- GET `/matieres/professeur/{profId}` - Matières d'un professeur
- GET `/matieres/alertes` - Matières en alerte (< 12h)
- POST `/matieres` - Créer une matière
- PUT `/matieres/{id}` - Modifier une matière
- DELETE `/matieres/{id}` - Supprimer une matière

### COURS (13 endpoints)
- GET `/` - Liste tous les cours
- GET `/{id}` - Détails d'un cours
- GET `/classe/{classe}` - Cours d'une classe
- GET `/professeur/{profId}` - Cours d'un professeur
- GET `/matiere/{matiereId}` - Cours d'une matière
- GET `/status/{status}` - Cours par status
- POST `/` - Planifier un cours
- PUT `/{id}` - Modifier un cours
- PUT `/{id}/cahier-texte` - Saisir le cahier de texte
- PUT `/{id}/valider` - Valider un cours
- PUT `/{id}/annuler` - Annuler un cours
- DELETE `/{id}` - Supprimer un cours

**TOTAL : 24 endpoints** ✅

---

## 🗄️ Modèle de Données

### Table `matieres`
```sql
- id (BIGINT, PK)
- nom (VARCHAR 100)
- code (VARCHAR 20, UNIQUE)
- volume_horaire (INT)
- volume_realise (INT)
- professeur_id (BIGINT)
- classe (VARCHAR 20)
- actif (BIT)
- date_creation, date_modification
```

### Table `cours`
```sql
- id (BIGINT, PK)
- matiere_id (BIGINT, FK)
- professeur_id (BIGINT)
- classe (VARCHAR 20)
- date_cours (DATE)
- heure_debut, heure_fin (TIME)
- salle (VARCHAR 50)
- cahier_texte (TEXT)
- status (VARCHAR 20)
- valide_par_prof (BIT)
- date_validation, date_creation, date_modification
```

---

## 💡 Logique Métier

### Workflow d'un Cours
1. **Planification** : Status = PLANIFIE
2. **Saisie cahier de texte** : Ajout du contenu pédagogique
3. **Validation** : Prof valide → Status = VALIDE
4. **Incrémentation** : Volume réalisé de la matière augmente automatiquement

### Règles de Gestion
- ❌ Impossible de modifier un cours validé
- ❌ Impossible de planifier un cours dans le passé
- ❌ Cahier de texte obligatoire avant validation
- ✅ Volume réalisé auto-calculé lors de la validation
- ✅ Alertes si heures restantes < 12

---

## 🧪 Tests Rapides

### 1. Créer une matière
```bash
POST /matieres
{
  "nom": "Sécurité Réseau",
  "code": "SEC301",
  "volumeHoraire": 40,
  "professeurId": 3,
  "classe": "CI_M1"
}
```

### 2. Planifier un cours
```bash
POST /
{
  "matiereId": 1,
  "professeurId": 3,
  "classe": "CI_M1",
  "dateCours": "2026-03-15",
  "heureDebut": "08:00",
  "heureFin": "10:00",
  "salle": "A1"
}
```

### 3. Saisir le cahier de texte
```bash
PUT /{id}/cahier-texte
{
  "cahierTexte": "Introduction aux pare-feu..."
}
```

### 4. Valider le cours
```bash
PUT /{id}/valider
```

---

## ⚠️ Configuration

**Database** : SQL Server (tables `matieres` et `cours`)  
**Port** : 8083  
**Context-path** : `/api/cours`

Modifiez `application.yml` :
```yaml
spring:
  datasource:
    password: VotreMotDePasse  # ⚠️ CHANGEZ-MOI
```

---

## ✅ Checklist

- [ ] Service démarre sans erreur
- [ ] Swagger UI accessible
- [ ] Créer une matière fonctionne
- [ ] Planifier un cours fonctionne
- [ ] Validation d'un cours fonctionne
- [ ] Volume réalisé s'incrémente
- [ ] Alertes matières fonctionnent

---

**Service 100% opérationnel ! 🚀**

Pour la documentation complète, voir les commentaires dans le code source.
