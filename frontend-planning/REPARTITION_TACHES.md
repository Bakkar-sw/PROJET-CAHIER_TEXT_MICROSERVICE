# 👥 RÉPARTITION DES TÂCHES FRONTEND

## 🎯 Principe de Répartition

Chaque membre reprend son domaine du backend pour assurer la cohérence et la compréhension globale.

---

## 👤 BOUBACAR SOUARE - Module Authentification

### 📦 Responsabilités
- **Module** : `modules/auth/`
- **Pages** : 4 pages
- **Services** : AuthService (déjà dans core)
- **Layouts** : AuthLayout

### 📄 Pages à Développer

#### 1. Login Page ✅ PRIORITAIRE
- **Fichier** : `login/login.component.ts`
- **Route** : `/login`
- **Formulaire** :
  - Username (required)
  - Password (required, type password)
  - Bouton "Se connecter"
  - Lien "Mot de passe oublié"
- **Logique** :
  - Validation du formulaire
  - Appel `authService.login()`
  - Stockage du token
  - Redirection selon le rôle
- **API** : `POST /api/auth/login`

#### 2. Register Page
- **Fichier** : `register/register.component.ts`
- **Route** : `/register`
- **Formulaire** : Tous les champs utilisateur
- **API** : `POST /api/auth/register`

#### 3. Forgot Password Page
- **Fichier** : `forgot-password/forgot-password.component.ts`
- **Route** : `/forgot-password`
- **Fonction** : Email de réinitialisation

#### 4. Change Password Page
- **Fichier** : `change-password/change-password.component.ts`
- **Route** : `/change-password`
- **Formulaire** :
  - Ancien mot de passe
  - Nouveau mot de passe
  - Confirmer nouveau mot de passe
- **API** : `PUT /api/users/{id}/change-password`

### 📚 Livrables
- [ ] 4 composants fonctionnels
- [ ] AuthLayout (header simple, pas de sidebar)
- [ ] Formulaires avec validation
- [ ] Gestion des erreurs
- [ ] Tests unitaires (optionnel)

---

## 👤 OUSMANE DEME - Module Utilisateurs

### 📦 Responsabilités
- **Module** : `modules/users/`
- **Pages** : 5 pages
- **Services** : UserService (core)

### 📄 Pages à Développer

#### 1. User List ✅ PRIORITAIRE
- **Fichier** : `user-list/user-list.component.ts`
- **Route** : `/users`
- **Features** :
  - Tableau avec DataTable (composant partagé)
  - Filtres : Rôle, Classe, Statut
  - Recherche par nom/username
  - Actions : Modifier, Supprimer, Toggle Statut
  - Pagination
- **API** : `GET /api/users`

#### 2. User Create
- **Fichier** : `user-create/user-create.component.ts`
- **Route** : `/users/create`
- **Formulaire** : Tous les champs
- **Validation** : Username unique, email valide
- **API** : `POST /api/users`

#### 3. User Edit
- **Fichier** : `user-edit/user-edit.component.ts`
- **Route** : `/users/edit/:id`
- **Pré-remplissage** : Charger les données existantes
- **API** : `PUT /api/users/{id}`

#### 4. User Detail
- **Fichier** : `user-detail/user-detail.component.ts`
- **Route** : `/users/detail/:id`
- **Affichage** :
  - Infos utilisateur
  - Stats si étudiant (via StatsService)
  - Bouton Modifier
- **API** : `GET /api/users/{id}`

#### 5. Professeur List
- **Fichier** : `professeur-list/professeur-list.component.ts`
- **Route** : `/users/professeurs`
- **Spécifique** : Liste filtrée des professeurs
- **API** : `GET /api/users/professeurs`

### 📚 Livrables
- [ ] 5 composants fonctionnels
- [ ] Formulaires réactifs avec validation
- [ ] Gestion CRUD complète
- [ ] Confirmation avant suppression
- [ ] Messages de succès/erreur

---

## 👤 ABDOULAYE GUENE - Modules Cours & Matières

