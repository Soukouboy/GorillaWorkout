package com.soukouboy.gorillaworkout.viewmodel

import androidx.lifecycle.*
import com.soukouboy.gorillaworkout.data.model.Workout
import com.soukouboy.gorillaworkout.data.repository.WorkoutRepository
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class HomeViewModel(
    private val workoutRepository: WorkoutRepository
) : ViewModel() {
    
    // === STATISTIQUES HEBDOMADAIRES ===
    val weeklyWorkouts: LiveData<Int> = workoutRepository.getWorkoutsSince(7).map { workouts ->
        workouts.size
    }
    
    val weeklyTime: LiveData<Int> = workoutRepository.getWorkoutsSince(7).map { workouts ->
        workouts.sumOf { it.duration }
    }
    
    val weeklyCalories: LiveData<Int> = workoutRepository.getWorkoutsSince(7).map { workouts ->
        workouts.sumOf { it.totalCalories }
    }
    
    // === SÉRIE DE JOURS CONSÉCUTIFS ===
    private val _currentStreak = MutableLiveData<Int>()
    val currentStreak: LiveData<Int> = _currentStreak
    
    // === DERNIER ENTRAÎNEMENT ===
    val lastWorkout: LiveData<Workout?> = workoutRepository.recentWorkouts.map { workouts ->
        workouts.firstOrNull()
    }
    
    // === TOTAL GÉNÉRAL ===
    val totalWorkouts: LiveData<Int> = workoutRepository.totalCompletedWorkouts
    val totalTime: LiveData<Int> = workoutRepository.totalWorkoutTime
    val totalCalories: LiveData<Int> = workoutRepository.totalCaloriesBurned
    
    init {
        loadCurrentStreak()
    }
    
    private fun loadCurrentStreak() {
        viewModelScope.launch {
            val streak = workoutRepository.getCurrentStreak()
            _currentStreak.postValue(streak)
        }
    }
    
    fun refreshData() {
        loadCurrentStreak()
    }
    
    // === FORMATAGE ===
    fun formatDuration(seconds: Int): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        return if (hours > 0) {
            "${hours}h ${minutes}min"
        } else {
            "${minutes}min"
        }
    }
    
    fun getWeeklyProgress(current: Int, goal: Int): Int {
        return if (goal > 0) {
            (current * 100 / goal).coerceAtMost(100)
        } else {
            0
        }
    }
}