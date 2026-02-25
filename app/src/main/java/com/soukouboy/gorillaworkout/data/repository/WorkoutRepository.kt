package com.soukouboy.gorillaworkout.data.repository

import androidx.lifecycle.LiveData
import com.soukouboy.gorillaworkout.data.database.WorkoutDao
import com.soukouboy.gorillaworkout.data.model.Workout
import com.soukouboy.gorillaworkout.data.model.WorkoutExercise
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import java.util.concurrent.TimeUnit

class WorkoutRepository(private val workoutDao: WorkoutDao) {
    
    // === WORKOUTS ===
    val allWorkouts: LiveData<List<Workout>> = workoutDao.getAllWorkouts()
    val completedWorkouts: LiveData<List<Workout>> = workoutDao.getCompletedWorkouts()
    val recentWorkouts: LiveData<List<Workout>> = workoutDao.getRecentWorkouts()
    
    suspend fun insertWorkout(workout: Workout): Long {
        return withContext(Dispatchers.IO) {
            workoutDao.insertWorkout(workout)
        }
    }
    
    suspend fun updateWorkout(workout: Workout) {
        withContext(Dispatchers.IO) {
            workoutDao.updateWorkout(workout)
        }
    }
    
    suspend fun deleteWorkout(workout: Workout) {
        withContext(Dispatchers.IO) {
            workoutDao.deleteWorkout(workout)
        }
    }


    suspend fun getWorkoutById(workoutId: Int): Workout? {
        return withContext(Dispatchers.IO) {
            workoutDao.getWorkoutById(workoutId)
        }
    }



    suspend fun getWorkoutExercisesByWorkoutId(workoutId: Int) =
        workoutDao.getWorkoutExercisesByWorkoutId(workoutId)
    
    // === WORKOUT EXERCISES ===
    fun getWorkoutExercises(workoutId: Int): LiveData<List<WorkoutExercise>> {
        return workoutDao.getWorkoutExercises(workoutId)
    }
    
    suspend fun insertWorkoutExercise(workoutExercise: WorkoutExercise) {
        withContext(Dispatchers.IO) {
            workoutDao.insertWorkoutExercise(workoutExercise)
        }
    }
    
    suspend fun updateWorkoutExercise(workoutExercise: WorkoutExercise) {
        withContext(Dispatchers.IO) {
            workoutDao.updateWorkoutExercise(workoutExercise)
        }
    }
    
    suspend fun deleteWorkoutExercise(workoutExercise: WorkoutExercise) {
        withContext(Dispatchers.IO) {
            workoutDao.deleteWorkoutExercise(workoutExercise)
        }
    }
    
    suspend fun deleteAllWorkoutExercises(workoutId: Int) {
        withContext(Dispatchers.IO) {
            workoutDao.deleteAllWorkoutExercises(workoutId)
        }
    }
    
    // === STATISTIQUES ===
    val totalCompletedWorkouts: LiveData<Int> = workoutDao.getTotalCompletedWorkouts()
    val totalWorkoutTime: LiveData<Int> = workoutDao.getTotalWorkoutTime()
    val totalCaloriesBurned: LiveData<Int> = workoutDao.getTotalCaloriesBurned()
    val averageWorkoutDuration: LiveData<Double> = workoutDao.getAverageWorkoutDuration()
    val longestWorkoutDuration: LiveData<Int> = workoutDao.getLongestWorkoutDuration()
    
    // === FILTRAGE PAR DATE ===
    fun getWorkoutsSince(days: Int): LiveData<List<Workout>> {
        val startDate = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(days.toLong())
        return workoutDao.getWorkoutsSince(startDate)
    }
    
    fun getWorkoutsBetween(startDaysAgo: Int, endDaysAgo: Int): LiveData<List<Workout>> {
        val endDate = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(endDaysAgo.toLong())
        val startDate = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(startDaysAgo.toLong())
        return workoutDao.getWorkoutsBetween(startDate, endDate)
    }
    
    // === JOURS CONSÉCUTIFS ===
    suspend fun getCurrentStreak(): Int {
        return withContext(Dispatchers.IO) {
            val workoutDates = workoutDao.getAllWorkoutDates()
            if (workoutDates.isEmpty()) return@withContext 0
            
            val today = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis
            
            val sortedDates = workoutDates.sortedDescending()
            var streak = 0
            var currentDate = today
            
            for (date in sortedDates) {
                val workoutDate = Calendar.getInstance().apply {
                    timeInMillis = date
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }.timeInMillis
                
                val diff = currentDate - workoutDate
                val daysDiff = TimeUnit.MILLISECONDS.toDays(diff)
                
                when (daysDiff) {
                    0L -> {
                        streak++
                        currentDate = workoutDate - TimeUnit.DAYS.toMillis(1)
                    }
                    1L -> {
                        streak++
                        currentDate = workoutDate - TimeUnit.DAYS.toMillis(1)
                    }
                    else -> break
                }
            }
            
            streak
        }
    }
    
    // === SUPPRESSION TOTALE ===
    suspend fun resetAllData() {
        withContext(Dispatchers.IO) {
            // Note: Les workouts seront supprimés en cascade grâce aux FK
            // mais Room ne supporte pas les cascades automatiques par défaut
            // donc on doit gérer manuellement
            
            val workouts = workoutDao.getAllWorkouts().value
            workouts?.forEach { workout ->
                workoutDao.deleteAllWorkoutExercises(workout.id)
                workoutDao.deleteWorkout(workout)
            }
        }
    }
}