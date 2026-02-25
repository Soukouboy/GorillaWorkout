package com.soukouboy.gorillaworkout.viewmodel

import androidx.lifecycle.*
import com.soukouboy.gorillaworkout.data.model.Exercise
import com.soukouboy.gorillaworkout.data.model.Workout
import com.soukouboy.gorillaworkout.data.model.WorkoutExercise
import com.soukouboy.gorillaworkout.data.repository.WorkoutRepository
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit

class WorkoutViewModel(
    private val workoutRepository: WorkoutRepository
) : ViewModel() {

    // === ENTRAÎNEMENT EN COURS ===
    private val _currentWorkout = MutableLiveData<Workout?>(null)
    val currentWorkout: LiveData<Workout?> = _currentWorkout

    private val _currentWorkoutExercises = MutableLiveData<List<WorkoutExercise>>(emptyList())
    val currentWorkoutExercises: LiveData<List<WorkoutExercise>> = _currentWorkoutExercises

    private val _currentExerciseIndex = MutableLiveData<Int>(0)
    val currentExerciseIndex: LiveData<Int> = _currentExerciseIndex

    private val _currentSet = MutableLiveData<Int>(1)
    val currentSet: LiveData<Int> = _currentSet

    // === TIMERS ===
    private val _restTimeRemaining = MutableLiveData<Int>(0)
    val restTimeRemaining: LiveData<Int> = _restTimeRemaining

    private val _totalTimeElapsed = MutableLiveData<Long>(0)
    val totalTimeElapsed: LiveData<Long> = _totalTimeElapsed

    private val _isRestTimerRunning = MutableLiveData<Boolean>(false)
    val isRestTimerRunning: LiveData<Boolean> = _isRestTimerRunning

    private val _isWorkoutActive = MutableLiveData<Boolean>(false)
    val isWorkoutActive: LiveData<Boolean> = _isWorkoutActive

    // === PROGRESSION ===
    val progressPercentage: LiveData<Int> = MediatorLiveData<Int>().apply {
        addSource(_currentExerciseIndex) { index ->
            updateProgress()
        }
        addSource(_currentSet) { set ->
            updateProgress()
        }
        addSource(_currentWorkoutExercises) { exercises ->
            updateProgress()
        }
    }

    private fun updateProgress() {
        val exercises = _currentWorkoutExercises.value ?: return
        val currentIndex = _currentExerciseIndex.value ?: 0
        val currentSetNum = _currentSet.value ?: 1

        if (exercises.isEmpty()) {
            (progressPercentage as MutableLiveData).value = 0
            return
        }

        // Calcul basé sur les exercices complétés + le set actuel
        var completedSets = 0
        var totalSets = 0

        exercises.forEachIndexed { index, exercise ->
            totalSets += exercise.sets
            if (index < currentIndex) {
                completedSets += exercise.sets
            } else if (index == currentIndex) {
                completedSets += (currentSetNum - 1).coerceAtLeast(0)
            }
        }

        val percentage = if (totalSets > 0) {
            (completedSets * 100 / totalSets).coerceAtMost(100)
        } else {
            0
        }

        (progressPercentage as MutableLiveData).value = percentage
    }

    // === CRÉATION D'ENTRAÎNEMENT ===
    fun createWorkout(name: String) {
        viewModelScope.launch {
            val workout = Workout(name = name)
            val workoutId = workoutRepository.insertWorkout(workout)
            _currentWorkout.value = workout.copy(id = workoutId.toInt())
        }
    }

    fun addExerciseToWorkout(exercise: Exercise, sets: Int, reps: Int, weight: Double, restTime: Int) {
        val workout = _currentWorkout.value ?: return
        val currentExercises = _currentWorkoutExercises.value ?: emptyList()

        val workoutExercise = WorkoutExercise(
            workoutId = workout.id,
            exerciseId = exercise.id,
            sets = sets,
            reps = reps,
            weight = weight,
            restTime = restTime,
            order = currentExercises.size
        )

        viewModelScope.launch {
            workoutRepository.insertWorkoutExercise(workoutExercise)
            loadWorkoutExercises(workout.id)
        }
    }

    fun removeExerciseFromWorkout(exercise: WorkoutExercise) {
        viewModelScope.launch {
            workoutRepository.deleteWorkoutExercise(exercise)
            _currentWorkout.value?.let { workout ->
                loadWorkoutExercises(workout.id)
            }
        }
    }

    fun updateWorkoutExercise(exercise: WorkoutExercise) {
        viewModelScope.launch {
            workoutRepository.updateWorkoutExercise(exercise)
            _currentWorkout.value?.let { workout ->
                loadWorkoutExercises(workout.id)
            }
        }
    }

    private fun loadWorkoutExercises(workoutId: Int) {
        // Note: Dans une implémentation réelle, on observerait le LiveData
        // Pour simplifier, on met à jour directement
        viewModelScope.launch {
            // Ici on devrait observer le LiveData, mais pour la simplicité:
            // (Dans une vraie app, on utiliserait un observer)
        }
    }

    // === DÉMARRER UN ENTRAÎNEMENT ===
    fun startWorkout(workout: Workout, exercises: List<WorkoutExercise>) {
        _currentWorkout.value = workout
        _currentWorkoutExercises.value = exercises.sortedBy { it.order }
        _currentExerciseIndex.value = 0
        _currentSet.value = 1
        _totalTimeElapsed.value = 0
        _isWorkoutActive.value = true

        // Démarrer le chronomètre global (dans une vraie implémentation)
        startGlobalTimer()
    }

    // === GESTION DES SÉRIES ===
    fun completeSet(repsDone: Int) {
        val exercises = _currentWorkoutExercises.value ?: return
        val currentIndex = _currentExerciseIndex.value ?: 0
        val currentSetNum = _currentSet.value ?: 1

        if (currentIndex >= exercises.size) return

        val currentExercise = exercises[currentIndex]

        // Si toutes les séries sont faites pour cet exercice
        if (currentSetNum >= currentExercise.sets) {
            // Passer à l'exercice suivant
            if (currentIndex < exercises.size - 1) {
                _currentExerciseIndex.value = currentIndex + 1
                _currentSet.value = 1
                startRestTimer(currentExercise.restTime)
            } else {
                // Entraînement terminé
                finishWorkout()
            }
        } else {
            // Prochaine série du même exercice
            _currentSet.value = currentSetNum + 1
            startRestTimer(currentExercise.restTime)
        }
    }

    fun skipToNextExercise() {
        val exercises = _currentWorkoutExercises.value ?: return
        val currentIndex = _currentExerciseIndex.value ?: 0

        if (currentIndex < exercises.size - 1) {
            _currentExerciseIndex.value = currentIndex + 1
            _currentSet.value = 1
        } else {
            finishWorkout()
        }
    }

    // === TIMERS ===
    private fun startRestTimer(seconds: Int) {
        _restTimeRemaining.value = seconds
        _isRestTimerRunning.value = true

        // Dans une vraie implémentation, on utiliserait un CountDownTimer
        // Pour cette démo, on simule
        viewModelScope.launch {
            var timeLeft = seconds
            while (timeLeft > 0) {
                kotlinx.coroutines.delay(1000)
                timeLeft--
                _restTimeRemaining.value = timeLeft
            }
            _isRestTimerRunning.value = false
        }
    }

    fun skipRestTimer() {
        _isRestTimerRunning.value = false
        _restTimeRemaining.value = 0
    }

    fun addRestTime(seconds: Int) {
        _restTimeRemaining.value = (_restTimeRemaining.value ?: 0) + seconds
    }

    private fun startGlobalTimer() {
        // Chronomètre global de l'entraînement
        viewModelScope.launch {
            var elapsed = 0L
            while (_isWorkoutActive.value == true) {
                kotlinx.coroutines.delay(1000)
                elapsed += 1
                _totalTimeElapsed.value = elapsed
            }
        }
    }

    fun pauseWorkout() {
        _isWorkoutActive.value = false
        _isRestTimerRunning.value = false
    }

    fun resumeWorkout() {
        _isWorkoutActive.value = true
        startGlobalTimer()
    }

    // === FIN D'ENTRAÎNEMENT ===
    fun finishWorkout() {
        _isWorkoutActive.value = false
        _isRestTimerRunning.value = false

        val workout = _currentWorkout.value ?: return
        val totalTime = _totalTimeElapsed.value?.toInt() ?: 0

        // Calculer les calories (estimation simple)
        val calories = (totalTime / 60) * 5 // 5 calories par minute en moyenne

        val completedWorkout = workout.copy(
            duration = totalTime,
            totalCalories = calories,
            isCompleted = true
        )

        viewModelScope.launch {
            workoutRepository.updateWorkout(completedWorkout)
            _currentWorkout.value = completedWorkout
        }
    }

    // === UTILITAIRES ===
    fun getCurrentExercise(): WorkoutExercise? {
        val exercises = _currentWorkoutExercises.value ?: return null
        val index = _currentExerciseIndex.value ?: 0
        return if (index < exercises.size) exercises[index] else null
    }

    fun formatTime(seconds: Long): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60
        return String.format("%02d:%02d:%02d", hours, minutes, secs)
    }
}