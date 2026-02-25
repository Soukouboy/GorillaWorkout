package com.soukouboy.gorillaworkout.data.database

import androidx.room.TypeConverter
import com.soukouboy.gorillaworkout.data.model.*

class Converters {
    
    // === ExerciseCategory Converter ===
    @TypeConverter
    fun fromExerciseCategory(category: ExerciseCategory): String {
        return category.name
    }
    
    @TypeConverter
    fun toExerciseCategory(categoryName: String): ExerciseCategory {
        return try {
            ExerciseCategory.valueOf(categoryName)
        } catch (e: IllegalArgumentException) {
            ExerciseCategory.STRENGTH // Valeur par défaut
        }
    }
    
    // === DifficultyLevel Converter ===
    @TypeConverter
    fun fromDifficultyLevel(level: DifficultyLevel): String {
        return level.name
    }
    
    @TypeConverter
    fun toDifficultyLevel(levelName: String): DifficultyLevel {
        return try {
            DifficultyLevel.valueOf(levelName)
        } catch (e: IllegalArgumentException) {
            DifficultyLevel.BEGINNER // Valeur par défaut
        }
    }
    
    // === Gender Converter ===
    @TypeConverter
    fun fromGender(gender: Gender): String {
        return gender.name
    }
    
    @TypeConverter
    fun toGender(genderName: String): Gender {
        return try {
            Gender.valueOf(genderName)
        } catch (e: IllegalArgumentException) {
            Gender.OTHER // Valeur par défaut
        }
    }
    
    // === FitnessGoal Converter ===
    @TypeConverter
    fun fromFitnessGoal(goal: FitnessGoal): String {
        return goal.name
    }
    
    @TypeConverter
    fun toFitnessGoal(goalName: String): FitnessGoal {
        return try {
            FitnessGoal.valueOf(goalName)
        } catch (e: IllegalArgumentException) {
            FitnessGoal.GENERAL_FITNESS // Valeur par défaut
        }
    }
}