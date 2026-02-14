# 📱 INVENTAIRE COMPLET DES PAGES FRONTEND

## 🎯 Vue d'ensemble

**Framework** : Angular 17+  
**Style** : Bootstrap 5 / Angular Material  
**État** : NgRx (optionnel) ou Services avec RxJS  
**API** : Communication via API Gateway (http://localhost:8080)

---

## 📋 PAGES PAR MODULE

### 1. MODULE AUTHENTIFICATION (4 pages)

#### 1.1 Page Login
- **Route** : `/login`
- **Fichier** : `login.component.ts`
- **Fonction** : Connexion des utilisateurs
- **Champs** : Username, Password
- **API** : `POST /api/auth/login`
- **Redirection** : Selon le rôle (dashboard correspondant)

#### 1.2 Page Register (optionnel)
- **Route** : `/register`
- **Fichier** : `register.component.ts`
- **Fonction** : Inscription nouveaux utilisateurs
- **Champs** : Username, Email, Password, Prenom, Nom, Role, Classe
- **API** : `POST /api/auth/register`

#### 1.3 Page Forgot Password (optionnel)
- **Route** : `/forgot-password`
- **Fichier** : `forgot-password.component.ts`
- **Fonction** : Réinitialisation mot de passe

#### 1.4 Page Change Password
- **Route** : `/change-password`
- **Fichier** : `change-password.component.ts`
- **Fonction** : Changer son mot de passe
- **API** : `PUT /api/users/{id}/change-password`

---

### 2. MODULE DASHBOARD (4 dashboards différents)

#### 2.1 Dashboard Responsable Formation
- **Route** : `/dashboard/formation`
- **Fichier** : `dashboard-formation.component.ts`
- **Fonction** : Vue d'ensemble pour RF
- **Widgets** :
  - Statistiques globales (nombre users, cours, absences)
  - Top 5 étudiants absences critiques
  - Matières en alerte (< 12h)
  - Graphiques de présence par classe
  - Justificatifs en attente
- **API** : `GET /api/stats/dashboard/formation`

#### 2.2 Dashboard Responsable Classe
- **Route** : `/dashboard/classe`
- **Fichier** : `dashboard-classe.component.ts`
- **Fonction** : Vue d'ensemble pour RC
- **Widgets** :
  - Statistiques de sa classe
  - Liste présences du jour
  - Justificatifs à traiter
  - Emploi du temps de la semaine
- **API** : `GET /api/stats/classe/{classe}`

#### 2.3 Dashboard Professeur
- **Route** : `/dashboard/professeur`
- **Fichier** : `dashboard-professeur.component.ts`
- **Fonction** : Vue d'ensemble pour Professeur
- **Widgets** :
  - Mes cours à venir
  - Mes matières (heures restantes)
  - Cours non validés
  - Statistiques de présence de mes classes
- **API** : `GET /api/cours/professeur/{id}`

#### 2.4 Dashboard Étudiant
- **Route** : `/dashboard/etudiant`
- **Fichier** : `dashboard-etudiant.component.ts`
- **Fonction** : Vue d'ensemble pour Étudiant
- **Widgets** :
  - Mes statistiques de présence
  - Mes justificatifs (status)
  - Emploi du temps de la semaine
  - Alertes absences
- **API** : `GET /api/stats/etudiant/{id}`

---

### 3. MODULE UTILISATEURS (5 pages)

#### 3.1 Liste des Utilisateurs
- **Route** : `/users`
- **Fichier** : `user-list.component.ts`
- **Fonction** : Liste tous les utilisateurs
- **Features** :
  - Tableau avec filtres (rôle, classe, statut)
  - Recherche par nom/username
  - Actions : Modifier, Supprimer, Activer/Désactiver
- **API** : `GET /api/users`

#### 3.2 Créer Utilisateur
- **Route** : `/users/create`
- **Fichier** : `user-create.component.ts`
- **Fonction** : Créer un nouvel utilisateur
- **Formulaire** : Tous les champs utilisateur
- **API** : `POST /api/users`

#### 3.3 Modifier Utilisateur
- **Route** : `/users/edit/:id`
- **Fichier** : `user-edit.component.ts`
- **Fonction** : Modifier un utilisateur
- **API** : `PUT /api/users/{id}`

#### 3.4 Détails Utilisateur
- **Route** : `/users/detail/:id`
- **Fichier** : `user-detail.component.ts`
- **Fonction** : Voir les détails d'un utilisateur
- **Affichage** : Infos + Stats si étudiant
- **API** : `GET /api/users/{id}`

#### 3.5 Liste Professeurs
- **Route** : `/users/professeurs`
- **Fichier** : `professeur-list.component.ts`
- **Fonction** : Liste uniquement les professeurs
- **API** : `GET /api/users/professeurs`

---

### 4. MODULE COURS (6 pages)

#### 4.1 Liste des Cours
- **Route** : `/cours`
- **Fichier** : `cours-list.component.ts`
- **Fonction** : Liste tous les cours
- **Filtres** : Classe, Matière, Professeur, Status, Date
- **API** : `GET /api/cours`

#### 4.2 Planifier un Cours
- **Route** : `/cours/create`
- **Fichier** : `cours-create.component.ts`
- **Fonction** : Planifier un nouveau cours
- **Formulaire** : Matière, Date, Heures, Salle, Classe
- **API** : `POST /api/cours`

#### 4.3 Modifier un Cours
- **Route** : `/cours/edit/:id`
- **Fichier** : `cours-edit.component.ts`
- **Fonction** : Modifier un cours planifié
- **API** : `PUT /api/cours/{id}`

#### 4.4 Détails d'un Cours
- **Route** : `/cours/detail/:id`
- **Fichier** : `cours-detail.component.ts`
- **Fonction** : Voir détails + cahier de texte + présences
- **API** : `GET /api/cours/{id}`

#### 4.5 Saisir Cahier de Texte
- **Route** : `/cours/:id/cahier-texte`
- **Fichier** : `cahier-texte.component.ts`
- **Fonction** : Saisir/Modifier le contenu pédagogique
- **API** : `PUT /api/cours/{id}/cahier-texte`

#### 4.6 Valider un Cours (Professeur)
- **Route** : `/cours/:id/valider`
- **Fichier** : `cours-validation.component.ts`
- **Fonction** : Valider qu'un cours a eu lieu
- **API** : `PUT /api/cours/{id}/valider`

---

### 5. MODULE MATIÈRES (4 pages)

#### 5.1 Liste des Matières
- **Route** : `/matieres`
- **Fichier** : `matiere-list.component.ts`
- **Fonction** : Liste toutes les matières
- **Affichage** : Nom, Code, Volume, Réalisé, Restant, Professeur
- **API** : `GET /api/matieres`

#### 5.2 Créer une Matière
- **Route** : `/matieres/create`
- **Fichier** : `matiere-create.component.ts`
- **Fonction** : Créer une nouvelle matière
- **API** : `POST /api/matieres`

#### 5.3 Modifier une Matière
- **Route** : `/matieres/edit/:id`
- **Fichier** : `matiere-edit.component.ts`
- **Fonction** : Modifier une matière
- **API** : `PUT /api/matieres/{id}`

#### 5.4 Matières en Alerte
- **Route** : `/matieres/alertes`
- **Fichier** : `matiere-alertes.component.ts`
- **Fonction** : Liste des matières < 12h restantes
- **API** : `GET /api/matieres/alertes`

---

### 6. MODULE PRÉSENCES (4 pages)

#### 6.1 Faire l'Appel (Professeur/RC)
- **Route** : `/presences/appel/:coursId`
- **Fichier** : `faire-appel.component.ts`
- **Fonction** : Enregistrer les présences d'un cours
- **Interface** : Liste des étudiants avec boutons P/A/R
- **API** : `POST /api/presences/batch`

#### 6.2 Liste d'Émargement
- **Route** : `/presences/cours/:coursId`
- **Fichier** : `emargement-list.component.ts`
- **Fonction** : Voir la liste d'émargement d'un cours
- **API** : `GET /api/presences/cours/{coursId}`

#### 6.3 Historique Présences Étudiant
- **Route** : `/presences/etudiant/:etudiantId`
- **Fichier** : `presence-etudiant.component.ts`
- **Fonction** : Voir toutes les présences d'un étudiant
- **API** : `GET /api/presences/etudiant/{etudiantId}`

#### 6.4 Modifier une Présence
- **Route** : `/presences/edit/:id`
- **Fichier** : `presence-edit.component.ts`
- **Fonction** : Corriger une présence
- **API** : `PUT /api/presences/{id}`

---

### 7. MODULE JUSTIFICATIFS (5 pages)

#### 7.1 Soumettre un Justificatif (Étudiant)
- **Route** : `/justificatifs/create`
- **Fichier** : `justificatif-create.component.ts`
- **Fonction** : Soumettre un justificatif d'absence
- **Formulaire** : Cours, Motif, Upload fichier
- **API** : `POST /api/justificatifs`

#### 7.2 Mes Justificatifs (Étudiant)
- **Route** : `/justificatifs/mes-justificatifs`
- **Fichier** : `mes-justificatifs.component.ts`
- **Fonction** : Voir ses justificatifs
- **API** : `GET /api/justificatifs/etudiant/{id}`

#### 7.3 Justificatifs en Attente (Responsable)
- **Route** : `/justificatifs/en-attente`
- **Fichier** : `justificatifs-attente.component.ts`
- **Fonction** : Liste des justificatifs à traiter
- **Actions** : Valider, Refuser
- **API** : `GET /api/justificatifs/en-attente`

#### 7.4 Détails Justificatif
- **Route** : `/justificatifs/detail/:id`
- **Fichier** : `justificatif-detail.component.ts`
- **Fonction** : Voir détails + fichier
- **Actions** : Valider/Refuser (si responsable)
- **API** : `GET /api/justificatifs/{id}`

#### 7.5 Tous les Justificatifs
- **Route** : `/justificatifs`
- **Fichier** : `justificatif-list.component.ts`
- **Fonction** : Liste complète avec filtres
- **API** : `GET /api/justificatifs`

---

### 8. MODULE STATISTIQUES (5 pages)

#### 8.1 Stats Globales
- **Route** : `/stats/global`
- **Fichier** : `stats-global.component.ts`
- **Fonction** : Vue d'ensemble institut
- **Graphiques** : Présence par classe, évolution
- **API** : `GET /api/stats/dashboard/formation`

#### 8.2 Stats par Classe
- **Route** : `/stats/classe/:classe`
- **Fichier** : `stats-classe.component.ts`
- **Fonction** : Statistiques d'une classe
- **API** : `GET /api/stats/classe/{classe}`

#### 8.3 Stats d'un Étudiant
- **Route** : `/stats/etudiant/:id`
- **Fichier** : `stats-etudiant.component.ts`
- **Fonction** : Détails stats d'un étudiant
- **API** : `GET /api/stats/etudiant/{id}`

#### 8.4 Absences Critiques
- **Route** : `/stats/absences-critiques`
- **Fichier** : `absences-critiques.component.ts`
- **Fonction** : Liste étudiants >= 3 absences
- **API** : `GET /api/stats/absences/critiques`

#### 8.5 Rapports / Export
- **Route** : `/stats/rapports`
- **Fichier** : `rapports.component.ts`
- **Fonction** : Génération de rapports PDF/Excel

---

### 9. MODULE EMPLOI DU TEMPS (2 pages)

#### 9.1 Emploi du Temps Classe
- **Route** : `/emploi-du-temps/classe/:classe`
- **Fichier** : `emploi-temps-classe.component.ts`
- **Fonction** : Vue calendrier des cours d'une classe
- **API** : `GET /api/cours/classe/{classe}`

#### 9.2 Mon Emploi du Temps (Étudiant/Professeur)
- **Route** : `/emploi-du-temps/mon-emploi`
- **Fichier** : `mon-emploi-temps.component.ts`
- **Fonction** : Emploi du temps personnel
- **API** : `GET /api/cours/professeur/{id}` ou `/classe/{classe}`

---

### 10. PAGES COMMUNES (4 pages)

#### 10.1 Page 404
- **Route** : `**`
- **Fichier** : `not-found.component.ts`
- **Fonction** : Page non trouvée

#### 10.2 Page Unauthorized
- **Route** : `/unauthorized`
- **Fichier** : `unauthorized.component.ts`
- **Fonction** : Accès refusé

#### 10.3 Mon Profil
- **Route** : `/profil`
- **Fichier** : `profil.component.ts`
- **Fonction** : Voir/Modifier son profil
- **API** : `GET /api/users/{id}`

#### 10.4 À Propos
- **Route** : `/about`
- **Fichier** : `about.component.ts`
- **Fonction** : Informations sur l'application

---

## 📊 RÉSUMÉ PAR MODULE

| Module | Nombre de Pages |
|--------|----------------|
| Authentification | 4 |
| Dashboard | 4 |
| Utilisateurs | 5 |
| Cours | 6 |
| Matières | 4 |
| Présences | 4 |
| Justificatifs | 5 |
| Statistiques | 5 |
| Emploi du Temps | 2 |
| Pages Communes | 4 |
| **TOTAL** | **43 pages** |

---

## 🔧 COMPOSANTS PARTAGÉS

### 1. Layouts
- **AppLayout** : Layout principal avec sidebar + header
- **AuthLayout** : Layout pour pages login/register
- **DashboardLayout** : Layout avec widgets

### 2. Composants Réutilisables
- **DataTable** : Tableau avec tri/filtres/pagination
- **StatCard** : Carte de statistique
- **Calendar** : Composant calendrier
- **FileUpload** : Upload de fichiers
- **ConfirmDialog** : Modal de confirmation
- **LoadingSpinner** : Indicateur de chargement
- **AlertMessage** : Messages de succès/erreur
- **UserAvatar** : Avatar utilisateur
- **Badge** : Badge de status (actif, en attente, etc.)
- **Chart** : Graphiques (Chart.js ou NgCharts)

### 3. Pipes
- **RolePipe** : Afficher le nom du rôle en français
- **ClassePipe** : Afficher le nom de la classe
- **StatusPipe** : Afficher le status en français
- **DateFormatPipe** : Format de date personnalisé

### 4. Directives
- **HasRoleDirective** : Afficher selon le rôle
- **HighlightDirective** : Surligner les alertes

---

## 📱 PAGES RESPONSIVES

Toutes les pages doivent être **responsive** :
- **Desktop** : Écran > 1024px
- **Tablet** : 768px - 1024px
- **Mobile** : < 768px

---

## ✅ FEATURES TRANSVERSALES

### 1. Authentification
- Login/Logout
- JWT Token dans localStorage
- HTTP Interceptor pour ajouter le token
- Guard pour protéger les routes

### 2. Autorisation
- Route Guards basés sur le rôle
- Affichage conditionnel selon le rôle
- Redirections automatiques

### 3. Gestion d'Erreurs
- Interceptor pour gérer les erreurs HTTP
- Messages d'erreur utilisateur-friendly
- Retry automatique sur erreurs réseau

### 4. State Management
- Services avec BehaviorSubject
- Observable pour les données
- Cache local pour optimisation

---

**Ce document servira de base pour la répartition des pages entre les membres de l'équipe.**