### 📦 Responsabilités
- **Modules** : `modules/cours/` + `modules/matieres/`
- **Pages** : 10 pages (6 cours + 4 matières)
- **Services** : CoursService + MatiereService

### 📄 Pages Cours (6 pages)

#### 1. Cours List ✅ PRIORITAIRE
- **Fichier** : `cours-list/cours-list.component.ts`
- **Route** : `/cours`
- **Filtres** : Classe, Matière, Professeur, Status, Date
- **API** : `GET /api/cours`

#### 2. Cours Create
- **Fichier** : `cours-create/cours-create.component.ts`
- **Route** : `/cours/create`
- **Formulaire** : Matière, Date, Heures, Salle, Classe
- **API** : `POST /api/cours`

#### 3. Cours Edit
- **Fichier** : `cours-edit/cours-edit.component.ts`
- **Route** : `/cours/edit/:id`
- **API** : `PUT /api/cours/{id}`

#### 4. Cours Detail
- **Fichier** : `cours-detail/cours-detail.component.ts`
- **Route** : `/cours/detail/:id`
- **Affichage** : Infos + Cahier de texte + Présences
- **API** : `GET /api/cours/{id}`

#### 5. Cahier Texte
- **Fichier** : `cahier-texte/cahier-texte.component.ts`
- **Route** : `/cours/:id/cahier-texte`
- **Éditeur** : Zone de texte enrichie (optionnel)
- **API** : `PUT /api/cours/{id}/cahier-texte`

#### 6. Cours Validation
- **Fichier** : `cours-validation/cours-validation.component.ts`
- **Route** : `/cours/:id/valider`
- **Action** : Bouton "Valider le cours"
- **API** : `PUT /api/cours/{id}/valider`

### 📄 Pages Matières (4 pages)

#### 1. Matiere List ✅ PRIORITAIRE
- **Fichier** : `matiere-list/matiere-list.component.ts`
- **Route** : `/matieres`
- **Colonnes** : Nom, Code, Volume, Réalisé, Restant, Alerte
- **API** : `GET /api/matieres`

#### 2. Matiere Create
- **Fichier** : `matiere-create/matiere-create.component.ts`
- **Route** : `/matieres/create`
- **API** : `POST /api/matieres`

#### 3. Matiere Edit
- **Fichier** : `matiere-edit/matiere-edit.component.ts`
- **Route** : `/matieres/edit/:id`
- **API** : `PUT /api/matieres/{id}`

#### 4. Matiere Alertes
- **Fichier** : `matiere-alertes/matiere-alertes.component.ts`
- **Route** : `/matieres/alertes`
- **Liste** : Matières < 12h restantes
- **API** : `GET /api/matieres/alertes`

### 📚 Livrables
- [ ] 10 composants fonctionnels
- [ ] 2 modules (cours + matieres)
- [ ] Formulaires avec sélection (matière, professeur)
- [ ] Indicateurs visuels (alertes)
- [ ] Validation de cours fonctionnelle

---

## 👤 CHEIKH TJIAN DIAW - Modules Dashboard & Stats

### 📦 Responsabilités
- **Modules** : `modules/dashboard/` + `modules/stats/`
- **Pages** : 9 pages (4 dashboards + 5 stats)
- **Services** : StatsService
- **Composants** : StatCard, Chart

### 📄 Pages Dashboard (4 pages)

#### 1. Dashboard Formation ✅ PRIORITAIRE
- **Fichier** : `dashboard-formation/dashboard-formation.component.ts`
- **Route** : `/dashboard/formation`
- **Widgets** :
  - Cartes de stats (total users, cours, absences)
  - Top 5 absences critiques
  - Matières en alerte
  - Graphique présence par classe
- **API** : `GET /api/stats/dashboard/formation`

#### 2. Dashboard Classe
- **Fichier** : `dashboard-classe/dashboard-classe.component.ts`
- **Route** : `/dashboard/classe`
- **Widgets** : Stats de la classe du responsable
- **API** : `GET /api/stats/classe/{classe}`

