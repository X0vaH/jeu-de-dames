# ♟️ Jeu de dames — Moteur Java (dames internationales 10×10)

Moteur de **jeu de dames internationales** (plateau 10×10, 50 cases jouables) écrit en **Java**, avec une interface console pour jouer à deux et une suite de **tests unitaires JUnit**.

Les cases sont identifiées selon la **notation Manoury** (1 à 50), la notation officielle du jeu de dames.

> Travail pratique réalisé dans le cours de développement mobile au cégep (automne 2024).

---

## ✨ Fonctionnalités

- Plateau 10×10 avec conversion **notation Manoury ↔ coordonnées** (ligne, colonne).
- Placement initial des 40 pions (20 noirs, 20 blancs) ; les Blancs commencent.
- **Déplacements** des pions (diagonale avant) et des **dames** (diagonales sur toute la longueur).
- **Prises**, y compris les **prises multiples** (rafles), vers l'avant et l'arrière.
- **Promotion** automatique d'un pion en dame sur la dernière rangée.
- Alternance des tours et validation : impossible de jouer une pièce adverse ou un coup illégal.
- **Historique** des coups et **annulation** du dernier coup.
- Détection de **fin de partie** (un joueur ne peut plus bouger).
- Affichage textuel du damier en console.

## 🧱 Conception

Programmation orientée objet avec héritage et polymorphisme :

```
Piece (abstraite)            getMouvementsValides(), getPrisesValides(), getRepresentation()
├── Pion                     se déplace d'une case vers l'avant, prend dans les 2 sens
└── Dame                     se déplace sur toute la diagonale

Damier                       état du plateau, règles, tours, historique (Stack<Move>)
Move                         un coup : départ, arrivée, pièces prises
AffichageDamier              rendu texte du damier (responsabilité unique)
Jeu                          point d'entrée console (main)
```

```
src/cstjean/mobile/dames/      Code source
Tests/cstjean/mobile/dames/    Tests unitaires JUnit 4
```

## 🧰 Stack technique

- Java 11+ (testé avec Java 16)
- JUnit 4.13.2
- IntelliJ IDEA, Checkstyle

---

## 🚀 Installation et lancement

### Prérequis

- [JDK 11 ou plus récent](https://adoptium.net/) (`java -version` pour vérifier)
- [Git](https://git-scm.com/)

### 1. Cloner le dépôt

```bash
git clone https://github.com/X0vaH/jeu-de-dames.git
cd jeu-de-dames
```

### 2. Compiler et jouer en console

**Linux / macOS / Git Bash :**

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out cstjean.mobile.dames.Jeu
```

**Windows (PowerShell) :**

```powershell
javac -encoding UTF-8 -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
java -cp out cstjean.mobile.dames.Jeu
```

### 3. Comment jouer

```
-P-P-P-P-P      P = pion noir     p = pion blanc
P-P-P-P-P-      D = dame noire    d = dame blanche
-P-P-P-P-P      x = case vide     - = case non jouable
P-P-P-P-P-
-x-x-x-x-x      Cases numérotées de 1 à 50 (notation Manoury),
x-x-x-x-x-      de gauche à droite et de haut en bas,
-p-p-p-p-p      en ne comptant que les cases jouables.
p-p-p-p-p-
-p-p-p-p-p
p-p-p-p-p-
```

| Saisie | Action |
| --- | --- |
| `32-28` | déplace la pièce de la case 32 vers la case 28 |
| `28x19` | prise (de 28 vers 19) — pour une rafle, indiquer la case d'arrivée finale |
| `u` | annule le dernier coup |
| `q` | quitte la partie |

### 4. Lancer les tests

Télécharger JUnit et Hamcrest :

```bash
mkdir -p lib
curl -L -o lib/junit-4.13.2.jar https://repo1.maven.org/maven2/junit/junit/4.13.2/junit-4.13.2.jar
curl -L -o lib/hamcrest-core-1.3.jar https://repo1.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar
```

Compiler et exécuter la suite complète (sur Windows, remplacer les `:` du classpath par des `;`) :

```bash
javac -encoding UTF-8 -d out -cp "lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar" $(find src Tests -name "*.java")
java -cp "out:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore cstjean.mobile.dames.TestComplet
```

Résultat attendu : `OK (21 tests)`.

### Avec IntelliJ IDEA

1. **File → Open** et choisir le dossier du projet.
2. Clic droit sur `src` → **Mark Directory as → Sources Root**, et sur `Tests` → **Test Sources Root**.
3. Ajouter JUnit 4 : **File → Project Structure → Libraries → + → From Maven** → `junit:junit:4.13.2`.
4. Exécuter `Jeu.main()` pour jouer, ou `TestComplet` pour lancer les tests.

---

## 🧪 Tests

| Classe | Ce qui est testé |
| --- | --- |
| `TestPion` | création, couleur et représentation des pions |
| `TestDame` | création et représentation des dames |
| `TestDamier` | initialisation, déplacements (pion, dame, coups invalides, hors limites, pièce adverse), prises (pion et dame), promotion, annulation, fin de partie |
| `TestComplet` | suite qui regroupe tous les tests |

## ⚠️ Limitations connues

- La prise obligatoire (et la règle de la prise majoritaire) n'est pas imposée.
- Lors d'une annulation, une dame capturée est restaurée sous forme de pion.

## 👤 Auteur

**Alex Héroux** ([@X0vaH](https://github.com/X0vaH))
