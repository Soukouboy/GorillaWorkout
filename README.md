# Gorilla Workout - Application Mobile Fitness

## 📋 Description

Gorilla Workout est une application mobile Android de suivi et de gestion d'entraînements fitness. Elle permet aux utilisateurs de créer des programmes d'exercices personnalisés, suivre leurs séances en temps réel avec un système de timer intelligent, et visualiser leur progression au fil du temps.

## ✨ Fonctionnalités Principales

### 🏠 Accueil (Home)
- **Carte de bienvenue** avec message de motivation personnalisé
- **Statistiques hebdomadaires** (séances, temps, calories, série de jours consécutifs)
- **Dernier entraînement** avec accès rapide aux détails
- **Bouton d'action principal** pour démarrer un entraînement

### 💪 Exercices
- **Bibliothèque d'exercices** avec 20+ exercices prédéfinis
- **Recherche en temps réel** par nom d'exercice
- **Filtres par catégorie** (Musculation, Cardio, Flexibilité, Équilibre, Sports)
- **Gestion des favoris** avec icônes interactives
- **Création d'exercices personnalisés**
- **Détails complets** pour chaque exercice

### 🏋️ Entraînement
- **Création d'entraînements** avec exercices personnalisés
- **Configuration détaillée** (séries, répétitions, poids, repos)
- **Timer de repos automatique** avec notification
- **Suivi en temps réel** de la progression
- **Chronomètre global** de l'entraînement
- **Notes pendant l'entraînement**

### 📊 Statistiques
- **Vue d'ensemble** avec statistiques globales
- **Graphique d'activité hebdomadaire** (Bar Chart)
- **Répartition par catégorie** (Pie Chart)
- **Historique récent** des 10 derniers entraînements
- **Records personnels** (plus longue séance, plus de calories, etc.)

### 👤 Profil
- **Informations personnelles** (nom, âge, poids, taille, genre)
- **Objectifs fitness** personnalisables
- **Préférences** (durée de repos, notifications, unités)
- **Gestion des données** (export, réinitialisation)

## 🏗️ Architecture Technique

### Pattern MVVM (Model-View-ViewModel)
```
View (Fragments, Activities)
    ↓
ViewModel (Business Logic)
    ↓
Repository (Data Abstraction)
    ↓
Data Source (Room Database)
```

### Technologies Utilisées

- **Kotlin 2.0.21** - Langage de programmation moderne
- **Android SDK** - Min SDK 24 (Android 7.0), Target SDK 34 (Android 14)
- **Material Design 3** - Interface moderne et intuitive
- **Room Database** - Persistance locale des données
- **LiveData** - Observation réactive des données
- **ViewModel** - Gestion du cycle de vie
- **Navigation Component** - Navigation entre écrans
- **Coroutines** - Programmation asynchrone
- **MPAndroidChart** - Graphiques et statistiques

### Bibliothèques Principales

```kotlin
// Android Core
implementation("androidx.core:core-ktx:1.13.1")
implementation("androidx.appcompat:appcompat:1.7.0")

// Material Design
implementation("com.google.android.material:material:1.12.0")

// Architecture Components
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")

// Room Database
implementation("androidx.room:room-runtime:2.6.1")
implementation("androidx.room:room-ktx:2.6.1")
ksp("androidx.room:room-compiler:2.6.1")

// Coroutines
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

// MPAndroidChart
implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
```

## 🎨 Design System

### Palette de Couleurs
- **Primary**: #FF6B35 (Orange énergique)
- **Secondary**: #4ECDC4 (Turquoise)
- **Accent**: #FFE66D (Jaune)
- **Background**: #F7F7F7
- **Surface**: #FFFFFF

### Typographie
- **Titre Principal**: 28sp, Bold
- **Corps**: 16sp, Regular
- **Caption**: 12sp, Regular

### Composants
- **Cards**: Corner radius 12dp, Elevation 4dp
- **Buttons**: Height 48dp, Corner radius 24dp
- **Chips**: Height 32dp, Horizontal padding 12dp

## 🗄️ Modèle de Données

### Entités Room

#### Exercise (Exercice)
```kotlin
@Entity(tableName = "exercises")
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val category: ExerciseCategory,
    val description: String = "",
    val muscleGroup: String = "",
    val equipment: String = "None",
    val difficulty: DifficultyLevel = BEGINNER,
    val isFavorite: Boolean = false
)
```

#### Workout (Entraînement)
```kotlin
@Entity(tableName = "workouts")
data class Workout(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val date: Long = System.currentTimeMillis(),
    val duration: Int = 0,
    val totalCalories: Int = 0,
    val isCompleted: Boolean = false
)
```

#### UserProfile (Profil Utilisateur)
```kotlin
@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey
    val id: Int = 1,
    val name: String = "",
    val age: Int = 0,
    val weight: Double = 0.0,
    val height: Double = 0.0,
    val fitnessGoal: FitnessGoal = GENERAL_FITNESS,
    val weeklyGoal: Int = 3
)
```

## 🚀 Installation et Configuration

### Prérequis

- Android Studio (dernière version stable)
- JDK 8 ou supérieur
- Android SDK 24+

### Étapes d'Installation

1. **Cloner le projet**
```bash
git clone https://github.com/votre-repo/gorilla-workout.git
cd gorilla-workout
```

2. **Ouvrir dans Android Studio**
   - File → Open → Sélectionner le dossier du projet

3. **Synchroniser le projet**
   - Android Studio synchronisera automatiquement les dépendances Gradle

4. **Lancer l'application**
   - Sélectionner un émulateur ou un appareil physique
   - Cliquer sur Run (Ctrl+R)

## 📱 Fonctionnement Hors Ligne

L'application fonctionne **100% hors ligne** sans nécessiter de connexion internet :

- Base de données locale Room pour toutes les données
- Aucune synchronisation cloud requise
- Fonctionnalités complètes disponibles hors ligne
- Export de données possible pour sauvegarde locale

## 🎯 Public Cible

- **Débutants** en fitness cherchant à structurer leurs entraînements
- **Sportifs intermédiaires** voulant suivre leur progression
- **Toute personne** souhaitant maintenir une routine d'exercice régulière

## 📈 Fonctionnalités Futures (Roadmap)

- [ ] **Cloud Sync** - Synchronisation des données entre appareils
- [ ] **Partage** - Partage de séances d'entraînement
- [ ] **Vidéos d'exercices** - Démonstrations visuelles
- [ ] **Plans d'entraînement prédéfinis** - Programmes structurés
- [ ] **Intégration avec wearables** - Suivi avec montres connectées
- [ ] **Mode sombre** - Thème sombre pour l'application

## 🤝 Contribution

Les contributions sont les bienvenues ! Pour contribuer :

1. Fork le projet
2. Créer une branche feature (`git checkout -b feature/AmazingFeature`)
3. Commit vos changements (`git commit -m 'Add some AmazingFeature'`)
4. Push vers la branche (`git push origin feature/AmazingFeature`)
5. Ouvrir une Pull Request

## 📄 Licence

Ce projet est sous licence MIT. Voir le fichier `LICENSE` pour plus de détails.

## 👨‍💻 Développeur

**Soukouboy** - Développeur Android

- GitHub: [@soukouboy](https://github.com/soukouboy)

## 🙏 Remerciements

- **Material Design Team** pour les composants UI
- **MPAndroidChart** pour les graphiques
- **Android Jetpack** pour les architectures components
- **Kotlin Team** pour le langage moderne

---

**Gorilla Workout** - 💪 Entraînez-vous comme un gorille, progressez comme un champion !