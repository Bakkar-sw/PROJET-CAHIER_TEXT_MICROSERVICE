# 🏗️ ARCHITECTURE FRONTEND ANGULAR

## 📋 Structure du Projet

```
frontend/
├── src/
│   ├── app/
│   │   ├── core/                    # Services, Guards, Interceptors
│   │   │   ├── guards/
│   │   │   │   ├── auth.guard.ts
│   │   │   │   └── role.guard.ts
│   │   │   ├── interceptors/
│   │   │   │   ├── auth.interceptor.ts
│   │   │   │   └── error.interceptor.ts
│   │   │   ├── services/
│   │   │   │   ├── auth.service.ts
│   │   │   │   ├── user.service.ts
│   │   │   │   ├── cours.service.ts
│   │   │   │   ├── matiere.service.ts
│   │   │   │   ├── presence.service.ts
│   │   │   │   ├── justificatif.service.ts
│   │   │   │   └── stats.service.ts
│   │   │   └── models/
│   │   │       ├── user.model.ts
│   │   │       ├── cours.model.ts
│   │   │       ├── matiere.model.ts
│   │   │       ├── presence.model.ts
│   │   │       ├── justificatif.model.ts
│   │   │       └── api-response.model.ts
│   │   │
│   │   ├── shared/                  # Composants, Pipes, Directives partagés
│   │   │   ├── components/
│   │   │   │   ├── data-table/
│   │   │   │   ├── stat-card/
│   │   │   │   ├── calendar/
│   │   │   │   ├── file-upload/
│   │   │   │   ├── confirm-dialog/
│   │   │   │   ├── loading-spinner/
│   │   │   │   ├── alert-message/
│   │   │   │   ├── user-avatar/
│   │   │   │   ├── badge/
│   │   │   │   └── chart/
│   │   │   ├── pipes/
│   │   │   │   ├── role.pipe.ts
│   │   │   │   ├── classe.pipe.ts
│   │   │   │   ├── status.pipe.ts
│   │   │   │   └── date-format.pipe.ts
│   │   │   ├── directives/
│   │   │   │   ├── has-role.directive.ts
│   │   │   │   └── highlight.directive.ts
│   │   │   └── shared.module.ts
│   │   │
│   │   ├── layouts/                 # Layouts de l'application
│   │   │   ├── app-layout/
│   │   │   │   ├── app-layout.component.ts
│   │   │   │   ├── header/
│   │   │   │   └── sidebar/
│   │   │   ├── auth-layout/
│   │   │   └── dashboard-layout/
│   │   │
│   │   ├── modules/                 # Modules fonctionnels
│   │   │   │
│   │   │   ├── auth/                [BOUBACAR]
│   │   │   │   ├── login/
│   │   │   │   ├── register/
│   │   │   │   ├── forgot-password/
│   │   │   │   ├── change-password/
│   │   │   │   ├── auth-routing.module.ts
│   │   │   │   └── auth.module.ts
│   │   │   │
│   │   │   ├── dashboard/           [CHEIKH]
│   │   │   │   ├── dashboard-formation/
│   │   │   │   ├── dashboard-classe/
│   │   │   │   ├── dashboard-professeur/
│   │   │   │   ├── dashboard-etudiant/
│   │   │   │   ├── dashboard-routing.module.ts
│   │   │   │   └── dashboard.module.ts
│   │   │   │
│   │   │   ├── users/               [OUSMANE]
│   │   │   │   ├── user-list/
│   │   │   │   ├── user-create/
│   │   │   │   ├── user-edit/
│   │   │   │   ├── user-detail/
│   │   │   │   ├── professeur-list/
│   │   │   │   ├── users-routing.module.ts
│   │   │   │   └── users.module.ts
│   │   │   │
│   │   │   ├── cours/               [ABDOULAYE]
│   │   │   │   ├── cours-list/
│   │   │   │   ├── cours-create/
│   │   │   │   ├── cours-edit/
│   │   │   │   ├── cours-detail/
│   │   │   │   ├── cahier-texte/
│   │   │   │   ├── cours-validation/
│   │   │   │   ├── cours-routing.module.ts
│   │   │   │   └── cours.module.ts
│   │   │   │
│   │   │   ├── matieres/            [ABDOULAYE]
│   │   │   │   ├── matiere-list/
│   │   │   │   ├── matiere-create/
│   │   │   │   ├── matiere-edit/
│   │   │   │   ├── matiere-alertes/
│   │   │   │   ├── matieres-routing.module.ts
│   │   │   │   └── matieres.module.ts
│   │   │   │
│   │   │   ├── presences/           [ALIOUNE]
│   │   │   │   ├── faire-appel/
│   │   │   │   ├── emargement-list/
│   │   │   │   ├── presence-etudiant/
│   │   │   │   ├── presence-edit/
│   │   │   │   ├── presences-routing.module.ts
│   │   │   │   └── presences.module.ts
│   │   │   │
│   │   │   ├── justificatifs/       [FATOU]
│   │   │   │   ├── justificatif-create/
│   │   │   │   ├── mes-justificatifs/
│   │   │   │   ├── justificatifs-attente/
│   │   │   │   ├── justificatif-detail/
│   │   │   │   ├── justificatif-list/
│   │   │   │   ├── justificatifs-routing.module.ts
│   │   │   │   └── justificatifs.module.ts
│   │   │   │
│   │   │   ├── stats/               [CHEIKH]
│   │   │   │   ├── stats-global/
│   │   │   │   ├── stats-classe/
│   │   │   │   ├── stats-etudiant/
│   │   │   │   ├── absences-critiques/
│   │   │   │   ├── rapports/
│   │   │   │   ├── stats-routing.module.ts
│   │   │   │   └── stats.module.ts
│   │   │   │
│   │   │   ├── emploi-temps/        [ALIOUNE]
│   │   │   │   ├── emploi-temps-classe/
│   │   │   │   ├── mon-emploi-temps/
│   │   │   │   ├── emploi-temps-routing.module.ts
│   │   │   │   └── emploi-temps.module.ts
│   │   │   │
│   │   │   └── common/              [ÉQUIPE]
│   │   │       ├── not-found/
│   │   │       ├── unauthorized/
│   │   │       ├── profil/
│   │   │       ├── about/
│   │   │       ├── common-routing.module.ts
│   │   │       └── common.module.ts
│   │   │
│   │   ├── app-routing.module.ts
│   │   ├── app.component.ts
│   │   └── app.module.ts
│   │
│   ├── assets/
│   │   ├── images/
│   │   ├── icons/
│   │   └── styles/
│   │
│   ├── environments/
│   │   ├── environment.ts
│   │   └── environment.prod.ts
│   │
│   ├── styles.scss
│   └── index.html
│
├── angular.json
├── package.json
├── tsconfig.json
└── README.md
```

