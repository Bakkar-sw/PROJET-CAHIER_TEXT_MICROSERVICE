# 🎨 FRONTEND ANGULAR - CAHIER DE TEXTE TDSI

## 📋 Vue d'Ensemble

Application web Angular pour la gestion du cahier de texte de l'Institut TDSI.

**Framework** : Angular 17+  
**UI** : Bootstrap 5 + Angular Material  
**Backend** : API Gateway (http://localhost:8080)  
**Équipe** : 6 développeurs

---

## 🎯 Fonctionnalités Principales

### Pour le Responsable de Formation
- Dashboard global avec statistiques
- Gestion des utilisateurs (CRUD)
- Vue d'ensemble des cours et matières
- Suivi des absences critiques
- Validation des justificatifs

### Pour le Responsable de Classe
- Dashboard de sa classe
- Faire l'appel (présences)
- Gérer les justificatifs
- Consulter les statistiques de classe

### Pour les Professeurs
- Dashboard personnel
- Planifier des cours
- Saisir le cahier de texte
- Valider les cours
- Voir les stats de ses classes

### Pour les Étudiants
- Dashboard personnel
- Consulter l'emploi du temps
- Soumettre des justificatifs
- Voir ses statistiques de présence
- Consulter le cahier de texte

---

## 🏗️ Architecture

```
frontend/
├── src/
│   ├── app/
│   │   ├── core/                    # Services, Guards, Interceptors
│   │   ├── shared/                  # Composants partagés
│   │   ├── layouts/                 # Layouts (App, Auth, Dashboard)
│   │   └── modules/                 # Modules fonctionnels
│   │       ├── auth/                [BOUBACAR]
│   │       ├── dashboard/           [CHEIKH]
│   │       ├── users/               [OUSMANE]
│   │       ├── cours/               [ABDOULAYE]
│   │       ├── matieres/            [ABDOULAYE]
│   │       ├── presences/           [ALIOUNE]
│   │       ├── justificatifs/       [FATOU]
│   │       ├── stats/               [CHEIKH]
│   │       ├── emploi-temps/        [ALIOUNE]
│   │       └── common/              [ÉQUIPE]
│   ├── assets/
│   ├── environments/
│   └── styles.scss
├── angular.json
├── package.json
└── README.md
```

---

## 🚀 Installation

### Prérequis
- Node.js 18+
- npm 9+
- Angular CLI 17+

### Installation
```bash
# 1. Installer Angular CLI
npm install -g @angular/cli@17

# 2. Cloner le projet
cd frontend

# 3. Installer les dépendances
npm install

# 4. Lancer le serveur de développement
ng serve

# 5. Accéder à l'application
http://localhost:4200
```

---

## 📦 Dépendances Principales

```json
{
  "dependencies": {
    "@angular/core": "^17.0.0",
    "@angular/common": "^17.0.0",
    "@angular/router": "^17.0.0",
    "@angular/forms": "^17.0.0",
    "@angular/material": "^17.0.0",
    "bootstrap": "^5.3.0",
    "@fortawesome/fontawesome-free": "^6.5.0",
    "ng2-charts": "^5.0.0",
    "chart.js": "^4.4.0",
    "rxjs": "^7.8.0",
    "date-fns": "^3.0.0",
    "ngx-toastr": "^18.0.0"
  }
}
```

---

## 🎨 Style Guide

### Conventions de Nommage
- **Composants** : `kebab-case.component.ts`
- **Services** : `kebab-case.service.ts`
- **Modules** : `kebab-case.module.ts`
- **Classes** : `PascalCase`
- **Variables** : `camelCase`

### Structure d'un Composant
```typescript
// Import Angular
import { Component, OnInit } from '@angular/core';

// Import services
import { UserService } from '@core/services/user.service';

// Import models
import { User } from '@core/models/user.model';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {
  // Properties
  users: User[] = [];
  loading = false;
  
  // Constructor
  constructor(private userService: UserService) {}
  
  // Lifecycle hooks
  ngOnInit(): void {
    this.loadUsers();
  }
  
  // Methods
  loadUsers(): void {
    this.loading = true;
    this.userService.getAllUsers().subscribe({
      next: (data) => {
        this.users = data;
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.loading = false;
      }
    });
  }
}
```

---

## 🔐 Authentification & Autorisation

### JWT Token
Le token est stocké dans `localStorage` et ajouté automatiquement à chaque requête via un interceptor.

### Guards
- **AuthGuard** : Protège les routes authentifiées
- **RoleGuard** : Protège les routes selon le rôle

### Exemple de Route Protégée
```typescript
{
  path: 'users',
  canActivate: [AuthGuard, RoleGuard],
  data: { roles: ['RESPONSABLE_FORMATION'] },
  loadChildren: () => import('./modules/users/users.module')
}
```

---

## 📡 Communication avec le Backend

### Base URL
```typescript
// environment.ts
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080'
};
```

### Exemple de Service
```typescript
@Injectable({ providedIn: 'root' })
export class UserService {
  private apiUrl = `${environment.apiUrl}/api/users`;
  
  constructor(private http: HttpClient) {}
  
  getAllUsers(): Observable<User[]> {
    return this.http.get<ApiResponse<User[]>>(this.apiUrl)
      .pipe(map(response => response.data));
  }
}
```

---

## 🎨 Composants Partagés

### DataTable
Tableau réutilisable avec tri, filtres et pagination.
```html
<app-data-table
  [columns]="columns"
  [data]="users"
  [actions]="actions"
  (onEdit)="editUser($event)"
  (onDelete)="deleteUser($event)">
</app-data-table>
```

### StatCard
Carte de statistique.
```html
<app-stat-card
  icon="users"
  title="Total Utilisateurs"
  [value]="totalUsers"
  variant="primary">
</app-stat-card>
```

### Calendar
Calendrier pour l'emploi du temps.
```html
<app-calendar
  [events]="cours"
  (onEventClick)="showCours($event)">
</app-calendar>
```

---

## 📱 Responsive Design

### Breakpoints
- **Mobile** : < 768px
- **Tablet** : 768px - 1024px
- **Desktop** : > 1024px

### Exemple
```scss
.user-card {
  // Mobile
  @media (max-width: 767px) {
    width: 100%;
  }
  
  // Tablet
  @media (min-width: 768px) and (max-width: 1023px) {
    width: 50%;
  }
  
  // Desktop
  @media (min-width: 1024px) {
    width: 33.333%;
  }
}
```

---

## 🧪 Tests

### Tests Unitaires
```bash
ng test
```

### Tests E2E
```bash
ng e2e
```

---

## 📦 Build & Déploiement

### Build de Production
```bash
ng build --configuration production
```

### Fichiers Générés
```
dist/frontend/
├── index.html
├── main.js
├── polyfills.js
├── runtime.js
└── styles.css
```

---

## 👥 Équipe de Développement

| Membre | Module(s) | Pages |
|--------|-----------|-------|
| Boubacar Souare | Auth + Layouts | 4 + Layouts |
| Ousmane Deme | Users | 5 |
| Abdoulaye Guene | Cours + Matières | 10 |
| Cheikh Tjian Diaw | Dashboard + Stats | 9 |
| Alioune Kebe | Présences + Emploi Temps | 6 |
| Fatou Leye | Justificatifs | 5 |
| **TOTAL** | **10 modules** | **43 pages** |

---

## 📚 Documentation Détaillée

Chaque membre dispose d'un README personnalisé :
- `README_BOUBACAR.md` - Module Auth
- `README_OUSMANE.md` - Module Users
- `README_ABDOULAYE.md` - Modules Cours & Matières
- `README_CHEIKH.md` - Modules Dashboard & Stats
- `README_ALIOUNE.md` - Modules Présences & Emploi du Temps
- `README_FATOU.md` - Module Justificatifs

---

## ✅ Checklist de Développement

### Global
- [ ] Structure du projet créée
- [ ] Services Core implémentés
- [ ] Guards & Interceptors fonctionnels
- [ ] Layouts créés
- [ ] Composants partagés développés

### Par Module
- [ ] Routing configuré
- [ ] Components créés
- [ ] Services intégrés
- [ ] Formulaires validés
- [ ] API calls fonctionnels
- [ ] Gestion d'erreurs
- [ ] Design responsive

---

## 🐛 Troubleshooting

### CORS Error
→ Vérifier que le backend (API Gateway) est démarré

### 401 Unauthorized
→ Token expiré, se reconnecter

### Module not found
→ Vérifier les imports et les paths

---

## 📞 Support

Pour toute question, contacter le chef de projet ou consulter la documentation dans `/frontend-planning/`.

---

**Bon développement à toute l'équipe ! 🚀**
