# inventaire-legacy

Petite application Java de gestion d'inventaire :

- `Panier` calcule le total TTC d'un panier à partir de prix HT ;
- `Stock` suit les entrées et sorties de stock d'un article, et refuse une sortie supérieure au stock disponible.

## Installation et lancement

Prérequis : un JDK 17 ou plus récent (`javac -version`). Le projet n'utilise ni Maven ni Gradle.

```bash
git clone https://github.com/<compte>/inventaire-legacy.git
cd inventaire-legacy
javac -d out src/*.java
java -cp out Panier
```

Les fichiers compilés vont dans `out/`, qui est ignoré par Git.

### Configuration

L'accès à l'API de paiement se configure par variables d'environnement :

```bash
cp .env.example .env
```

Renseignez ensuite les valeurs dans `.env`. Ce fichier est ignoré par Git : ne le committez jamais.

## Structure

```
src/            sources Java
.env.example    variables d'environnement attendues (sans valeurs)
.depot          identifiant de génération du dépôt (ne pas modifier)
AUDIT.md        état des lieux du dépôt à la reprise
```

## Contribuer

Les conventions (nommage des branches, messages de commit, Pull Requests) sont décrites dans [CONTRIBUTING.md](CONTRIBUTING.md). Toute modification de `main` passe par une Pull Request relue.

## Licence

MIT, voir [LICENSE](LICENSE).
