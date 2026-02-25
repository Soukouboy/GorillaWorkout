package com.soukouboy.gorillaworkout.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.*
import com.soukouboy.gorillaworkout.data.model.Workout
import com.soukouboy.gorillaworkout.data.model.WorkoutExercise
import com.soukouboy.gorillaworkout.data.repository.WorkoutRepository
import kotlinx.coroutines.launch

class ActiveWorkoutViewModel(
    private val workoutRepository: WorkoutRepository,
    private val workoutId: Int
) : ViewModel() {

    private val _currentWorkout = MutableLiveData<Workout>()
    val currentWorkout: LiveData<Workout> = _currentWorkout

    private val _exercises = MutableLiveData<List<WorkoutExercise>>()
    val exercises: LiveData<List<WorkoutExercise>> = _exercises

    private val _currentExerciseIndex = MutableLiveData(0)
    val currentExerciseIndex: LiveData<Int> = _currentExerciseIndex

    private val _currentSet = MutableLiveData(1)
    val currentSet: LiveData<Int> = _currentSet

    private val _totalTimeElapsed = MutableLiveData(0L)
    val totalTimeElapsed: LiveData<Long> = _totalTimeElapsed

    private val _restTimeRemaining = MutableLiveData(0)
    val restTimeRemaining: LiveData<Int> = _restTimeRemaining

    private val _isRestTimerRunning = MutableLiveData(false)
    val isRestTimerRunning: LiveData<Boolean> = _isRestTimerRunning

    private val _isWorkoutActive = MutableLiveData(false)
    val isWorkoutActive: LiveData<Boolean> = _isWorkoutActive

    val progressPercentage: LiveData<Int> = MediatorLiveData<Int>().apply {
        addSource(_currentExerciseIndex) { updateProgress() }
        addSource(_currentSet) { updateProgress() }
        addSource(_exercises) { updateProgress() }
    }

    private var globalTimer: CountDownTimer? = null
    private var restTimer: CountDownTimer? = null

    init {
        loadWorkout()
    }

    fun startWorkout() {
        _isWorkoutActive.value = true
        startGlobalTimer()
    }

    private fun loadWorkout() {
        viewModelScope.launch {
            _currentWorkout.value = workoutRepository.getWorkoutById(workoutId)
            _exercises.value = workoutRepository.getWorkoutExercisesByWorkoutId(workoutId)
        }
    }

    private fun startGlobalTimer() {
        globalTimer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _totalTimeElapsed.value = _totalTimeElapsed.value?.plus(1)
            }
            override fun onFinish() {}
        }.start()
    }

    fun completeSet() {
        val exercise = getCurrentExercise() ?: return
        val currentSetNum = _currentSet.value ?: 1

        if (currentSetNum >= exercise.sets) {
            // Exercice terminé, passer au suivant
            if (_currentExerciseIndex.value!! < _exercises.value!!.size - 1) {
                _currentExerciseIndex.value = _currentExerciseIndex.value!! + 1
                _currentSet.value = 1
                startRestTimer(exercise.restTime)
            } else {
                // Workout terminé
                finishWorkout()
            }
        } else {
            // Prochaine série
            _currentSet.value = currentSetNum + 1
            startRestTimer(exercise.restTime)
        }
    }

    fun skipToNextExercise() {
        val nextIndex = _currentExerciseIndex.value!! + 1
        if (nextIndex < _exercises.value!!.size) {
            _currentExerciseIndex.value = nextIndex
            _currentSet.value = 1
        } else {
            finishWorkout()
        }
    }

    fun startRestTimer(seconds: Int) {
        _restTimeRemaining.value = seconds
        _isRestTimerRunning.value = true

        restTimer?.cancel()
        restTimer = object : CountDownTimer(seconds * 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _restTimeRemaining.value = (millisUntilFinished / 1000).toInt()
            }
            override fun onFinish() {
                _isRestTimerRunning.value = false
                _restTimeRemaining.value = 0
            }
        }.start()
    }

    fun skipRestTimer() {
        restTimer?.cancel()
        _isRestTimerRunning.value = false
        _restTimeRemaining.value = 0
    }

    fun addRestTime(seconds: Int) {
        _restTimeRemaining.value = (_restTimeRemaining.value ?: 0) + seconds
        startRestTimer(_restTimeRemaining.value ?: 0)
    }

    fun finishWorkout() {
        globalTimer?.cancel()
        restTimer?.cancel()
        _isWorkoutActive.value = false
        _isRestTimerRunning.value = false

        viewModelScope.launch {
            _currentWorkout.value?.let { workout ->
                val updatedWorkout = workout.copy(
                    duration = _totalTimeElapsed.value?.toInt() ?: 0,
                    totalCalories = calculateCalories(),
                    isCompleted = true
                )
                workoutRepository.updateWorkout(updatedWorkout)
            }
        }
    }

    fun pauseWorkout() {
        _isWorkoutActive.value = false
        globalTimer?.cancel()
    }

    fun resumeWorkout() {
        _isWorkoutActive.value = true
        startGlobalTimer()
    }

    fun getCurrentExercise(): WorkoutExercise? {
        val index = _currentExerciseIndex.value ?: return null
        return _exercises.value?.getOrNull(index)
    }

    private fun updateProgress() {
        val exercises = _exercises.value ?: return
        val index = _currentExerciseIndex.value ?: 0
        val set = _currentSet.value ?: 1

        if (exercises.isEmpty()) return

        var completed = 0
        var total = exercises.sumOf { it.sets }

        exercises.forEachIndexed { i, exercise ->
            if (i < index) {
                completed += exercise.sets
            } else if (i == index) {
                completed += (set - 1).coerceAtLeast(0)
            }
        }

        (_currentWorkout as MediatorLiveData).value = _currentWorkout.value // Trigger
    }

    private fun calculateCalories(): Int {
        // Estimation simplifiée : 5 cal/minutes
        return ((_totalTimeElapsed.value ?: 0L) / 60 * 5).toInt()
    }

    override fun onCleared() {
        super.onCleared()
        globalTimer?.cancel()
        restTimer?.cancel()
    }
}