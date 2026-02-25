package com.soukouboy.gorillaworkout.viewmodel

import androidx.lifecycle.*
import com.soukouboy.gorillaworkout.data.model.Exercise
import com.soukouboy.gorillaworkout.data.model.ExerciseCategory
import com.soukouboy.gorillaworkout.data.repository.ExerciseRepository
import kotlinx.coroutines.launch

class ExerciseViewModel(
    private val exerciseRepository: ExerciseRepository
) : ViewModel() {

    // === TOUS LES EXERCICES ===
    val allExercisess: LiveData<List<Exercise>> = exerciseRepository.allExercises
    val favoriteExercises: LiveData<List<Exercise>> = exerciseRepository.favoriteExercises
    val exerciseCount: LiveData<Int> = exerciseRepository.exerciseCount

    // === FILTRAGE ET RECHERCHE ===
    private val _currentFilter = MutableLiveData<ExerciseCategory?>(null)
    private val _searchQuery = MutableLiveData<String>("")

    val filteredExercises: LiveData<List<Exercise>> = MediatorLiveData<List<Exercise>>().apply {
        addSource(allExercisess) { exercises ->
            value = filterExercises(exercises, _currentFilter.value, _searchQuery.value ?: "")
        }
        addSource(_currentFilter) { filter ->
            allExercises.value?.let { exercises ->
                value = filterExercises(exercises, filter, _searchQuery.value ?: "")
            }
        }
        addSource(_searchQuery) { query ->
            allExercises.value?.let { exercises ->
                value = filterExercises(exercises, _currentFilter.value, query)
            }
        }
    }

        val allExercises: LiveData<List<Exercise>> = exerciseRepository.allExercises





    private fun filterExercises(
        exercises: List<Exercise>,
        category: ExerciseCategory?,
        searchQuery: String
    ): List<Exercise> {
        return exercises.filter { exercise ->
            val matchesCategory = category == null || exercise.category == category
            val matchesSearch = searchQuery.isBlank() ||
                exercise.name.contains(searchQuery, ignoreCase = true) ||
                exercise.muscleGroup.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesSearch
        }
    }

    // === ACTIONS ===
    fun setCategoryFilter(category: ExerciseCategory?) {
        _currentFilter.value = category
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun clearFilters() {
        _currentFilter.value = null
        _searchQuery.value = ""
    }

    // === CRUD OPERATIONS ===
    fun addExercise(exercise: Exercise) {
        viewModelScope.launch {
            exerciseRepository.insertExercise(exercise)
        }
    }

    fun updateExercise(exercise: Exercise) {
        viewModelScope.launch {
            exerciseRepository.updateExercise(exercise)
        }
    }

    fun deleteExercise(exercise: Exercise) {
        viewModelScope.launch {
            exerciseRepository.deleteExercise(exercise)
        }
    }

    fun deleteExerciseById(exerciseId: Int) {
        viewModelScope.launch {
            exerciseRepository.deleteExerciseById(exerciseId)
        }
    }

    fun toggleFavorite(exerciseId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            exerciseRepository.toggleFavorite(exerciseId, isFavorite)
        }
    }

    // === RÉINITIALISATION ===
    fun resetToDefaultExercises(defaultExercises: List<Exercise>) {
        viewModelScope.launch {
            exerciseRepository.resetToDefaultExercises(defaultExercises)
        }
    }
}