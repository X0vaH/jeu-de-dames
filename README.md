# ♟️ Jeu de dames — Application Android (Java)

Application **Android** de **jeu de dames internationales** (plateau 10×10) pour deux joueurs sur le même appareil, écrite en **Java**. Le dépôt contient aussi le **moteur de jeu en version console**, qui a servi de base à l'application.

Les cases sont identifiées selon la **notation Manoury** (1 à 50), la notation officielle du jeu de dames.

> Travaux pratiques réalisés dans le cours de développement mobile au cégep (automne 2024) : TP1 = moteur de jeu, TP2 = application Android.

---

## ✨ Fonctionnalités

### Application Android

- **Menu d'accueil** : saisie du nom des deux joueurs (validation des champs vides).
- **Damier 10×10 interactif** : chaque case est un bouton ; `b`/`n` = pions blancs/noirs, `B`/`N` = dames.
- Toucher une pièce **surligne en vert les cases accessibles** et affiche la liste des mouvements possibles.
- Affichage du **nom du joueur dont c'est le tour**.
- Bouton **« Annuler coup »**.
- **Fin de partie** : annonce du gagnant puis retour au menu.
- Mise en page dédiée en mode **portrait et paysage**.
- État de la partie partagé via un **singleton** (`SingletonDames`).

### Moteur de jeu (commun aux deux versions)

- Conversion **notation Manoury ↔ coordonnées** (ligne, colonne).
- **Déplacements** des pions (diagonale avant) et des **dames** (diagonales sur toute la longueur).
- **Prises**, y compris les **prises multiples** (rafles), vers l'avant et l'arrière.
- **Promotion** automatique d'un pion en dame sur la dernière rangée.
- Alternance des tours et validation des coups.
- **Historique** (`Stack<Move>`) et **annulation** du dernier coup.
- Détection de **fin de partie** (un joueur ne peut plus bouger).

## 🧱 Conception

```
Piece (abstraite)       getMouvementsValides(), getPrisesValides(), getRepresentation()
├── Pion                se déplace d'une case vers l'avant, prend dans les 2 sens
└── Dame                se déplace sur toute la diagonale

Damier                  état du plateau, règles, tours, historique
Move                    un coup : départ, arrivée, pièces prises
AffichageDamier         rendu texte du damier

MenuActivity            écran de saisie des noms (activité de lancement)
MainActivity            héberge DamesFragment
DamesFragment           damier interactif, tour, mouvements possibles, annulation
SingletonDames          instance unique du damier et des noms des joueurs
```

## 🗂️ Structure du dépôt

```
app/                         Application Android (Gradle)
├── src/main/java/...        Activités, fragment et moteur de jeu
├── src/main/res/            Layouts (portrait + layout-land), thèmes, textes
├── src/test/java/...        Tests unitaires (JUnit 4 / JUnit 5)
└── src/androidTest/java/... Tests instrumentés de l'interface (Espresso)
console/                     Version console du moteur (TP1)
├── src/                     Code source + point d'entrée Jeu.main()
└── Tests/                   Tests unitaires JUnit 4
```

## 🧰 Stack technique

- Java 11, Android SDK 34 (minSdk 24), Gradle 8.9 (Kotlin DSL), Android Gradle Plugin 8.7
- AndroidX AppCompat, Material Components, ConstraintLayout
- JUnit 4, JUnit 5, Espresso
- Android Studio, Checkstyle

---

## 🚀 Application Android

### Prérequis

- [Android Studio](https://developer.android.com/studio) (récent, inclut le JDK 17+ requis par Gradle)
- Android SDK 34 (Android Studio propose de l'installer à l'ouverture du projet)
- Un émulateur Android ou un téléphone Android 7.0+ avec le **débogage USB** activé

### 1. Cloner le dépôt

```bash
git clone https://github.com/X0vaH/jeu-de-dames.git
```

### 2. Ouvrir et lancer dans Android Studio

1. **File → Open** et choisir le dossier `jeu-de-dames`.
2. Attendre la fin de la synchronisation Gradle (installer le SDK 34 si demandé).
3. Créer un émulateur au besoin : **Device Manager → Create Device** (ex. Pixel, API 34).
4. Cliquer sur **Run ▶** (configuration `app`).

### 3. En ligne de commande (optionnel)

Avec un JDK 17+ et la variable `ANDROID_HOME` (ou un fichier `local.properties` contenant `sdk.dir=...`) :

```bash
./gradlew assembleDebug          # génère app/build/outputs/apk/debug/app-debug.apk
./gradlew installDebug           # installe sur l'appareil / l'émulateur connecté
./gradlew testDebugUnitTest      # tests unitaires
./gradlew connectedAndroidTest   # tests Espresso (appareil ou émulateur requis)
```

Sur Windows, utiliser `gradlew.bat` au lieu de `./gradlew`.

### 4. Comment jouer

1. Entrer le nom des deux joueurs, puis **Commencer le jeu**. Le joueur 1 joue les Blancs, qui commencent.
2. Toucher une de vos pièces : les cases accessibles deviennent **vertes**.
3. Toucher une case verte pour jouer le coup (les prises retirent automatiquement les pièces adverses).
4. **Annuler coup** revient au coup précédent.
5. Quand un joueur ne peut plus bouger, le gagnant est annoncé et l'application revient au menu.

---

## 💻 Version console (`console/`)

Moteur de jeu jouable dans un terminal, avec JDK 11+ seulement.

```bash
cd console
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out cstjean.mobile.dames.Jeu
```

Windows (PowerShell) :

```powershell
cd console
javac -encoding UTF-8 -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
java -cp out cstjean.mobile.dames.Jeu
```

| Saisie | Action |
| --- | --- |
| `32-28` | déplace la pièce de la case 32 vers la case 28 |
| `28x19` | prise (de 28 vers 19) — pour une rafle, indiquer la case d'arrivée finale |
| `u` | annule le dernier coup |
| `q` | quitte la partie |

Affichage : `p`/`P` = pions blancs/noirs, `d`/`D` = dames, `x` = case vide, `-` = case non jouable.

Tests de la version console :

```bash
cd console
mkdir -p lib
curl -L -o lib/junit-4.13.2.jar https://repo1.maven.org/maven2/junit/junit/4.13.2/junit-4.13.2.jar
curl -L -o lib/hamcrest-core-1.3.jar https://repo1.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar
javac -encoding UTF-8 -d out -cp "lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar" $(find src Tests -name "*.java")
java -cp "out:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore cstjean.mobile.dames.TestComplet
```

(Sur Windows, remplacer les `:` du classpath par des `;`.) Résultat attendu : `OK (21 tests)`.

---

## 🧪 Tests

| Classe | Ce qui est testé |
| --- | --- |
| `TestPion`, `TestDame` | création, couleur, représentation et mouvements des pièces |
| `TestDamier` | initialisation, déplacements (pion, dame, coups invalides, hors limites, pièce adverse), prises, promotion, annulation, fin de partie |
| `TestSingletonDames` | instance unique, noms des joueurs, réinitialisation en fin de partie |
| `TestMenuActivity` (Espresso) | saisie des noms et lancement de la partie |

## ⚠️ Limitations connues

- La prise obligatoire (et la règle de la prise majoritaire) n'est pas imposée.
- Lors d'une annulation, une dame capturée est restaurée sous forme de pion.
- Partie à deux joueurs sur le même appareil uniquement (pas d'IA ni de jeu en ligne).

## 👤 Auteur

**Alex Héroux** ([@X0vaH](https://github.com/X0vaH))
