package com.soukouboy.gorillaworkout.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,                          // Nom de l'exercice
    val category: ExerciseCategory,            // Catégorie (STRENGTH, CARDIO, etc.)
    val description: String = "",              // Description/instructions
    val muscleGroup: String = "",              // Groupe musculaire ciblé
    val equipment: String = "None",            // Équipement nécessaire
    val difficulty: DifficultyLevel = DifficultyLevel.BEGINNER, // Niveau de difficulté
    val imageUrl: String? = null,              // URL ou ressource image
    val isFavorite: Boolean = false,           // Marqué comme favori
    val createdAt: Long = System.currentTimeMillis() // Date de création
) {
    // Constructeur secondaire pour faciliter la création
    constructor(
        name: String,
        category: ExerciseCategory,
        muscleGroup: String,
        description: String = "",
        equipment: String = "None",
        difficulty: DifficultyLevel = DifficultyLevel.BEGINNER
    ) : this(
        name = name,
        category = category,
        muscleGroup = muscleGroup,
        description = description,
        equipment = equipment,
        difficulty = difficulty,
        imageUrl = null,
        isFavorite = false,
        createdAt = System.currentTimeMillis()
    )
}