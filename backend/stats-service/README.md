# 📊 STATS SERVICE - Microservice de Statistiques

**Responsable** : Cheikh Tjian Diaw  
**Port** : 8086  
**Base URL** : http://localhost:8086/api/stats

---

## 🎯 Responsabilités

✅ Statistiques de présence des étudiants  
✅ Statistiques globales par classe  
✅ Alertes absences critiques (>= 3)  
✅ Alertes matières (< 12h restantes)  
✅ Dashboard responsable formation  
✅ Taux de présence global

---

## ⚡ PARTICULARITÉ

**Ce service N'A PAS de base de données !**

Il appelle les autres microservices via OpenFeign pour agréger les données.

---

## 🚀 Démarrage

```bash
cd backend/stats-service
mvn clean install
mvn spring-boot:run
```

Swagger : http://localhost:8086/api/stats/swagger-ui.html

---

## 📡 Endpoints (8 disponibles)

1. GET `/health` - Health check
2. GET `/etudiant/{id}` - Stats d'un étudiant
3. GET `/classe/{classe}` - Stats d'une classe
4. GET `/absences/critiques` - Étudiants avec >= 3 absences
5. GET `/matieres/alertes` - Matières < 12h
6. GET `/dashboard/formation` - Dashboard global
7. GET `/global/classes` - Stats toutes classes
8. GET `/taux-presence/global` - Taux global

---

## ⚠️ Dépendances

Nécessite que ces services soient démarrés :
- auth-service (8081)
- user-service (8082)
- presence-service (8084)

---

Voir le README complet dans le dossier du service !
