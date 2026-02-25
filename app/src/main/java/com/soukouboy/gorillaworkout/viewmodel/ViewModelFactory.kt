package com.soukouboy.gorillaworkout.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.soukouboy.gorillaworkout.data.repository.ExerciseRepository
import com.soukouboy.gorillaworkout.data.repository.UserRepository
import com.soukouboy.gorillaworkout.data.repository.WorkoutRepository

class ViewModelFactory(
    private val workoutRepository: WorkoutRepository,
    private val exerciseRepository: ExerciseRepository,
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(workoutRepository) as T
            }
            modelClass.isAssignableFrom(ExerciseViewModel::class.java) -> {
                ExerciseViewModel(exerciseRepository) as T
            }
            modelClass.isAssignableFrom(WorkoutViewModel::class.java) -> {
                WorkoutViewModel(workoutRepository) as T
            }
            modelClass.isAssignableFrom(StatsViewModel::class.java) -> {
                StatsViewModel(workoutRepository, exerciseRepository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(userRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}