#### 3. Dashboard Professeur
- **Fichier** : `dashboard-professeur/dashboard-professeur.component.ts`
- **Route** : `/dashboard/professeur`
- **Widgets** : Mes cours, mes matières, stats
- **API** : `GET /api/cours/professeur/{id}`

#### 4. Dashboard Étudiant
- **Fichier** : `dashboard-etudiant/dashboard-etudiant.component.ts`
- **Route** : `/dashboard/etudiant`
- **Widgets** : Mes stats, mes justificatifs, emploi du temps
- **API** : `GET /api/stats/etudiant/{id}`

### 📄 Pages Stats (5 pages)

#### 1. Stats Global
- **Fichier** : `stats-global/stats-global.component.ts`
- **Route** : `/stats/global`
- **Graphiques** : Présence par classe, évolution
- **API** : `GET /api/stats/dashboard/formation`

#### 2. Stats Classe
- **Fichier** : `stats-classe/stats-classe.component.ts`
- **Route** : `/stats/classe/:classe`
- **API** : `GET /api/stats/classe/{classe}`

#### 3. Stats Étudiant
- **Fichier** : `stats-etudiant/stats-etudiant.component.ts`
- **Route** : `/stats/etudiant/:id`
- **Détails** : Présences, absences, taux, justificatifs
- **API** : `GET /api/stats/etudiant/{id}`

#### 4. Absences Critiques
- **Fichier** : `absences-critiques/absences-critiques.component.ts`
- **Route** : `/stats/absences-critiques`
- **Liste** : Étudiants >= 3 absences
- **API** : `GET /api/stats/absences/critiques`

#### 5. Rapports
- **Fichier** : `rapports/rapports.component.ts`
- **Route** : `/stats/rapports`
- **Export** : Génération PDF/Excel (optionnel)

### 📚 Livrables
- [ ] 9 composants fonctionnels
- [ ] Composant StatCard réutilisable
- [ ] Graphiques (NgCharts)
- [ ] Dashboards adaptatifs selon le rôle
- [ ] Widgets interactifs

---

## 👤 ALIOUNE KEBE - Modules Présences & Emploi du Temps

### 📦 Responsabilités
- **Modules** : `modules/presences/` + `modules/emploi-temps/`
- **Pages** : 6 pages (4 presences + 2 emploi du temps)
- **Services** : PresenceService + CoursService

### 📄 Pages Présences (4 pages)

#### 1. Faire l'Appel ✅ PRIORITAIRE
- **Fichier** : `faire-appel/faire-appel.component.ts`
- **Route** : `/presences/appel/:coursId`
- **Interface** :
  - Liste des étudiants de la classe
  - Boutons radio : P / A / R
  - Champ remarque (optionnel)
  - Bouton "Enregistrer les présences"
- **Logique** :
  - Récupérer les étudiants de la classe
  - Enregistrer en batch
- **API** :
  - `GET /api/users/etudiants/classe/{classe}`
  - `POST /api/presences/batch`

#### 2. Liste Émargement
- **Fichier** : `emargement-list/emargement-list.component.ts`
- **Route** : `/presences/cours/:coursId`
- **Affichage** : Tableau de toutes les présences du cours
- **API** : `GET /api/presences/cours/{coursId}`

#### 3. Historique Étudiant
- **Fichier** : `presence-etudiant/presence-etudiant.component.ts`
- **Route** : `/presences/etudiant/:etudiantId`
- **Affichage** : Historique complet
- **API** : `GET /api/presences/etudiant/{etudiantId}`

#### 4. Modifier Présence
- **Fichier** : `presence-edit/presence-edit.component.ts`
- **Route** : `/presences/edit/:id`
- **Formulaire** : Status, Remarque
- **API** : `PUT /api/presences/{id}`

### 📄 Pages Emploi du Temps (2 pages)

