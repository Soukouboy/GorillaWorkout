package com.soukouboy.gorillaworkout.data.repository

import androidx.lifecycle.LiveData
import com.soukouboy.gorillaworkout.data.database.ExerciseDao
import com.soukouboy.gorillaworkout.data.model.Exercise
import com.soukouboy.gorillaworkout.data.model.ExerciseCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExerciseRepository(private val exerciseDao: ExerciseDao) {
    
    val allExercises: LiveData<List<Exercise>> = exerciseDao.getAllExercises()
    val favoriteExercises: LiveData<List<Exercise>> = exerciseDao.getFavoriteExercises()
    val exerciseCount: LiveData<Int> = exerciseDao.getExerciseCount()
    
    suspend fun insertExercise(exercise: Exercise): Long {
        return withContext(Dispatchers.IO) {
            exerciseDao.insertExercise(exercise)
        }
    }



    suspend fun updateExercise(exercise: Exercise) {
        withContext(Dispatchers.IO) {
            exerciseDao.updateExercise(exercise)
        }
    }
    
    suspend fun deleteExercise(exercise: Exercise) {
        withContext(Dispatchers.IO) {
            exerciseDao.deleteExercise(exercise)
        }
    }
    
    suspend fun deleteExerciseById(exerciseId: Int) {
        withContext(Dispatchers.IO) {
            exerciseDao.deleteExerciseById(exerciseId)
        }
    }
    
    suspend fun toggleFavorite(exerciseId: Int, isFavorite: Boolean) {
        withContext(Dispatchers.IO) {
            exerciseDao.toggleFavorite(exerciseId, isFavorite)
        }
    }
    
    fun getExercisesByCategory(category: ExerciseCategory): LiveData<List<Exercise>> {
        return exerciseDao.getExercisesByCategory(category)
    }
    
    fun searchExercises(searchQuery: String): LiveData<List<Exercise>> {
        return exerciseDao.searchExercises(searchQuery)
    }
    
    suspend fun getExerciseById(exerciseId: Int): Exercise? {
        return withContext(Dispatchers.IO) {
            exerciseDao.getExerciseById(exerciseId)
        }
    }
    
    // Restaurer les exercices par défaut
    suspend fun resetToDefaultExercises(defaultExercises: List<Exercise>) {
        withContext(Dispatchers.IO) {
            // Supprimer tous les exercices existants
            val exercises = allExercises.value
            exercises?.forEach { exercise ->
                exerciseDao.deleteExercise(exercise)
            }
            
            // Insérer les exercices par défaut
            defaultExercises.forEach { exercise ->
                exerciseDao.insertExercise(exercise)
            }
        }
    }
}