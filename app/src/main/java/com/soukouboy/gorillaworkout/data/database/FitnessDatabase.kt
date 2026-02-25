package com.soukouboy.gorillaworkout.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.soukouboy.gorillaworkout.data.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        Exercise::class,
        Workout::class,
        WorkoutExercise::class,
        UserProfile::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class FitnessDatabase : RoomDatabase() {
    
    abstract fun exerciseDao(): ExerciseDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun userProfileDao(): UserProfileDao
    
    companion object {
        @Volatile
        private var INSTANCE: FitnessDatabase? = null
        
        fun getDatabase(context: Context): FitnessDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FitnessDatabase::class.java,
                    "fitness_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(DatabaseCallback(context))
                    .build()
                INSTANCE = instance
                instance
            }
        }
        
        private class DatabaseCallback(
            private val context: Context
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        populateDatabase(database)
                    }
                }
            }
        }
        
        private suspend fun populateDatabase(database: FitnessDatabase) {
            val exerciseDao = database.exerciseDao()
            val userProfileDao = database.userProfileDao()
            
            // Insérer les exercices par défaut
            val defaultExercises = getDefaultExercises()
            defaultExercises.forEach { exercise ->
                exerciseDao.insertExercise(exercise)
            }
            
            // Créer un profil utilisateur par défaut
            val defaultProfile = UserProfile(
                name = "Utilisateur",
                age = 25,
                weight = 70.0,
                height = 175.0,
                gender = Gender.OTHER,
                fitnessGoal = FitnessGoal.GENERAL_FITNESS,
                experienceLevel = DifficultyLevel.BEGINNER,
                weeklyGoal = 3
            )
            userProfileDao.insertUserProfile(defaultProfile)
        }
        
        private fun getDefaultExercises(): List<Exercise> {
            return listOf(
                // === MUSCULATION (STRENGTH) ===
                Exercise(
                    name = "Pompes",
                    category = ExerciseCategory.STRENGTH,
                    muscleGroup = "Pectoraux, Triceps, Épaules",
                    description = "Exercice de base pour le haut du corps. Gardez le corps droit, descendez jusqu'à ce que la poitrine touche presque le sol.",
                    equipment = "Aucun",
                    difficulty = DifficultyLevel.BEGINNER
                ),
                Exercise(
                    name = "Squats",
                    category = ExerciseCategory.STRENGTH,
                    muscleGroup = "Jambes, Fessiers, Quadriceps",
                    description = "Flexion des genoux avec le dos droit. Descendez jusqu'à ce que les cuisses soient parallèles au sol.",
                    equipment = "Aucun",
                    difficulty = DifficultyLevel.BEGINNER
                ),
                Exercise(
                    name = "Tractions",
                    category = ExerciseCategory.STRENGTH,
                    muscleGroup = "Dos, Biceps, Épaules",
                    description = "Tirez votre corps vers le haut jusqu'à ce que votre menton dépasse la barre.",
                    equipment = "Barre de traction",
                    difficulty = DifficultyLevel.INTERMEDIATE
                ),
                Exercise(
                    name = "Développé couché",
                    category = ExerciseCategory.STRENGTH,
                    muscleGroup = "Pectoraux, Triceps",
                    description = "Allongé sur un banc, abaissez la barre jusqu'à la poitrine puis poussez vers le haut.",
                    equipment = "Barre, banc",
                    difficulty = DifficultyLevel.INTERMEDIATE
                ),
                Exercise(
                    name = "Soulevé de terre",
                    category = ExerciseCategory.STRENGTH,
                    muscleGroup = "Dos, Jambes, Fessiers",
                    description = "Soulevez la barre du sol en gardant le dos droit. Mouvement composé pour tout le corps.",
                    equipment = "Barre",
                    difficulty = DifficultyLevel.ADVANCED
                ),
                Exercise(
                    name = "Dips",
                    category = ExerciseCategory.STRENGTH,
                    muscleGroup = "Triceps, Pectoraux",
                    description = "Sur des barres parallèles, descendez puis remontez en poussant avec les bras.",
                    equipment = "Barres parallèles",
                    difficulty = DifficultyLevel.INTERMEDIATE
                ),
                Exercise(
                    name = "Crunchs",
                    category = ExerciseCategory.STRENGTH,
                    muscleGroup = "Abdominaux",
                    description = "Allongé sur le dos, soulevez les épaules du sol en contractant les abdominaux.",
                    equipment = "Aucun",
                    difficulty = DifficultyLevel.BEGINNER
                ),
                Exercise(
                    name = "Planche",
                    category = ExerciseCategory.STRENGTH,
                    muscleGroup = "Core, Abdominaux",
                    description = "Maintenez une position de planche sur les avant-bras, corps bien droit.",
                    equipment = "Aucun",
                    difficulty = DifficultyLevel.BEGINNER
                ),
                
                // === CARDIO ===
                Exercise(
                    name = "Course",
                    category = ExerciseCategory.CARDIO,
                    muscleGroup = "Jambes, Cardio",
                    description = "Course sur place ou en extérieur. Maintenez un rythme régulier.",
                    equipment = "Aucun",
                    difficulty = DifficultyLevel.BEGINNER
                ),
                Exercise(
                    name = "Burpees",
                    category = ExerciseCategory.CARDIO,
                    muscleGroup = "Corps entier",
                    description = "Combinaison de squat, planche et saut. Exercice très intense.",
                    equipment = "Aucun",
                    difficulty = DifficultyLevel.INTERMEDIATE
                ),
                Exercise(
                    name = "Jumping Jacks",
                    category = ExerciseCategory.CARDIO,
                    muscleGroup = "Corps entier, Cardio",
                    description = "Sauts avec écart des jambes et des bras simultanés.",
                    equipment = "Aucun",
                    difficulty = DifficultyLevel.BEGINNER
                ),
                Exercise(
                    name = "Mountain Climbers",
                    category = ExerciseCategory.CARDIO,
                    muscleGroup = "Cardio, Core, Épaules",
                    description = "En position de planche, amenez les genoux alternativement vers la poitrine.",
                    equipment = "Aucun",
                    difficulty = DifficultyLevel.INTERMEDIATE
                ),
                Exercise(
                    name = "High Knees",
                    category = ExerciseCategory.CARDIO,
                    muscleGroup = "Jambes, Cardio",
                    description = "Course sur place en amenant les genoux le plus haut possible.",
                    equipment = "Aucun",
                    difficulty = DifficultyLevel.BEGINNER
                ),
                
                // === FLEXIBILITÉ ===
                Exercise(
                    name = "Étirements",
                    category = ExerciseCategory.FLEXIBILITY,
                    muscleGroup = "Corps entier",
                    description = "Étirements statiques pour améliorer la souplesse.",
                    equipment = "Aucun",
                    difficulty = DifficultyLevel.BEGINNER
                ),
                Exercise(
                    name = "Yoga",
                    category = ExerciseCategory.FLEXIBILITY,
                    muscleGroup = "Corps entier",
                    description = "Séries de postures de yoga pour flexibilité et détente.",
                    equipment = "Tapis",
                    difficulty = DifficultyLevel.BEGINNER
                ),
                Exercise(
                    name = "Touche orteils",
                    category = ExerciseCategory.FLEXIBILITY,
                    muscleGroup = "Jambes, Dos",
                    description = "Flexion avant pour toucher les orteils, jambes tendues.",
                    equipment = "Aucun",
                    difficulty = DifficultyLevel.BEGINNER
                ),
                
                // === ÉQUILIBRE ===
                Exercise(
                    name = "Tree Pose",
                    category = ExerciseCategory.BALANCE,
                    muscleGroup = "Jambes, Core",
                    description = "Yoga pose : tenez-vous sur une jambe, pied de l'autre jambe sur la cuisse.",
                    equipment = "Aucun",
                    difficulty = DifficultyLevel.INTERMEDIATE
                ),
                Exercise(
                    name = "Planche latérale",
                    category = ExerciseCategory.BALANCE,
                    muscleGroup = "Core, Épaules",
                    description = "Planche sur le côté, maintenir l'équilibre sur un bras.",
                    equipment = "Aucun",
                    difficulty = DifficultyLevel.INTERMEDIATE
                )
            )
        }
    }
}