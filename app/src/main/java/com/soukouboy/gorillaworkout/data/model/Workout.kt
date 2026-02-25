package com.soukouboy.gorillaworkout.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class Workout(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,                          // Nom de la séance
    val date: Long = System.currentTimeMillis(), // Date/heure
    val duration: Int = 0,                     // Durée en secondes
    val totalCalories: Int = 0,                // Calories brûlées (estimé)
    val notes: String = "",                    // Notes de l'utilisateur
    val isCompleted: Boolean = false           // Statut de complétion
) {
    // Fonction utilitaire pour obtenir la durée formatée
    fun getFormattedDuration(): String {
        val hours = duration / 3600
        val minutes = (duration % 3600) / 60
        val seconds = duration % 60
        
        return if (hours > 0) {
            String.format("%d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format("%d:%02d", minutes, seconds)
        }
    }
    
    // Fonction pour obtenir la date formatée (jour/mois/année)
    fun getFormattedDate(): String {
        val dateObj = java.util.Date(date)
        val formatter = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault())
        return formatter.format(dateObj)
    }
}