package com.soukouboy.gorillaworkout.ui.adapters


// Data class temporaire pour l'affichage (pas une entité Room)
data class SummaryExercise(
    val exerciseName: String,
    val sets: Int,
    val reps: Int,
    val weight: Double,
    val restTime: Int
)