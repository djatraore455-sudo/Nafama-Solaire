# Nafama Solaire — Prototype Android (Kotlin + Jetpack Compose)

Ce dossier contient le CODE SOURCE de l'application. Il n'est **pas** un
projet Android Studio complet et autonome (pas de dossier `gradle/`, pas
de `settings.gradle.kts`) : vous allez le greffer sur un projet vide créé
par l'assistant de création de projets d'Android Studio, ce qui garantit
que toute la configuration Gradle (wrapper, versions, SDK) est correcte
dès le départ.

## Étape 1 — Créer le projet vide

1. Ouvrez Android Studio → **New Project**
2. Choisissez le modèle **Empty Activity** (dans la section *Compose*)
3. Renseignez :
   - Name : `Nafama Solaire`
   - Package name : `com.nafamasolaire.app`  ⚠️ important, doit être identique
   - Minimum SDK : API 26 ou plus
4. Cliquez sur **Finish** et laissez Android Studio synchroniser le projet
   (ça peut prendre quelques minutes la première fois).

## Étape 2 — Remplacer les fichiers par ceux fournis

Dans le projet créé par Android Studio, remplacez/complétez :

- `app/build.gradle.kts` → remplacez-le entièrement par le fichier fourni ici
- `app/src/main/AndroidManifest.xml` → remplacez-le par celui fourni
- `app/src/main/java/com/nafamasolaire/app/MainActivity.kt` → remplacez-le
- `app/src/main/res/values/themes.xml` et `strings.xml` → remplacez-les

Puis copiez l'intégralité des dossiers suivants dans
`app/src/main/java/com/nafamasolaire/app/` :

- `ui/theme/` (Color.kt, Theme.kt, Type.kt)
- `ui/navigation/` (NafamaNavigation.kt)
- `ui/screens/` (les 9 écrans)
- `data/` (Modeles.kt, SimulateurViewModel.kt, MarcheViewModel.kt)

Et copiez ces dossiers dans `app/src/main/res/` (remplacez les dossiers
`mipmap-*` déjà créés par Android Studio — c'est votre logo Nafama
Solaire, déjà préparé à toutes les tailles) :

- `mipmap-mdpi/`, `mipmap-hdpi/`, `mipmap-xhdpi/`, `mipmap-xxhdpi/`,
  `mipmap-xxxhdpi/` (chacun contient `ic_launcher.png` et
  `ic_launcher_round.png`)

## Étape 3 — Synchroniser et lancer

1. Cliquez sur **Sync Now** si Android Studio le propose
2. Branchez un téléphone Android (mode développeur + débogage USB activé)
   ou lancez un émulateur
3. Cliquez sur **Run ▶**

## Étape 4 — Générer le fichier APK pour la soutenance

Menu **Build → Build App Bundle(s) / APK(s) → Build APK(s)**
Le fichier `.apk` est généré dans `app/build/outputs/apk/debug/`.

## Fonctionnalités déjà codées

- Icône de l'application = votre logo Nafama Solaire
- Inscription (numéro de téléphone, profil Productrice/Acheteur)
- Accueil avec indicateurs en direct (batterie, ensoleillement, économies)
- Pilotage solaire : simulateur de données évolutives + marche/arrêt pompe
- Suivi énergie : courbe production/consommation, conseils, économies
- Marché : liste d'annonces, publication avec sélection de photo réelle
  (Photo Picker natif Android, sans permission ni dépendance)
- Paiement : sélection Orange Money / MTN / Wave (simulation de la
  transaction — voir note ci-dessous)
- Carte des installations (représentation stylisée)
- Aide : assistant vocal (reconnaissance + synthèse vocale en français) et
  appel direct vers un technicien

## Limites actuelles à annoncer au jury comme feuille de route

- **Paiement réel** : nécessite un contrat marchand avec Orange Money /
  MTN MoMo / Wave (API dédiée par opérateur) — hors périmètre d'un
  prototype étudiant, mais l'écran et le parcours sont prêts.
- **Carte réelle géolocalisée** : nécessite une clé Google Maps (compte
  Google Cloud avec facturation) — remplaçable facilement par
  `com.google.maps.android:maps-compose` quand la clé sera disponible.
- **Assistant vocal en dioula/senoufo** : les moteurs vocaux natifs
  d'Android ne couvrent que le français ici. Un vrai support des langues
  locales demande un moteur vocal spécialisé.
- **Capteurs IoT réels** : le module Pilotage utilise un simulateur ;
  brancher de vrais capteurs demande un module de communication
  (ex. ESP32 en Wi-Fi/MQTT).
