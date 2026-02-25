package com.soukouboy.gorillaworkout.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "workout_exercises",
    primaryKeys = ["workoutId", "exerciseId"]
)
data class WorkoutExercise(
    val workoutId: Int,                        // FK vers Workout
    val exerciseId: Int,                       // FK vers Exercise
    val sets: Int = 3,                         // Nombre de séries
    val reps: Int = 10,                        // Répétitions par série
    val weight: Double = 0.0,                  // Poids utilisé (kg)
    val restTime: Int = 60,                    // Temps de repos (secondes)
    val order: Int = 0,                        // Ordre d'exécution
    val isCompleted: Boolean = false           // Complété ou non
) {
    // Fonction pour calculer le volume total (poids × reps × sets)
    fun getTotalVolume(): Double {
        return weight * reps * sets
    }
    
    // Fonction pour obtenir le temps de repos formaté
    fun getFormattedRestTime(): String {
        return if (restTime >= 60) {
            val minutes = restTime / 60
            val seconds = restTime % 60
            if (seconds == 0) {
                "${minutes}min"
            } else {
                "${minutes}min ${seconds}s"
            }
        } else {
            "${restTime}s"
        }
    }
}