package com.soukouboy.gorillaworkout.viewmodel

import androidx.lifecycle.*
import com.soukouboy.gorillaworkout.data.model.ExerciseCategory
import com.soukouboy.gorillaworkout.data.model.Workout
import com.soukouboy.gorillaworkout.data.model.WorkoutExercise
import com.soukouboy.gorillaworkout.data.repository.ExerciseRepository
import com.soukouboy.gorillaworkout.data.repository.WorkoutRepository
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit

class StatsViewModel(
    private val workoutRepository: WorkoutRepository,
    private val exerciseRepository: ExerciseRepository
) : ViewModel() {
    
    // === STATISTIQUES GLOBALES ===
    val totalWorkouts: LiveData<Int> = workoutRepository.totalCompletedWorkouts
    val totalTime: LiveData<Int> = workoutRepository.totalWorkoutTime
    val totalCalories: LiveData<Int> = workoutRepository.totalCaloriesBurned
    val averageWorkoutDuration: LiveData<Double> = workoutRepository.averageWorkoutDuration
    val longestWorkoutDuration: LiveData<Int> = workoutRepository.longestWorkoutDuration
    
    // === DONNÉES HEBDOMADAIRES ===
    val weeklyData: LiveData<List<WeeklyStats>> = workoutRepository.getWorkoutsSince(28).map { workouts ->
        calculateWeeklyStats(workouts)
    }
    
    // === RÉPARTITION PAR CATÉGORIE ===
    val categoryDistribution: LiveData<Map<ExerciseCategory, Int>> = workoutRepository.completedWorkouts.map { workouts ->
        calculateCategoryDistribution(workouts)
    }
    
    // === HISTORIQUE RÉCENT ===
    val recentWorkouts: LiveData<List<Workout>> = workoutRepository.recentWorkouts
    
    // === ÉVOLUTION MENSUELLE ===
    val monthlyProgress: LiveData<List<MonthlyStats>> = workoutRepository.getWorkoutsSince(180).map { workouts ->
        calculateMonthlyStats(workouts)
    }
    
    // === RECORDS PERSONNELS ===
    private val _personalRecords = MutableLiveData<PersonalRecords>()
    val personalRecords: LiveData<PersonalRecords> = _personalRecords
    
    init {
        loadPersonalRecords()
    }
    
    private fun calculateWeeklyStats(workouts: List<Workout>): List<WeeklyStats> {
        val calendar = Calendar.getInstance()
        val currentWeek = calendar.get(Calendar.WEEK_OF_YEAR)
        
        // Regrouper par semaine (4 dernières semaines)
        val weeklyMap = mutableMapOf<Int, MutableList<Workout>>()
        
        workouts.forEach { workout ->
            calendar.timeInMillis = workout.date
            val week = calendar.get(Calendar.WEEK_OF_YEAR)
            
            // S'assurer que c'est dans les 4 dernières semaines
            val weekDiff = currentWeek - week
            if (weekDiff in 0..3) {
                weeklyMap.getOrPut(week) { mutableListOf() }.add(workout)
            }
        }
        
        // Créer les statistiques pour chaque semaine
        val weeklyStats = mutableListOf<WeeklyStats>()
        for (i in 3 downTo 0) {
            val targetWeek = currentWeek - i
            val weekWorkouts = weeklyMap[targetWeek] ?: emptyList()
            
            weeklyStats.add(WeeklyStats(
                weekLabel = when (i) {
                    0 -> "Cette semaine"
                    1 -> "Semaine dernière"
                    2 -> "Il y a 2 semaines"
                    else -> "Il y a 3 semaines"
                },
                workoutCount = weekWorkouts.size,
                totalDuration = weekWorkouts.sumOf { it.duration },
                totalCalories = weekWorkouts.sumOf { it.totalCalories }
            ))
        }
        
        return weeklyStats
    }
    
    private fun calculateCategoryDistribution(workouts: List<Workout>): Map<ExerciseCategory, Int> {
        val distribution = mutableMapOf<ExerciseCategory, Int>()
        
        // Note: Pour une vraie implémentation, on devrait récupérer les exercices
        // de chaque workout pour connaître leur catégorie
        // Pour cette démo, on simule une distribution
        distribution[ExerciseCategory.STRENGTH] = (workouts.size * 0.6).toInt()
        distribution[ExerciseCategory.CARDIO] = (workouts.size * 0.3).toInt()
        distribution[ExerciseCategory.FLEXIBILITY] = (workouts.size * 0.1).toInt()
        
        return distribution
    }
    
    private fun calculateMonthlyStats(workouts: List<Workout>): List<MonthlyStats> {
        val monthlyMap = mutableMapOf<String, MutableList<Workout>>()
        val calendar = Calendar.getInstance()
        
        workouts.forEach { workout ->
            calendar.timeInMillis = workout.date
            val month = calendar.get(Calendar.MONTH)
            val year = calendar.get(Calendar.YEAR)
            val key = "$year-$month"
            
            monthlyMap.getOrPut(key) { mutableListOf() }.add(workout)
        }
        
        return monthlyMap.map { (key, monthWorkouts) ->
            val parts = key.split("-")
            val year = parts[0].toInt()
            val month = parts[1].toInt()
            
            val monthName = Calendar.getInstance().apply {
                set(Calendar.MONTH, month)
            }.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.FRENCH) ?: ""
            
            MonthlyStats(
                monthLabel = "$monthName $year",
                workoutCount = monthWorkouts.size,
                totalDuration = monthWorkouts.sumOf { it.duration },
                totalCalories = monthWorkouts.sumOf { it.totalCalories }
            )
        }.sortedBy { it.monthLabel }
    }
    
    private fun loadPersonalRecords() {
        viewModelScope.launch {
            val workouts = workoutRepository.completedWorkouts.value ?: emptyList()
            
            val records = PersonalRecords(
                longestWorkout = workouts.maxByOrNull { it.duration }?.let {
                    Record(it.name, formatDuration(it.duration))
                },
                mostCalories = workouts.maxByOrNull { it.totalCalories }?.let {
                    Record(it.name, "${it.totalCalories} calories")
                },
                longestStreak = workoutRepository.getCurrentStreak().let {
                    Record("Série actuelle", "$it jours")
                },
                totalWorkouts = Record("Total entraînements", "${workouts.size} séances")
            )
            
            _personalRecords.postValue(records)
        }
    }
    
    fun formatDuration(seconds: Int): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        return if (hours > 0) {
            "${hours}h ${minutes}min"
        } else {
            "${minutes}min"
        }
    }
    
    fun getFormattedDate(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - timestamp
        val days = TimeUnit.MILLISECONDS.toDays(diff)
        
        return when (days) {
            0L -> "Aujourd'hui"
            1L -> "Hier"
            2L -> "Avant-hier"
            in 3..6 -> "Il y a $days jours"
            else -> {
                val date = Date(timestamp)
                val formatter = java.text.SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH)
                formatter.format(date)
            }
        }
    }
    
    fun refreshData() {
        loadPersonalRecords()
    }
}

// === DATA CLASSES POUR LES STATISTIQUES ===
data class WeeklyStats(
    val weekLabel: String,
    val workoutCount: Int,
    val totalDuration: Int,
    val totalCalories: Int
)

data class MonthlyStats(
    val monthLabel: String,
    val workoutCount: Int,
    val totalDuration: Int,
    val totalCalories: Int
)

data class PersonalRecords(
    val longestWorkout: Record?,
    val mostCalories: Record?,
    val longestStreak: Record?,
    val totalWorkouts: Record?
)

data class Record(
    val label: String,
    val value: String
)