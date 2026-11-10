# Audit du dépôt inventaire-legacy

Audit réalisé le 10/11/2026, avant toute modification, à partir de :

- `git log --all --stat`
- `git shortlog -sne --all`
- `git diff main...<branche>` pour chaque branche
- `ls -la` à la racine

## État initial

- 6 commits sur `main` (`init` → `final VRAI`).
- 3 branches partent de `final VRAI` : `test-marc`, `nouvelle-tva`, `arrondi`.
- 3 identités d'auteur pour 2 personnes.
- Aucun tag, aucune release.

## Défauts relevés

> Les hashes cités dans ce tableau sont ceux d'**avant** la réécriture de l'historique (voir défaut 1). Après `git filter-repo`, le commit `1442873` (« fix ») a disparu et les commits suivants ont changé de hash. Seuls `d72a035` (init) et `4a47d5a` (modifs) sont restés identiques.

| # | Où | Pourquoi c'est un problème | Ce qu'on fait |
|---|----|----------------------------|---------------|
| 1 | `.env`, ajouté par le commit `1442873` (« fix ») | Il contient `PAIEMENT_API_KEY`, une clé d'API. Une fois le dépôt public, n'importe qui peut la lire, même si on supprime le fichier dans un commit suivant : elle reste dans l'historique. | On retire `.env` de **tout** l'historique avec `git filter-repo` avant de publier. On ajoute `.env` au `.gitignore` et on versionne un `.env.example` sans valeurs. |
| 2 | `target/Panier.class`, commit `3d45e6b` (« fix 2 ») | C'est un fichier compilé. Il se régénère depuis les sources, il ne reflète pas forcément le code actuel et il pollue les diffs. | On ajoute `target/` et `*.class` au `.gitignore` et on retire le dossier du suivi (`git rm -r --cached`) dans une PR. |
| 3 | `src/Panier.java.bak`, commit `3d45e6b` | Copie de sauvegarde de l'éditeur. Git garde déjà l'historique, le `.bak` fait doublon et risque d'être modifié par erreur à la place du vrai fichier. | On ajoute `*.bak` au `.gitignore` et on supprime le fichier dans une PR. |
| 4 | `notes.txt`, commit `d71d370` | C'est une liste de tâches (« README, tests, version 1.0 ») qui n'a rien à faire dans le code. Elle ne sera jamais à jour. | On transforme son contenu en issues GitHub, puis on supprime le fichier. |
| 5 | Messages de commit : `modifs`, `fix`, `fix 2`, `final`, `final VRAI`, `wip`, `taux`, `ça marche ???` | Ils ne disent ni ce qui change ni pourquoi. Impossible de retrouver un changement ou de comprendre l'historique sans ouvrir chaque diff. | On ne réécrit pas les anciens messages. On fixe un format dans `CONTRIBUTING.md` (Conventional Commits) et on l'applique à partir de maintenant. |
| 6 | Auteurs : `Marc Durand <marc.durand@perso.example>` et `m.durand <m.durand@ecole.example>` | La même personne apparaît sous deux identités, donc les statistiques (`git shortlog`, blame) sont faussées. | On ajoute un `.mailmap` qui regroupe les deux identités sous un seul nom. |
| 7 | Branche `test-marc` (commit `d694627`, « wip ») | Elle n'ajoute qu'une ligne `// essai` à la fin de `Stock.java`. C'est un essai abandonné : rien à récupérer. | On vérifie avec `git log main..test-marc`, puis on supprime la branche en local et sur GitHub. |
| 8 | Branches `nouvelle-tva` et `arrondi` | Elles modifient toutes les deux la même ligne de `Panier.totalTtc()` : l'une passe le taux de 1.20 à 1.055, l'autre ajoute un arrondi au centime. Les fusionner va créer un conflit. | On fusionne chacune par PR (J3), après avoir demandé au commanditaire quel taux est le bon. |
| 9 | Racine du dépôt | Pas de `README.md`, `CONTRIBUTING.md`, `LICENSE` ni `.gitignore`. Un nouveau venu ne sait pas à quoi sert le projet, comment le compiler, ni s'il a le droit de le réutiliser. | On ajoute ces quatre fichiers (J1). |
| 10 | Versions | Aucun tag, aucun changelog : on ne sait pas quelle version est livrée. | On rédige `CHANGELOG.md` et on publie la release `v1.0.0` sur un tag annoté (J4). |
| 11 | `.depot` | Fichier à la racine sans explication (`build <id>`). | On le garde (il identifie le dépôt) et on le documente dans le README. |
| 12 | Fins de ligne | À la création, Git affiche des warnings `LF will be replaced by CRLF`. Selon l'OS de chacun, les fins de ligne changent et des diffs inutiles peuvent apparaître. | On ajoute un `.gitattributes` (`* text=auto eol=lf`). |

## Remarques sur le code (hors périmètre 1.0.0, à discuter)

- `Panier` utilise des `double` pour des montants : les erreurs d'arrondi sont possibles, ce qui explique sans doute la branche `arrondi`. `BigDecimal` serait plus sûr.
- `Stock.entrer()` et `Stock.sortir()` acceptent des quantités négatives.
- Il n'y a aucun test.

## Si le dépôt avait déjà été public

Retirer `.env` de l'historique n'aurait **pas** suffi : la clé aurait pu être copiée entre-temps (clones, forks, caches, robots qui scannent GitHub à la recherche de secrets). Il aurait fallu en plus :

1. **Révoquer la clé tout de suite** auprès du fournisseur de paiement et en générer une nouvelle. C'est la seule mesure qui rend la fuite inoffensive.
2. Vérifier dans les journaux du fournisseur si la clé a été utilisée entre la fuite et la révocation.
3. Réécrire l'historique quand même, puis forcer le push (`git push --force --all`) et prévenir tous ceux qui ont un clone de le refaire.
4. Demander au support GitHub de purger les vues en cache et les références des Pull Requests, qui gardent encore les anciens commits.
5. Activer le *secret scanning* et la *push protection* de GitHub pour bloquer les prochains secrets.
