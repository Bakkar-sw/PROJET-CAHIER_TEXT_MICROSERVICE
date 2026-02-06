# ⚡ DÉMARRAGE ULTRA-RAPIDE

## 🎯 Pour commencer EN 5 MINUTES

### 1️⃣ Prérequis (Vérifiez que vous avez tout)

```bash
java --version    # Doit afficher 21.x.x
mvn --version     # Doit afficher 3.9.x
```

Si ça ne fonctionne pas, installez :
- ☕ Java 21 : https://adoptium.net/
- 📦 Maven : https://maven.apache.org/download.cgi

### 2️⃣ SQL Server (5 min)

Ouvrez SQL Server Management Studio et exécutez :

```sql
-- Créer la base
CREATE DATABASE cahier_texte_db;
GO

-- Créer l'utilisateur
CREATE LOGIN admincahiertxt WITH PASSWORD = 'MotDePasse123!';
GO
```

Puis exécutez le fichier : `database/schema.sql` (tout le script complet)

✅ **Testez** : Vous devriez voir 5 tables créées

### 3️⃣ Installer le Module Common (2 min)

```bash
cd backend/common
mvn clean install
```

✅ Attendez "BUILD SUCCESS"

### 4️⃣ Configuration (1 min)

Ouvrez `backend/VOTRE-service/src/main/resources/application.yml`

Modifiez :
```yaml
spring:
  datasource:
    password: MotDePasse123!  # ⬅️ Mettez VOTRE mot de passe SQL Server
```

### 5️⃣ Démarrer Votre Service (2 min)

```bash
cd backend/auth-service  # ou votre-service
mvn spring-boot:run
```

✅ Attendez : "AUTH SERVICE DÉMARRÉ AVEC SUCCÈS !"

### 6️⃣ Tester (1 min)

Ouvrez votre navigateur : **http://localhost:8081/api/auth/swagger-ui.html**

Testez l'endpoint `/login` avec :
```json
{
  "username": "prof.samb",
  "password": "password123"
}
```

✅ Vous devriez recevoir un token JWT !

---

## 🆘 Problèmes ?

### ❌ "Module common non trouvé"
```bash
cd backend/common
mvn clean install
```

### ❌ "Cannot connect to SQL Server"
- Vérifiez que SQL Server est démarré
- Vérifiez le mot de passe dans `application.yml`

### ❌ "Port 8081 already in use"
Changez le port dans `application.yml` :
```yaml
server:
  port: 8090  # ou autre
```

---

## 📋 Pour les Autres Membres

**Si vous n'êtes PAS Boubacar**, suivez ces étapes :

### 1. Copiez auth-service
```bash
cd backend
cp -r auth-service VOTRE-service
cd VOTRE-service
```

### 2. Modifiez le pom.xml
Changez :
```xml
<artifactId>auth-service</artifactId>
```
en :
```xml
<artifactId>VOTRE-service</artifactId>
```

### 3. Modifiez application.yml

Changez le **port** et le **context-path** :

| Service | Port | Context Path |
|---------|------|--------------|
| auth-service | 8081 | /api/auth |
| user-service | 8082 | /api/users |
| cours-service | 8083 | /api/cours |
| presence-service | 8084 | /api/presences |
| justificatif-service | 8085 | /api/justificatifs |
| stats-service | 8086 | /api/stats |

### 4. Renommez les packages
Dans votre IDE :
- Clic droit sur `com.cahiertexte.auth`
- Refactor → Rename
- Changez en `com.cahiertexte.VOTRE_SERVICE`

### 5. Lancez votre service
```bash
mvn clean install
mvn spring-boot:run
```

---

## 🎯 Checklist de Démarrage

- [ ] Java 21 installé
- [ ] Maven installé
- [ ] SQL Server démarré
- [ ] Base de données créée
- [ ] Tables créées (schema.sql)
- [ ] Module common installé (`mvn clean install`)
- [ ] Mot de passe SQL configuré dans application.yml
- [ ] Service démarre sans erreur
- [ ] Swagger UI accessible
- [ ] Endpoint /health fonctionne

---

## 📚 Prochaines Étapes

1. ✅ Lisez le `README.md` principal
2. ✅ Lisez le README de votre service
3. ✅ Lisez `SERVICE_SETUP_GUIDE.md`
4. ✅ Implémentez vos endpoints
5. ✅ Testez avec Swagger
6. ✅ Committez sur Git

---

**Vous êtes prêt ! 🚀**

Des questions ? Lisez les README détaillés ou demandez au chef de projet !
