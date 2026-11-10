# Journal — TP 06

## 2026-11-10 — J1 : mise en place

- **~15:26** — Dépôt `inventaire-legacy` créé avec `bash creer-depot.sh` (dans `travail/`, dossier vide).
  - Warnings `LF will be replaced by CRLF` à la création : liés à `core.autocrlf=true` sous Windows, sans conséquence sur le contenu.
- **15:50** — Hash du premier commit (`git rev-list --max-parents=0 HEAD`) :
  `d72a035f52b2524b0cbb28c671c3bf80b880eabc`
- État initial (`git log --oneline --all --graph`) : 6 commits sur `main`, plus 3 branches partant de `final VRAI` : `test-marc`, `nouvelle-tva`, `arrondi`.

### Observations / prédictions

<!-- À compléter toi-même : ce que tu remarques pendant l'audit, ce que tu prévois. -->

## 2026-11-10 — J1 : audit et nettoyage

- **16:05** — Audit avec `git log --all --stat`, `git shortlog -sne --all` et `git diff main...<branche>`. Résultat dans `AUDIT.md` (12 défauts).
- **16:10** — Installation de `git-filter-repo` (`pip install --user git-filter-repo`). Le script `git-filter-repo.exe` est dans `%APPDATA%\Python\Python314\Scripts`, qui n'est pas dans le PATH : il a fallu l'ajouter.
- **16:13** — Retrait de `.env` de tout l'historique :
  - avant : `git log --all --oneline -- .env` → `1442873 fix`
  - premier essai refusé (« does not look like a fresh clone »), relancé avec `--force` : sans danger puisque le dépôt n'est publié nulle part ;
  - après : `git log --all --oneline -- .env` → rien.
  - Le commit `fix` a disparu (il ne contenait que `.env`). Tous les commits suivants ont changé de hash (`final VRAI` : `d71d370` → `67f24ec`). Le commit racine reste `d72a035`, ainsi que `modifs` (`4a47d5a`).
- **16:20** — Ajout de `.gitignore`, `.gitattributes`, `.env.example`, `README.md`, `CONTRIBUTING.md`, `LICENSE` (MIT), commités directement sur `main` (dépôt pas encore partagé).
- `target/Panier.class`, `src/Panier.java.bak` et `notes.txt` sont encore suivis : ce sont des chantiers du J2, à traiter par issue + PR.

