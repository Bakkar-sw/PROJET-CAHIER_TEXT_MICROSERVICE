# 📚 Cours Service - Abdoulaye Guene

**Microservice** : Cours Service  
**Port** : 8083  
**Context path** : `/api/cours`  
**Branche Git** : `features/cours`

---

## 🏗️ Architecture (conforme au README projet)

```
cours-service/
├── src/main/java/com/cahiertexte/cours/
│   ├── CoursServiceApplication.java   # Point d'entrée
│   ├── config/                        # Security, OpenAPI (Swagger)
│   ├── controller/                    # CoursController (REST)
│   ├── dto/                           # CoursDTO, CoursCreateDTO, CoursUpdateDTO, MatiereDTO
│   ├── exception/                     # GlobalExceptionHandler
│   ├── model/                         # Cours, Matiere (entités JPA)
│   ├── repository/                    # CoursRepository, MatiereRepository
│   └── service/                       # CoursService
└── src/main/resources/
    └── application.yml
```

---

## 📋 Endpoints (README projet)

| Méthode | URL | Description |
|--------|-----|-------------|
| GET | `/cours` | Liste des cours |
| GET | `/cours/{id}` | Détails d'un cours |
| GET | `/cours/classe/{classe}` | Cours par classe |
| GET | `/cours/professeur/{profId}` | Cours par professeur |
| POST | `/cours` | Planifier un cours |
| PUT | `/cours/{id}` | Modifier un cours |
| DELETE | `/cours/{id}` | Annuler / Supprimer un cours |
| PUT | `/cours/{id}/valider` | Valider un cours |
| GET | `/health` | Health check |

---

## 🚀 Démarrer avec Eclipse

### 1. Importer les projets Maven

1. **File** → **Import** → **Maven** → **Existing Maven Projects**.
2. **Root Directory** : sélectionne le dossier du projet  
   `PROJET-CAHIER_TEXT_MICROSERVICE`.
3. Coche au minimum :
   - **backend/common**
   - **backend/cours-service**
4. **Finish**. Attends que Maven télécharge les dépendances.

### 2. Installer le module Common (obligatoire)

Sans cette étape, cours-service ne compilera pas.

1. Clic droit sur le projet **common** → **Run As** → **Maven build...**
2. Dans **Goals**, saisis : `clean install`
3. Clique sur **Run**.
4. Vérifie en bas dans la console : **BUILD SUCCESS**.

### 3. Configurer la base de données

1. Ouvre **cours-service** → `src/main/resources/application.yml`.
2. Vérifie (ou modifie) :
   - `databaseName` : même nom que ta base (ex. `cahier_texte_db_micro`).
   - `username` / `password` : identifiants SQL Server.

La base et les tables doivent exister (exécuter `database/schema.sql` dans SQL Server).

### 4. Lancer le Cours Service

1. Ouvre le fichier :  
   `cours-service/src/main/java/com/cahiertexte/cours/CoursServiceApplication.java`
2. Clic droit sur le fichier → **Run As** → **Java Application**.

Tu dois voir dans la console :

```
==============================================
🚀 COURS SERVICE DÉMARRÉ AVEC SUCCÈS !
📝 Swagger UI: http://localhost:8083/api/cours/swagger-ui.html
==============================================
```

### 5. Tester

- **Health** : http://localhost:8083/api/cours/health  
- **Swagger** : http://localhost:8083/api/cours/swagger-ui.html  

---

## 🐛 Problèmes fréquents avec Eclipse / Maven

### "Cannot resolve com.cahiertexte:common"

- Tu n’as pas fait **Maven build** sur **common** avec la goal `clean install`.
- Refais : clic droit sur **common** → **Run As** → **Maven build...** → Goals : `clean install` → **Run**.

### "Project build error: Unknown packaging: jar" ou erreurs Maven

- Clic droit sur **cours-service** (ou **common**) → **Maven** → **Update Project** (coche **Force Update** si besoin) → **OK**.

### "Address already in use: 8083"

- Un autre programme utilise le port 8083. Arrête-le ou change le port dans `application.yml` :  
  `server.port: 8084` (temporaire).

### "Could not connect to SQL Server"

- Vérifie que SQL Server est démarré.
- Vérifie `application.yml` : `url`, `username`, `password`.
- Vérifie que la base existe et que `schema.sql` a été exécuté.

### Eclipse ne voit pas les sources du projet

- Clic droit sur **cours-service** → **Build Path** → **Configure Build Path** → onglet **Sources** :  
  `src/main/java` doit être présent. Si le projet est bien importé en Maven, c’est normalement déjà le cas.
- Sinon : **Maven** → **Update Project** → **OK**.

---

## 📦 Démarrage en ligne de commande (optionnel)

```bash
# 1. Installer common
cd backend/common
mvn clean install

# 2. Lancer cours-service
cd ../cours-service
mvn spring-boot:run
```

---

## 📅 Convention Git (README projet)

- Branche : `features/cours`
- Commits : `feat(cours-service): description`

---

**Auteur** : Abdoulaye Guene
