# Student CRUD API

API REST de gestion des étudiants développée avec **Quarkus**, **Java** et **Amazon DynamoDB**.

Ce projet met en œuvre les principales opérations CRUD (Create, Read, Update, Delete) à travers une API REST et utilise un environnement DynamoDB local pour le stockage des données.

---

## 🚀 Technologies utilisées

* **Java 17**
* **Quarkus 3.2.0.Final**
* **RESTEasy Reactive**
* **Amazon DynamoDB**
* **AWS SDK for Java**
* **Maven**
* **JUnit 5**
* **REST Assured**
* **DynamoDB Local / environnement DynamoDB compatible local**

---

## 📌 Fonctionnalités

L'API permet de :

* Créer un étudiant
* Récupérer la liste des étudiants
* Récupérer un étudiant par son identifiant
* Modifier les informations d'un étudiant
* Supprimer un étudiant
* Valider les données reçues
* Tester les endpoints REST avec JUnit et REST Assured

---

## 🏗️ Architecture

Le projet adopte une architecture simple en couches :

```text
Client
   │
   ▼
EtudiantResource
   │
   ▼
EtudiantService
   │
   ▼
DynamoDbClient
   │
   ▼
DynamoDB
```

### Composants principaux

**EtudiantResource**

Expose les endpoints REST de l'application.

**EtudiantService**

Contient la logique métier et les opérations de communication avec DynamoDB.

**Etudiant**

Représente le modèle de données d'un étudiant.

**DynamoDB**

Stocke les informations des étudiants dans la table `etudiants`.

---

## 📂 Structure du projet

```text
student-crud/
│
├── src/
│   ├── main/
│   │   ├── java/com/example/
│   │   │   ├── Etudiant.java
│   │   │   ├── EtudiantResource.java
│   │   │   └── EtudiantService.java
│   │   │
│   │   ├── resources/
│   │   │   └── application.properties
│   │   │
│   │   └── docker/
│   │
│   └── test/
│       └── java/com/example/
│           └── EtudiantResourceTest.java
│
├── pom.xml
├── mvnw
├── mvnw.cmd
├── .gitignore
└── README.md
```

---

## ⚙️ Prérequis

Avant de lancer le projet, installer :

* **JDK 17**
* **Maven** (facultatif puisque le projet possède Maven Wrapper)
* **Docker** si vous utilisez un environnement DynamoDB local conteneurisé
* Un environnement **DynamoDB local compatible avec le endpoint `localhost:4566`**

---

# 🔧 Configuration de DynamoDB

L'application est actuellement configurée pour communiquer avec :

```text
http://localhost:4566
```

dans :

```text
src/main/resources/application.properties
```

La table utilisée par l'application est :

```text
etudiants
```

avec :

```text
id : String
nom : String
age : Integer
```

### Exemple de création de la table

Si ton environnement local utilise l'AWS CLI et expose DynamoDB sur le port `4566` :

```bash
aws dynamodb create-table \
  --table-name etudiants \
  --attribute-definitions AttributeName=id,AttributeType=S \
  --key-schema AttributeName=id,KeyType=HASH \
  --billing-mode PAY_PER_REQUEST \
  --endpoint-url http://localhost:4566 \
  --region us-east-1
```

Vérifier ensuite que la table existe :

```bash
aws dynamodb list-tables \
  --endpoint-url http://localhost:4566 \
  --region us-east-1
```

La réponse doit contenir :

```text
etudiants
```

> **Note :** le projet utilise actuellement le port `4566`. Si vous utilisez DynamoDB Local standalone avec son port par défaut `8000`, adaptez `quarkus.dynamodb.endpoint-override` dans `application.properties`.

---

# ▶️ Exécuter le projet

## 1. Cloner le repository

```bash
git clone https://github.com/VOTRE_USERNAME/student-crud.git
```

Puis :

```bash
cd student-crud
```

---

## 2. Lancer l'application en mode développement

### Linux / macOS

```bash
./mvnw quarkus:dev
```

### Windows

```cmd
mvnw.cmd quarkus:dev
```

L'application démarre sur :

```text
http://localhost:8080
```

L'API REST utilise le préfixe :

```text
/api
```

Les endpoints sont donc accessibles sous :

```text
http://localhost:8080/api/etudiants
```

---

# 📡 API Endpoints

| Méthode  | Endpoint              | Description                  |
| -------- | --------------------- | ---------------------------- |
| `POST`   | `/api/etudiants`      | Créer un étudiant            |
| `GET`    | `/api/etudiants`      | Récupérer tous les étudiants |
| `GET`    | `/api/etudiants/{id}` | Récupérer un étudiant        |
| `PUT`    | `/api/etudiants/{id}` | Modifier un étudiant         |
| `DELETE` | `/api/etudiants/{id}` | Supprimer un étudiant        |

---

## ➕ Créer un étudiant

### Request

```http
POST /api/etudiants
Content-Type: application/json
```

### Body

```json
{
  "id": "1",
  "nom": "John",
  "age": 25
}
```

### Réponse

```json
{
  "id": "1",
  "nom": "John",
  "age": 25
}
```

Code HTTP :

```text
201 Created
```

---

## 📋 Récupérer tous les étudiants

```http
GET /api/etudiants
```

Exemple :

```json
[
  {
    "id": "1",
    "nom": "John",
    "age": 25
  },
  {
    "id": "2",
    "nom": "Alice",
    "age": 22
  }
]
```

---

## 🔎 Récupérer un étudiant

```http
GET /api/etudiants/1
```

---

## ✏️ Modifier un étudiant

```http
PUT /api/etudiants/1
Content-Type: application/json
```

Body :

```json
{
  "nom": "Johnny",
  "age": 26
}
```

---

## 🗑️ Supprimer un étudiant

```http
DELETE /api/etudiants/1
```

Réponse :

```text
204 No Content
```

---

# 🧪 Tests

Le projet contient des tests d'intégration utilisant :

* **JUnit 5**
* **REST Assured**
* **Quarkus Test**

Pour lancer les tests :

```bash
./mvnw test
```

Sous Windows :

```cmd
mvnw.cmd test
```

Les tests couvrent notamment :

* création d'un étudiant ;
* récupération d'un étudiant ;
* modification d'un étudiant ;
* suppression d'un étudiant.

---

# 📦 Générer le projet

Pour construire l'application :

```bash
./mvnw package
```

Le build génère les fichiers nécessaires dans le dossier :

```text
target/
```

L'application peut ensuite être exécutée avec :

```bash
java -jar target/quarkus-app/quarkus-run.jar
```

---

# 🐳 Docker

Le projet contient également plusieurs Dockerfiles dans :

```text
src/main/docker/
```

notamment pour les builds JVM et natifs.

---

# 🎯 Objectifs pédagogiques

Ce projet permet de mettre en pratique :

* le développement d'API REST avec Quarkus ;
* l'architecture en couches ;
* l'utilisation de DynamoDB avec le SDK AWS ;
* les opérations CRUD ;
* la validation des données ;
* les tests d'API REST ;
* l'utilisation d'un environnement DynamoDB local ;
* la construction et l'exécution d'une application Java avec Maven.

---

## 👨‍💻 Auteur

**Khalid Abdel-Aziz**

Ingénieur en Informatique
Cloud Computing & Data Security

GitHub :
https://github.com/khalife10

Portfolio :
https://khalid-portfolio-c6rt.vercel.app/

---

## 📄 Licence

Ce projet est un projet académique réalisé à des fins d'apprentissage et de démonstration.
