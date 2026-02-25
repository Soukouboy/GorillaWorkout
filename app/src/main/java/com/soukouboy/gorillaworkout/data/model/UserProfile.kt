package com.soukouboy.gorillaworkout.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey
    val id: Int = 1,                           // ID fixe (un seul profil)
    val name: String = "",                     // Nom de l'utilisateur
    val age: Int = 0,                          // Âge
    val weight: Double = 0.0,                  // Poids en kg
    val height: Double = 0.0,                  // Taille en cm
    val gender: Gender = Gender.OTHER,         // Genre
    val fitnessGoal: FitnessGoal = FitnessGoal.GENERAL_FITNESS, // Objectif principal
    val experienceLevel: DifficultyLevel = DifficultyLevel.BEGINNER, // Niveau d'expérience
    val weeklyGoal: Int = 3,                   // Séances par semaine visées
    val createdAt: Long = System.currentTimeMillis() // Date de création
) {
    // Fonction pour calculer l'IMC (Indice de Masse Corporelle)
    fun getBMI(): Double {
        return if (height > 0) {
            weight / ((height / 100) * (height / 100))
        } else {
            0.0
        }
    }
    
    // Fonction pour obtenir la catégorie d'IMC
    fun getBMICategory(): String {
        val bmi = getBMI()
        return when {
            bmi < 18.5 -> "Insuffisance pondérale"
            bmi < 25 -> "Poids normal"
            bmi < 30 -> "Surpoids"
            bmi > 0 -> "Obésité"
            else -> "Non disponible"
        }
    }
    
    // Fonction pour calculer les calories brûlées lors d'un entraînement (estimation)
    fun calculateCalories(durationMinutes: Int, intensity: String = "moderate"): Int {
        // Formule simplifiée basée sur le poids et la durée
        val baseCaloriesPerMinute = when (intensity) {
            "low" -> 3.0
            "moderate" -> 5.0
            "high" -> 8.0
            else -> 5.0
        }
        
        return (baseCaloriesPerMinute * durationMinutes * (weight / 70.0)).toInt()
    }
}