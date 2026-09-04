# Yummy Restaurant — Backend

Application web full-stack de gestion pour un restaurant : réservations, chefs, plats et témoignages clients, avec authentification sécurisée par JWT.

## Stack technique

| Couche | Technologie |
|---|---|
| Backend | Spring Boot |
| Sécurité | Spring Security + JWT |
| Base de données | MySQL |
| ORM | JPA / Hibernate |
| Langage | Java 17 |

## Fonctionnalités

- Authentification JWT avec gestion des rôles (Admin / Client)
- Gestion des chefs (CRUD complet + upload de photo)
- Gestion des plats / menu (CRUD complet + upload de photo)
- Gestion des réservations avec statuts (En attente / Confirmée / Annulée)
- Gestion des témoignages clients
- Gestion des utilisateurs et des profils
- Stockage et diffusion de fichiers (photos de chefs et de plats)

## Architecture

```
src/main/java
├── controllers/    → API REST (endpoints)
├── services/        → Logique métier (interfaces)
├── servicesImpl/     → Logique métier (implémentations)
├── repositories/      → Accès base de données (JPA)
├── models/             → Entités (User, Chef, Meal, Reservation, Testimonial...)
├── DTO/                → Objets de transfert (Login, Signup...)
├── security/            → JWT, Spring Security
└── config/               → Sécurité, CORS
```

## Lancer le projet

### Prérequis

- Java 17+
- Maven
- MySQL 8+ (ou XAMPP)

### Configuration

```bash
# Copiez le fichier exemple
cp src/main/resources/application.properties.example src/main/resources/application.properties

# Modifiez application.properties avec vos valeurs (utilisateur/mot de passe MySQL...)
```

### Démarrage

```bash
mvn spring-boot:run
```

L'API sera disponible sur : **http://localhost:9000**

## Frontend

Le frontend Angular est disponible ici :
[Yummy-restaurant---Frontend](https://github.com/Zaineb-Hl/Yummy-restaurant---Frontend)

