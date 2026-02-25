package com.soukouboy.gorillaworkout.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soukouboy.gorillaworkout.data.repository.WorkoutRepository
import com.soukouboy.gorillaworkout.data.repository.ExerciseRepository
import com.soukouboy.gorillaworkout.ui.adapters.SummaryExercise
import kotlinx.coroutines.launch
import com.soukouboy.gorillaworkout.data.model.WorkoutExercise  // Votre classe exacte

class WorkoutSummaryViewModel(
    private val workoutRepository: WorkoutRepository,
    private val exerciseRepository: ExerciseRepository
) : ViewModel() {

    data class WorkoutSummary(
        val name: String,
        val duration: Int,
        val calories: Int,
        val exercises: List<SummaryExercise>
    )

    private val _workoutSummary = MutableLiveData<WorkoutSummary>()
    val workoutSummary: LiveData<WorkoutSummary> = _workoutSummary

    fun loadWorkoutSummary(workoutId: Int) {
        viewModelScope.launch {
            // ⚠️ VÉRIFIEZ QUE VOTRE DAO A CES MÉTHODES :
            val workout = workoutRepository.getWorkoutById(workoutId)
            val workoutExercises = workoutRepository.getWorkoutExercisesByWorkoutId(workoutId)

            val summaryExercises = workoutExercises.map { we ->
                // ⚠️ VÉRIFIEZ QUE VOTRE DAO A CETTE MÉTHODE :
                val exercise = exerciseRepository.getExerciseById(we.exerciseId)

                SummaryExercise(
                    exerciseName = exercise?.name ?: "Exercice inconnu",
                    sets = we.sets,
                    reps = we.reps,
                    weight = we.weight,
                    restTime = we.restTime
                )
            }

            _workoutSummary.value = WorkoutSummary(
                name = workout?.name ?: "Workout",
                duration = workout?.duration ?: 0,
                calories = workout?.totalCalories ?: 0,
                exercises = summaryExercises
            )
        }
    }
}