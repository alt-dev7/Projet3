# Estate — ChaTop

Plateforme de location ("ChaTop") : front-end Angular 20 + back-end API REST Spring Boot.

> Les commandes d'installation/lancement et les scripts SQL ne sont volontairement pas recopiés dans ce README : ils vivent dans `scripts/` et `ressources/sql/`, et le README se contente d'y renvoyer. Ça évite qu'une commande documentée diverge silencieusement de celle réellement exécutée, et qu'un mot de passe ou une valeur d'exemple codée en dur traîne dans la documentation.

## Front-end

Le front-end est basé sur Angular 20 et nécessite Node 22.

Installer les dépendances via `scripts/install-frontend.sh`.

Lancer le front-end via `scripts/start-frontend.sh`.

Le front-end tourne sur `http://localhost:4200` et proxy automatiquement toutes les requêtes `/api/**` vers le back-end sur `http://localhost:3001` (voir `src/proxy.config.json`). Le back-end doit donc être lancé (voir ci-dessous) pour que l'application fonctionne.

## Back-end

### Prérequis

- Java 17
- Maven
- Un serveur MySQL accessible

### 1. Créer la base de données

Copier `ressources/sql/create_db_user.sql.exemple` vers `ressources/sql/create_db_user.sql`, adapter les noms/mots de passe selon votre installation (ils devront correspondre au fichier `.env` de l'étape suivante), puis exécuter ce script sur votre serveur MySQL.

> Ce mot de passe est celui de l'utilisateur MySQL (accès à la base), pas celui des utilisateurs de l'application. Les mots de passe des comptes créés via `/api/auth/register` sont hachés (BCrypt) dès l'inscription, avant d'être stockés en base — voir `AuthService.register()`.

Exécuter ensuite `scripts/import-schema.sh` pour créer les tables `USERS`, `RENTALS` et `MESSAGES` à partir de `ressources/sql/script.sql`.

### 2. Configurer les variables d'environnement

Exécuter `scripts/setup-env.sh` pour copier `.env.exemple` vers `.env` et `application.properties.exemple` vers `application.properties`, tous deux à la racine du projet (c'est l'emplacement où Spring Boot les charge automatiquement au démarrage). Renseigner ensuite vos valeurs dans `.env` :

- `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME`, `DB_PASSWORD` : accès à la base créée à l'étape précédente.
- `JWT_SECRET` : une valeur longue et aléatoire (jamais commitée) utilisée pour signer les tokens.
- `JWT_EXPIRATION_MS` : durée de validité du token en millisecondes (24h par défaut).

### 3. Lancer le back-end

Lancer via `scripts/start-backend.sh`.

L'API démarre sur `http://localhost:3001`, préfixée par `/api` (ex : `http://localhost:3001/api/rentals`). Les fichiers uploadés (photos des locations) sont stockés dans le dossier `uploads/` (créé automatiquement) et servis sur `http://localhost:3001/api/images/...`.

### 4. Documentation de l'API (Swagger)

Une fois le back-end lancé :

- Interface interactive : http://localhost:3001/api/swagger-ui.html
- Spécification OpenAPI brute : http://localhost:3001/api/v3/api-docs

Toutes les routes sont documentées et testables directement depuis Swagger UI. Pour tester une route sécurisée : récupérer un token via `POST /auth/login`, puis cliquer sur le bouton **Authorize** en haut de la page et coller le token (sans le préfixe `Bearer`).

## Ressources

### Mockoon

Mockoon a été utilisé pour prototyper l'API avant son implémentation. Télécharger Mockoon ici : https://mockoon.com/download/

Charger l'environnement fourni, `ressources/mockoon/rental-oc.json`, directement dans Mockoon via `File > Open environment`, puis lancer le serveur avec le bouton play.

Documentation Mockoon : https://mockoon.com/docs/latest/about/

### Base de données

Le script SQL de création du schéma est disponible dans `ressources/sql/script.sql` (voir la section [Back-end](#back-end) pour la procédure complète d'installation).
