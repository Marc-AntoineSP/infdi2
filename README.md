# Annuaire - Application de Gestion d'Employés

Application JavaFX de gestion d'annuaire d'entreprise avec authentification, gestion des employés, services et sites.

## 📋 Table des matières

- [Prérequis](#prérequis)
- [Installation](#installation)
- [Lancement](#lancement)
- [Tests](#tests)
- [Structure du projet](#structure-du-projet)
- [Technologies](#technologies)

---

## 🔧 Prérequis

Avant de commencer, assurez-vous d'avoir installé les éléments suivants :

### 1. Make

**Linux/Mac :**

```bash
# Vérifier si Make est installé
make --version

# Installation sur Ubuntu/Debian
sudo apt-get install build-essential

# Installation sur macOS
xcode-select --install
```

**Windows :**

- Installer [Make pour Windows](http://gnuwin32.sourceforge.net/packages/make.htm)
- Ou utiliser WSL (Windows Subsystem for Linux)

### 2. Java 25

```bash
# Vérifier la version Java
java --version

# La sortie doit afficher : java 25.x.x
```

**Installation :**

- Télécharger depuis [Oracle JDK](https://www.oracle.com/java/technologies/downloads/)
- Ou via un gestionnaire de versions comme [SDKMAN](https://sdkman.io/)

### 3. Maven

```bash
# Vérifier si Maven est installé
mvn --version

# Installation sur Ubuntu/Debian
sudo apt-get install maven

# Installation sur macOS
brew install maven

# Installation sur Windows
# Télécharger depuis https://maven.apache.org/download.cgi
```

---

## 📦 Installation

### Étape 1 : Cloner le projet

```bash
git clone <url-du-repo>
cd infdi2
```

### Étape 2 : Lancer l'installation

```bash
make install
```

Cette commande va :

1. Compiler le projet avec Maven
2. Télécharger toutes les dépendances
3. Exécuter les tests unitaires et d'intégration

### Étape 3 : Vérification

✅ **Si tous les tests sont verts** → Installation réussie !

❌ **Si des tests échouent** → Relancez l'installation :

```bash
make install
```

> **Note :** La première installation peut prendre quelques minutes pour télécharger toutes les dépendances Maven.

---

## 🚀 Lancement

### Démarrer l'application

```bash
make start
```

L'application JavaFX se lance avec une interface de connexion.

### Comptes de test

L'application est pré-remplie avec des utilisateurs de test :

#### 👤 Compte Administrateur

- **Email :** `test.admin@example.com`
- **Mot de passe :** `password`
- **Privilèges :** Accès complet (ajouter/modifier/supprimer employés, gérer services et sites)

#### 👥 Compte Employé

- **Email :** `test.employee@example.com`
- **Mot de passe :** `password`
- **Privilèges :** Lecture seule (consultation de l'annuaire)

---

## 🧪 Tests

### Exécuter tous les tests

```bash
make test
```

## 🛠️ Technologies

### Backend

- **Java 25** - Langage principal
- **Hibernate 6.4.10** - ORM (Object-Relational Mapping)
- **H2 Database 2.2.224** - Base de données in-memory
- **Jakarta Bean Validation** - Validation des entités
- **BCrypt** - Hachage des mots de passe

### Frontend

- **JavaFX 25** - Framework d'interface graphique
- **AtlantaFX** - Thème moderne (Dracula)

### Tests

- **JUnit 5 Jupiter 5.10.0** - Framework de tests
- **Maven Surefire 3.5.2** - Exécution des tests

### Build & Outils

- **Maven 3.x** - Gestion de dépendances et build
- **Make** - Automatisation des commandes
- **PDFBox 3.0.5** - Génération de rapports PDF
- **Gson 2.10.1** - Parsing JSON

## 🐛 Dépannage

### Problème : "java: command not found"

→ Java 25 n'est pas installé ou pas dans le PATH. Vérifiez avec `java --version`

### Problème : "mvn: command not found"

→ Maven n'est pas installé. Installez-le selon votre OS (voir [Prérequis](#prérequis))

### Problème : Tests échouent au premier lancement

→ Normal si la base de données n'est pas initialisée. Relancez `make install`

### Problème : "Port already in use" ou erreur de connexion DB

→ Une instance de l'application est déjà en cours. Fermez-la et relancez.