---

## 🎨 Technologies & Bibliothèques

### Core
- **Angular** : 17+ (Standalone ou Modules)
- **TypeScript** : 5+
- **RxJS** : 7+

### UI
- **Bootstrap** : 5.3
- **Angular Material** : 17+ (optionnel)
- **Font Awesome** : 6+ (icônes)
- **NgCharts** : Graphiques (wrapper Chart.js)

### Utilitaires
- **date-fns** : Manipulation de dates
- **ngx-toastr** : Notifications toast
- **ngx-bootstrap** : Composants Bootstrap pour Angular
- **file-saver** : Export de fichiers

---

## 🔐 Services Core

### 1. AuthService
```typescript
export class AuthService {
  login(credentials): Observable<AuthResponse>
  register(user): Observable<any>
  logout(): void
  getToken(): string | null
  isAuthenticated(): boolean
  getCurrentUser(): User | null
  getRole(): string | null
}
```

### 2. UserService
```typescript
export class UserService {
  getAllUsers(): Observable<User[]>
  getUserById(id): Observable<User>
  createUser(user): Observable<User>
  updateUser(id, user): Observable<User>
  deleteUser(id): Observable<void>
  getUsersByRole(role): Observable<User[]>
  getUsersByClasse(classe): Observable<User[]>
}
```

### 3. CoursService
```typescript
export class CoursService {
  getAllCours(): Observable<Cours[]>
  getCoursById(id): Observable<Cours>
  createCours(cours): Observable<Cours>
  updateCours(id, cours): Observable<Cours>
  deleteCours(id): Observable<void>
  saisirCahierTexte(id, texte): Observable<Cours>
  validerCours(id): Observable<Cours>
}
```

### 4. MatiereService
```typescript
export class MatiereService {
  getAllMatieres(): Observable<Matiere[]>
  getMatiereById(id): Observable<Matiere>
  createMatiere(matiere): Observable<Matiere>
  updateMatiere(id, matiere): Observable<Matiere>
  deleteMatiere(id): Observable<void>
  getMatieresEnAlerte(): Observable<Matiere[]>
}
```

