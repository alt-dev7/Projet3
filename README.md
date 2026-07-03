# Estate — ChaTop

Plateforme de location ("ChaTop") : front-end Angular 20 + back-end API REST Spring Boot.

## Front-end

Le front-end est basé sur Angular 20 et nécessite Node 22.

Installer les dépendances :

> npm install

Lancer le front-end :

> ng serve

Le front-end tourne sur `http://localhost:4200` et proxy automatiquement toutes les requêtes `/api/**` vers le back-end sur `http://localhost:3001` (voir `src/proxy.config.json`). Le back-end doit donc être lancé (voir ci-dessous) pour que l'application fonctionne.

## Back-end

### Prérequis

- Java 17
- Maven
- Un serveur MySQL accessible

### 1. Créer la base de données

Créer une base et un utilisateur MySQL (adapter les noms/mots de passe selon votre installation — ils devront correspondre au fichier `.env` de l'étape suivante) :

```mariadb
CREATE DATABASE chatop_db;
CREATE USER 'chatop_user'@'localhost' IDENTIFIED BY 'changeme';
GRANT ALL PRIVILEGES ON chatop_db.* TO 'chatop_user'@'localhost';
FLUSH PRIVILEGES;
```

Puis exécuter le script `ressources/sql/script.sql` sur cette base pour créer les tables `USERS`, `RENTALS` et `MESSAGES` :

```
mysql -u chatop_user -p chatop_db < ressources/sql/script.sql
```

### 2. Configurer les variables d'environnement

Copier `.env.exemple` vers `.env` à la racine du projet et renseigner vos valeurs :

```
cp .env.exemple .env
```

- `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME`, `DB_PASSWORD` : accès à la base créée à l'étape précédente.
- `JWT_SECRET` : une valeur longue et aléatoire (jamais commitée) utilisée pour signer les tokens.
- `JWT_EXPIRATION_MS` : durée de validité du token en millisecondes (24h par défaut).

Copier ensuite `application.properties.exemple` vers `application.properties`, toujours à la racine du projet (c'est l'emplacement où Spring Boot le charge automatiquement au démarrage) :

```
cp application.properties.exemple application.properties
```

### 3. Lancer le back-end

```
mvn spring-boot:run
```

L'API démarre sur `http://localhost:3001`, préfixée par `/api` (ex : `http://localhost:3001/api/rentals`). Les fichiers uploadés (photos des locations) sont stockés dans le dossier `uploads/` (créé automatiquement) et servis sur `http://localhost:3001/api/images/...`.

### 4. Documentation de l'API (Swagger)

Une fois le back-end lancé :

- Interface interactive : http://localhost:3001/api/swagger-ui.html
- Spécification OpenAPI brute : http://localhost:3001/api/v3/api-docs

Toutes les routes sont documentées et testables directement depuis Swagger UI. Pour tester une route sécurisée : récupérer un token via `POST /auth/login`, puis cliquer sur le bouton **Authorize** en haut de la page et coller le token (sans le préfixe `Bearer`).

## Ressources

### Mockoon

Mockoon a été utilisé pour prototyper l'API avant son implémentation. Télécharger Mockoon ici : https://mockoon.com/download/

Charger l'environnement fourni :

> ressources/mockoon/rental-oc.json

directement dans Mockoon via `File > Open environment`, puis lancer le serveur avec le bouton play.

Documentation Mockoon : https://mockoon.com/docs/latest/about/

### Base de données

Le script SQL de création du schéma est disponible dans `ressources/sql/script.sql` (voir la section [Back-end](#back-end) pour la procédure complète d'installation).