#### 1. Emploi du Temps Classe
- **Fichier** : `emploi-temps-classe/emploi-temps-classe.component.ts`
- **Route** : `/emploi-du-temps/classe/:classe`
- **Vue** : Calendrier hebdomadaire
- **API** : `GET /api/cours/classe/{classe}`

#### 2. Mon Emploi du Temps
- **Fichier** : `mon-emploi-temps/mon-emploi-temps.component.ts`
- **Route** : `/emploi-du-temps/mon-emploi`
- **Vue** : Selon le rôle (étudiant ou professeur)
- **API** : Selon le rôle

### 📚 Livrables
- [ ] 6 composants fonctionnels
- [ ] Interface appel intuitive
- [ ] Composant Calendar réutilisable
- [ ] Enregistrement batch optimisé
- [ ] Vue calendrier responsive

---

## 👤 FATOU LEYE - Module Justificatifs

### 📦 Responsabilités
- **Module** : `modules/justificatifs/`
- **Pages** : 5 pages
- **Services** : JustificatifService
- **Composant** : FileUpload

### 📄 Pages à Développer

#### 1. Soumettre Justificatif ✅ PRIORITAIRE
- **Fichier** : `justificatif-create/justificatif-create.component.ts`
- **Route** : `/justificatifs/create`
- **Formulaire** :
  - Sélection cours (dropdown)
  - Motif (textarea)
  - Upload fichier (optionnel)
  - Bouton "Soumettre"
- **Logique** : FormData pour multipart
- **API** : `POST /api/justificatifs`

#### 2. Mes Justificatifs
- **Fichier** : `mes-justificatifs/mes-justificatifs.component.ts`
- **Route** : `/justificatifs/mes-justificatifs`
- **Liste** : Historique de l'étudiant connecté
- **Colonnes** : Date, Cours, Motif, Status, Commentaire
- **API** : `GET /api/justificatifs/etudiant/{id}`

#### 3. Justificatifs en Attente
- **Fichier** : `justificatifs-attente/justificatifs-attente.component.ts`
- **Route** : `/justificatifs/en-attente`
- **Liste** : Justificatifs à traiter (responsables)
- **Actions** : Valider, Refuser
- **API** : `GET /api/justificatifs/en-attente`

#### 4. Détails Justificatif
- **Fichier** : `justificatif-detail/justificatif-detail.component.ts`
- **Route** : `/justificatifs/detail/:id`
- **Affichage** :
  - Toutes les infos
  - Fichier uploadé (lien téléchargement)
  - Boutons Valider/Refuser (si responsable)
- **Modal** : Commentaire pour validation/refus
- **API** :
  - `GET /api/justificatifs/{id}`
  - `PUT /api/justificatifs/{id}/valider`
  - `PUT /api/justificatifs/{id}/refuser`

#### 5. Tous les Justificatifs
- **Fichier** : `justificatif-list/justificatif-list.component.ts`
- **Route** : `/justificatifs`
- **Liste** : Complète avec filtres
- **Filtres** : Status, Classe, Date
- **API** : `GET /api/justificatifs`

### 📚 Livrables
- [ ] 5 composants fonctionnels
- [ ] Composant FileUpload fonctionnel
- [ ] Upload/Download de fichiers
- [ ] Workflow validation/refus
- [ ] Modal de commentaire

---

## 👥 ÉQUIPE - Composants Partagés & Pages Communes

### 📦 Responsabilités Collectives
- **Module** : `shared/` + `layouts/` + `modules/common/`
- **Composants** : Réutilisables
- **Layouts** : App, Auth, Dashboard

### 🧩 Composants Partagés à Créer

#### 1. DataTable (OUSMANE)
- **Fichier** : `shared/components/data-table/`
- **Features** : Tri, Filtres, Pagination, Actions
- **Inputs** : Colonnes, Données, Actions
- **Réutilisable** : Tous les tableaux

#### 2. StatCard (CHEIKH)
- **Fichier** : `shared/components/stat-card/`
- **Features** : Icône, Titre, Valeur, Variation
- **Design** : Card Bootstrap/Material

