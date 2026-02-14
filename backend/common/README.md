# 📦 Module Common - Cahier Texte TDSI

## 🎯 Description

Module partagé contenant les DTOs, exceptions, utilitaires et constantes utilisés par tous les microservices.

## 📂 Structure

```
common/
├── dto/                    # Data Transfer Objects
│   ├── UserDTO.java
│   ├── AuthResponseDTO.java
│   ├── LoginRequestDTO.java
│   └── ApiResponseDTO.java
├── exception/              # Exceptions personnalisées
│   ├── ResourceNotFoundException.java
│   ├── UnauthorizedException.java
│   └── BadRequestException.java
├── util/                   # Classes utilitaires
│   ├── JwtUtil.java       # Gestion des tokens JWT
│   └── PasswordUtil.java  # Hashage des mots de passe
└── constants/              # Constantes de l'application
    └── AppConstants.java
```

## 🔧 Installation

### Dans votre microservice, ajoutez la dépendance :

```xml
<dependency>
    <groupId>com.cahiertexte</groupId>
    <artifactId>common</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Compilez et installez le module common dans votre repository Maven local :

```bash
cd backend/common
mvn clean install
```

## 💡 Utilisation

### 1. DTOs

```java
// Exemple d'utilisation de UserDTO
UserDTO user = new UserDTO();
user.setUsername("prof.samb");
user.setRole(AppConstants.Roles.PROFESSEUR);
user.hidePassword(); // Masquer le mot de passe avant envoi

// Exemple d'utilisation de ApiResponseDTO
ApiResponseDTO<UserDTO> response = ApiResponseDTO.success("Utilisateur créé", user);
return ResponseEntity.ok(response);
```

### 2. JWT Util

```java
@Autowired
private JwtUtil jwtUtil;

// Générer un token
String token = jwtUtil.generateToken(username, userId, role);

// Valider un token
boolean isValid = jwtUtil.validateToken(token);

// Extraire des informations
String username = jwtUtil.extractUsername(token);
Long userId = jwtUtil.extractUserId(token);
String role = jwtUtil.extractRole(token);
```

### 3. Password Util

```java
// Hasher un mot de passe
String hashedPassword = PasswordUtil.hashPassword("password123");

// Vérifier un mot de passe
boolean isValid = PasswordUtil.verifyPassword("password123", hashedPassword);

// Générer un mot de passe aléatoire
String randomPassword = PasswordUtil.generateRandomPassword(12);
```

### 4. Constantes

```java
// Utilisation des constantes
if (user.getRole().equals(AppConstants.Roles.PROFESSEUR)) {
    // Logique professeur
}

if (cours.getStatus().equals(AppConstants.Status.VALIDE)) {
    // Cours validé
}
```

### 5. Exceptions

```java
// Lever une exception
throw new ResourceNotFoundException("Utilisateur", "id", userId);

// Dans le GlobalExceptionHandler
@ExceptionHandler(ResourceNotFoundException.class)
public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ApiResponseDTO.error(ex.getMessage()));
}
```

## 📝 DTOs Disponibles

### UserDTO
Contient les informations d'un utilisateur (id, username, email, role, classe, etc.)

### AuthResponseDTO
Réponse d'authentification avec le token JWT et les infos utilisateur

### LoginRequestDTO
Requête de connexion (username, password)

### ApiResponseDTO<T>
Réponse générique pour toutes les API (success, message, data, error, timestamp)

## 🔐 Sécurité

### Configuration JWT
Les paramètres JWT peuvent être configurés dans `application.yml` :

```yaml
jwt:
  secret: votre-cle-secrete-ultra-secure-minimum-256-bits
  expiration: 86400000 # 24 heures en ms
```

## 🚀 Bonnes Pratiques

1. **Toujours masquer les mots de passe** avant de retourner un UserDTO :
   ```java
   user.hidePassword();
   ```

2. **Utiliser ApiResponseDTO** pour toutes les réponses :
   ```java
   return ResponseEntity.ok(ApiResponseDTO.success(data));
   ```

3. **Lever des exceptions appropriées** :
   ```java
   throw new ResourceNotFoundException("Cours", "id", coursId);
   throw new UnauthorizedException("Accès refusé");
   throw new BadRequestException("Données invalides");
   ```

4. **Utiliser les constantes** au lieu de chaînes hardcodées :
   ```java
   // ❌ Mauvais
   if (role.equals("PROFESSEUR"))
   
   // ✅ Bon
   if (role.equals(AppConstants.Roles.PROFESSEUR))
   ```

## 📚 Évolutions Futures

- [ ] Ajouter des DTOs pour Cours, Matiere, Presence, Justificatif
- [ ] Implémenter BCrypt au lieu de SHA-256
- [ ] Ajouter des validators personnalisés
- [ ] Ajouter des mappers (DTO <-> Entity)

## 👥 Contribution

Ce module est partagé par toute l'équipe. Toute modification doit être :
1. Discutée avec l'équipe
2. Testée
3. Documentée
4. Compatible avec tous les microservices

---

**Auteur** : Équipe Cahier Texte TDSI  
**Version** : 1.0.0
