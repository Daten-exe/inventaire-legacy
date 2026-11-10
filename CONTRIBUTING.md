# Contribuer à inventaire-legacy

## Le principe

1. Chaque modification part d'une **issue**.
2. On travaille sur une **branche** créée depuis `main` à jour.
3. On ouvre une **Pull Request** qui ferme l'issue.
4. L'autre membre du binôme la **relit** et l'approuve.
5. On fusionne.

Personne ne pousse directement sur `main` : la branche est protégée.

## Nommage des branches

Format : `<type>/<numéro-issue>-<description-courte>`, en minuscules, avec des tirets.

| Type | Usage | Exemple |
|------|-------|---------|
| `feat` | nouvelle fonctionnalité | `feat/12-export-csv` |
| `fix` | correction de bug | `fix/7-tva-panier` |
| `docs` | documentation uniquement | `docs/3-changelog` |
| `chore` | ménage, configuration, outillage | `chore/5-retirer-target` |

## Messages de commit

On suit [Conventional Commits](https://www.conventionalcommits.org/fr/) :

```
<type>(<portée optionnelle>): <résumé à l'impératif, 72 caractères max>

<corps optionnel : pourquoi ce changement, pas comment>

Refs #<numéro-issue>
```

- Types : `feat`, `fix`, `docs`, `chore`, `refactor`, `test`.
- Le résumé dit **ce que fait** le commit : « retire target/ du suivi », pas « fix » ni « modifs ».
- Un commit = un changement cohérent. On ne mélange pas un correctif et du ménage.

Exemples :

```
fix(panier): arrondit le total TTC au centime
chore: retire Panier.java.bak du dépôt
docs: ajoute le CHANGELOG de la 1.0.0
```

## Pull Requests

- Le titre suit le même format qu'un message de commit.
- La description contient :
  - **Quoi** : ce qui change ;
  - **Pourquoi** : le besoin ou le problème ;
  - **Comment tester** : les commandes à lancer (au minimum `javac src/*.java`) ;
  - `Closes #<numéro>` pour fermer l'issue automatiquement à la fusion.
- Une PR = une issue. Si la PR grossit, on la découpe.
- **Une approbation** de l'autre membre du binôme est obligatoire avant la fusion.
- Le code doit compiler sur la branche avant de demander une revue.

## Revue

- Le relecteur commente **une ligne précise du diff** quand il signale quelque chose (clic sur la ligne, puis commentaire).
- Il vérifie : la compilation, le respect de ces conventions, l'absence de fichiers parasites (`.class`, `.bak`, `.env`).
- L'auteur répond à chaque commentaire (correction ou explication) avant de fusionner.

## Après la fusion

- On supprime la branche (bouton « Delete branch » sur GitHub, puis `git branch -d` en local).
- On déplace la carte de l'issue dans la colonne « Fait » du board.

## Secrets

Aucun secret dans le dépôt. Les valeurs vont dans `.env` (ignoré par Git) et on documente les nouvelles variables dans `.env.example`, sans valeur.