#### 3. Calendar (ALIOUNE)
- **Fichier** : `shared/components/calendar/`
- **Features** : Vue semaine, affichage cours
- **Bibliothèque** : FullCalendar ou custom

#### 4. FileUpload (FATOU)
- **Fichier** : `shared/components/file-upload/`
- **Features** : Drag & drop, preview, validation
- **Max size** : 5MB

#### 5. Autres Composants (ÉQUIPE)
- **ConfirmDialog** : Modal de confirmation
- **LoadingSpinner** : Indicateur de chargement
- **AlertMessage** : Notifications
- **UserAvatar** : Avatar utilisateur
- **Badge** : Badge de status

### 📄 Pages Communes

#### 1. Not Found (BOUBACAR)
- **Route** : `**`
- **Message** : Page non trouvée

#### 2. Unauthorized (BOUBACAR)
- **Route** : `/unauthorized`
- **Message** : Accès refusé

#### 3. Profil (OUSMANE)
- **Route** : `/profil`
- **Affichage** : Infos + Modifier

#### 4. About (ÉQUIPE)
- **Route** : `/about`
- **Contenu** : Infos application

### 🏗️ Layouts

#### 1. AppLayout (BOUBACAR)
- Header avec menu
- Sidebar avec navigation
- Zone de contenu
- Footer

#### 2. AuthLayout (BOUBACAR)
- Layout simple sans sidebar
- Centré sur la page

### 📚 Livrables Collectifs
- [ ] 10+ composants réutilisables
- [ ] 3 layouts fonctionnels
- [ ] 4 pages communes
- [ ] Pipes et Directives

---

## 📋 ORDRE DE DÉVELOPPEMENT RECOMMANDÉ

### Phase 1 - Fondations (Semaine 1)
1. **Structure du projet** (ÉQUIPE)
2. **Services Core** (BOUBACAR)
3. **Guards & Interceptors** (BOUBACAR)
4. **Layouts** (BOUBACAR)
5. **Login Page** (BOUBACAR) ✅ PRIORITAIRE

### Phase 2 - Composants Partagés (Semaine 1-2)
1. **DataTable** (OUSMANE)
2. **StatCard** (CHEIKH)
3. **Calendar** (ALIOUNE)
4. **FileUpload** (FATOU)
5. **Autres composants** (ÉQUIPE)

### Phase 3 - Pages Principales (Semaine 2-3)
1. **Dashboards** (CHEIKH)
2. **User List** (OUSMANE)
3. **Cours List** (ABDOULAYE)
4. **Matiere List** (ABDOULAYE)
5. **Faire l'Appel** (ALIOUNE)
6. **Soumettre Justificatif** (FATOU)

### Phase 4 - Pages Secondaires (Semaine 3-4)
1. **CRUD Utilisateurs** (OUSMANE)
2. **CRUD Cours & Matières** (ABDOULAYE)
3. **Présences & Emploi du Temps** (ALIOUNE)
4. **Justificatifs** (FATOU)
5. **Statistiques** (CHEIKH)

### Phase 5 - Finitions (Semaine 4)
1. **Tests** (TOUS)
2. **Responsive** (TOUS)
3. **Optimisations** (TOUS)
4. **Documentation** (TOUS)

---

## ✅ CHECKLIST GLOBALE

### Par Développeur
- [ ] Toutes les pages fonctionnelles
- [ ] Appels API corrects
- [ ] Gestion des erreurs
- [ ] Formulaires validés
- [ ] Messages utilisateur
- [ ] Responsive design
- [ ] Code commenté

### Par Module
- [ ] Module compilable
- [ ] Routing configuré
- [ ] Services intégrés
- [ ] Tests unitaires (optionnel)

### Global
- [ ] Authentification fonctionnelle
- [ ] Navigation fluide
- [ ] Toutes les routes protégées
- [ ] Design cohérent
- [ ] Performance optimisée

---

**Chaque membre disposera d'un README détaillé personnalisé pour ses modules !**