### 5. PresenceService
```typescript
export class PresenceService {
  createPresenceBatch(batch): Observable<Presence[]>
  getPresencesByCours(coursId): Observable<Presence[]>
  getPresencesByEtudiant(etudiantId): Observable<Presence[]>
  updatePresence(id, presence): Observable<Presence>
  deletePresence(id): Observable<void>
  getStatsEtudiant(etudiantId): Observable<Stats>
}
```

### 6. JustificatifService
```typescript
export class JustificatifService {
  createJustificatif(formData): Observable<Justificatif>
  getJustificatifsByEtudiant(id): Observable<Justificatif[]>
  getJustificatifsEnAttente(): Observable<Justificatif[]>
  validerJustificatif(id, data): Observable<Justificatif>
  refuserJustificatif(id, data): Observable<Justificatif>
}
```

### 7. StatsService
```typescript
export class StatsService {
  getDashboardFormation(): Observable<DashboardData>
  getStatsClasse(classe): Observable<StatsClasse>
  getStatsEtudiant(id): Observable<StatsEtudiant>
  getAbsencesCritiques(): Observable<StatEtudiant[]>
}
```

---

## 🛡️ Guards

### 1. AuthGuard
```typescript
// Protège les routes nécessitant une authentification
canActivate(): boolean {
  if (authService.isAuthenticated()) {
    return true;
  }
  router.navigate(['/login']);
  return false;
}
```

### 2. RoleGuard
```typescript
// Protège les routes selon le rôle
canActivate(route): boolean {
  const requiredRole = route.data['role'];
  const userRole = authService.getRole();
  
  if (userRole === requiredRole) {
    return true;
  }
  router.navigate(['/unauthorized']);
  return false;
}
```

---

## 🔌 Interceptors

### 1. AuthInterceptor
```typescript
// Ajoute le token JWT à chaque requête
intercept(req, next): Observable<HttpEvent<any>> {
  const token = authService.getToken();
  if (token) {
    req = req.clone({
      setHeaders: { Authorization: `Bearer ${token}` }
    });
  }
  return next.handle(req);
}
```

### 2. ErrorInterceptor
```typescript
// Gère les erreurs HTTP globalement
intercept(req, next): Observable<HttpEvent<any>> {
  return next.handle(req).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 401) {
        authService.logout();
        router.navigate(['/login']);
      }
      toastr.error(error.message);
      return throwError(() => error);
    })
  );
}
```

---

## 🗺️ Routing Configuration

```typescript
const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  
  // Auth (pas de guard)
  {
    path: 'login',
    loadChildren: () => import('./modules/auth/auth.module')
  },
  
  // Dashboard (protégé)
  {
    path: 'dashboard',
    canActivate: [AuthGuard],
    loadChildren: () => import('./modules/dashboard/dashboard.module')
  },
  
  // Users (protégé + role)
  {
    path: 'users',
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['RESPONSABLE_FORMATION', 'RESPONSABLE_CLASSE'] },
    loadChildren: () => import('./modules/users/users.module')
  },
  
  // ... autres routes
  
  { path: '**', component: NotFoundComponent }
];
```

---

## 🎨 Styles & Thème

### Variables SCSS
```scss
// colors.scss
$primary: #0d6efd;
$secondary: #6c757d;
$success: #198754;
$danger: #dc3545;
$warning: #ffc107;
$info: #0dcaf0;

// Classes
.CI_M1 { background: #e3f2fd; }
.CI_M2 { background: #e8f5e9; }
.MCS_M1 { background: #fff3e0; }
.MCS_M2 { background: #fce4ec; }
```

---

## 📱 Responsive Design

### Breakpoints
```scss
$mobile: 576px;
$tablet: 768px;
$desktop: 992px;
$large: 1200px;
```

---

## ✅ Standards de Code

### 1. Conventions de Nommage
- **Composants** : `user-list.component.ts`
- **Services** : `user.service.ts`
- **Models** : `user.model.ts`
- **Guards** : `auth.guard.ts`

### 2. Structure d'un Composant
```typescript
@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {
  users: User[] = [];
  loading = false;
  
  constructor(private userService: UserService) {}
  
  ngOnInit(): void {
    this.loadUsers();
  }
  
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

## 🔄 State Management (Optionnel)

Si le projet grandit, utiliser **NgRx** pour gérer l'état global :
- Actions
- Reducers
- Effects
- Selectors

---

**Cette architecture servira de base pour le développement du frontend par toute l'équipe.**
