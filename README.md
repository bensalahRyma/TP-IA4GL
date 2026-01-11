# 📋 TodoManager - Application de Gestion de Tâches

[![Java](https://img.shields.io/badge/Java-11+-orange.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue.svg)](https://maven.apache.org/)
[![Cucumber](https://img.shields.io/badge/Cucumber-7.14.0-green.svg)](https://cucumber.io/)
[![Tests](https://img.shields.io/badge/Tests-41%20scenarios-success.svg)](#)
[![Coverage](https://img.shields.io/badge/Coverage-100%25-brightgreen.svg)](#)

Application complète de gestion de tâches développée dans le cadre du **TP IA4GL** (Utilisation de l'IA pour l'Automatisation des Tests Fonctionnels) - Master 2 Génie Logiciel, Université de Montpellier.

---

## 🎯 Fonctionnalités

### ✅ Gestion complète des tâches

- **Création** de tâches avec titre, description et date d'échéance
- **Consultation** de tâches individuelles ou liste complète
- **Modification** du titre, description et date
- **Finalisation** avec workflow de statuts (PENDING → IN_PROGRESS → DONE)
- **Suppression** de tâches

### 🔍 Recherche et filtrage avancés

- Filtrage par statut (PENDING, IN_PROGRESS, DONE)
- Recherche textuelle dans titre et description
- Tri par date de création ou d'échéance
- Pagination des résultats
- Détection des tâches en retard
- Alertes pour tâches à échéance proche

---

## 📊 Statistiques du projet

| Métrique | Valeur |
|----------|--------|
| **Scénarios de tests** | 41 |
| **Fichiers Feature** | 4 |
| **Step Definitions** | 71 méthodes |
| **Classes métier** | 5 |
| **Couverture fonctionnelle** | 100% |
| **Lignes de code** | ~2,220 |

---

## 🏗️ Architecture

```
todomanager-complet/
├── pom.xml                                 # Configuration Maven
├── README.md                               # Ce fichier
├── GUIDE_UTILISATION.md                    # Guide utilisateur
├── ARCHITECTURE.md                         # Documentation architecture
└── src/
    ├── main/java/todomanager/
    │   ├── Todo.java                       # Entité tâche
    │   ├── TodoService.java                # Logique métier
    │   ├── TodoStatus.java                 # Énumération des statuts
    │   ├── TodoNotFoundException.java      # Exception métier
    │   └── PaginationResult.java           # Résultats paginés
    └── test/
        ├── java/todomanager/
        │   ├── CucumberTest.java           # Runner de tests
        │   └── steps/
        │       ├── TodoContext.java        # Contexte partagé
        │       ├── CommonTodoSteps.java    # Steps communs
        │       ├── TodoSteps.java          # Steps CRUD (8)
        │       ├── InProgressSteps.java    # Steps statuts (7)
        │       ├── ListingFilterSteps.java # Steps filtrage (12)
        │       └── DescriptionDateSteps.java # Steps description (14)
        └── resources/features/
            ├── todo.feature                # Scénarios de base
            ├── in_progress_status.feature  # Scénarios statuts
            ├── listing_filters.feature     # Scénarios filtrage
            └── description_date.feature    # Scénarios dates
```

---

## 🚀 Installation et Exécution

### Prérequis

- **Java** 11 ou supérieur
- **Maven** 3.6 ou supérieur

### Installation

```bash
# 1. Naviguer dans le répertoire du projet
cd TP IA

# 2. Compiler le projet
mvn clean compile

# 3. Exécuter les tests
mvn test

# 4. Générer les rapports HTML (optionnel)
mvn verify
```

## ⚙️ Technologies

| Technologie | Version | Usage |
|-------------|---------|-------|
| Java | 11+ | Langage de programmation |
| Maven | 3.6+ | Build et dépendances |
| Cucumber | 7.14.0 | Framework BDD |
| JUnit | 4.13.2 | Tests unitaires |
| Gherkin | 3.0 | Langage de spécification |

---

## 🏛️ Architecture Shared Context

Le projet utilise le pattern **Shared Context**, une best practice Cucumber qui centralise l'état partagé entre toutes les step definitions.

### Avantages

✅ État centralisé et partagé  
✅ Pas de duplication de code  
✅ Scénarios composites illimités  
✅ Maintenance simplifiée  
✅ Testabilité maximale

### Exemple

```java
// TodoContext.java - Contexte partagé
public class TodoContext {
    public TodoService service;
    public Todo todo;
    public List<Todo> resultats;
    public Long todoId;
    public Exception exception;
}

// CommonTodoSteps.java - Exposition du contexte
public class CommonTodoSteps {
    protected static final TodoContext context = new TodoContext();

    @Etantdonnéque("le système TodoManager est disponible")
    public void systeme_disponible() {
        context.service = new TodoService();
        context.todo = null;
        context.exception = null;
    }
}

// TodoSteps.java - Utilisation du contexte
public class TodoSteps {
    private final TodoContext context = CommonTodoSteps.context;
    
    @Quand("l'utilisateur crée une tâche avec le titre {string}")
    public void creer_tache(String titre) {
        context.todo = context.service.createTodo(titre);
    }
}
```
---
## 🎓 Contexte académique

### Projet

- **TP** : IA4GL - Utilisation de l'IA pour l'Automatisation des Tests Fonctionnels
- **Formation** : Master 2 Génie Logiciel
- **Université** : Université de Montpellier
- **Année académique** : 2025-2026

### Étudiante

- **Nom** : Ryma Ben Salah
- **Formation** : Master 2 Génie Logiciel
- **Université** : Université de Montpellier

### Méthodologie

Ce projet a été développé en utilisant :
- ✅ Approche BDD (Behavior-Driven Development)
- ✅ TDD (Test-Driven Development)
- ✅ Pattern Shared Context (best practice Cucumber)
- ✅ Assistance IA (Claude AI - Anthropic)


---

## 🔧 Commandes utiles

### Build et tests

```bash
# Nettoyer le projet
mvn clean

# Compiler
mvn compile

# Exécuter les tests
mvn test

# Package
mvn package

# Tout en une commande
mvn clean test
```

### Rapports

```bash
# Générer les rapports Cucumber
mvn verify

# Ouvrir le rapport HTML
open target/cucumber-reports/cucumber.html
```

### Debug

```bash
# Tests avec logs détaillés
mvn test -X

# Tests en mode debug
mvn test -Dmaven.surefire.debug
```

---

## 🐛 Problèmes connus et solutions

### Problème 1 : Duplicate Step Definitions

**Erreur** : `DuplicateStepDefinitionException`

**Solution** : Vérifier qu'aucune annotation `@Etantdonnéque`, `@Quand`, ou `@Alors` n'est dupliquée dans les step definitions.



## 🚀 Améliorations futures possibles

- [ ] Persistance en base de données (H2, PostgreSQL)
- [ ] Interface REST API
- [ ] Interface web (React, Angular)
- [ ] Authentification et gestion multi-utilisateurs
- [ ] Système de notifications et rappels
- [ ] Application mobile (Android, iOS)
- [ ] Synchronisation cloud
- [ ] Intégration CI/CD

---
