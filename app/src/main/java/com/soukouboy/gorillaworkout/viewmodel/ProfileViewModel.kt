package com.soukouboy.gorillaworkout.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soukouboy.gorillaworkout.data.repository.UserRepository
import com.soukouboy.gorillaworkout.data.model.UserProfile
import com.soukouboy.gorillaworkout.data.model.Gender
import com.soukouboy.gorillaworkout.data.model.FitnessGoal
import com.soukouboy.gorillaworkout.data.model.DifficultyLevel
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    val userProfile: LiveData<UserProfile> = userRepository.userProfile

    private val _weight = MutableLiveData<Double>()
    private val _height = MutableLiveData<Double>()
    private val _bmi = MediatorLiveData<Pair<Double, String>>()

    val bmi: LiveData<Pair<Double, String>> = _bmi

    init {
        _bmi.addSource(_weight) { recalculateBMI(it, _height.value) }
        _bmi.addSource(_height) { recalculateBMI(_weight.value, it) }
    }

    fun loadProfile() {
        // Le profil est observé automatiquement via LiveData
        // On initialise les valeurs de poids/taille pour le calcul BMI
        userProfile.value?.let { profile ->
            _weight.value = profile.weight
            _height.value = profile.height
        }
    }

    fun updateWeightHeight(weight: Double, height: Double) {
        _weight.value = weight
        _height.value = height
    }

    fun saveProfile(
        name: String,
        age: Int,
        weight: Double,
        height: Double,
        gender: Gender,
        fitnessGoal: FitnessGoal,
        experienceLevel: DifficultyLevel,
        weeklyGoal: Int
    ) {
        viewModelScope.launch {
            val profile = UserProfile(
                id = 1,
                name = name,
                age = age,
                weight = weight,
                height = height,
                gender = gender,
                fitnessGoal = fitnessGoal,
                experienceLevel = experienceLevel,
                weeklyGoal = weeklyGoal
            )
            userRepository.insertUserProfile(profile)
        }
    }

    private fun recalculateBMI(weight: Double?, height: Double?) {
        if (weight != null && height != null && weight > 0 && height > 0) {
            val bmiValue = weight / ((height / 100) * (height / 100))
            val category = when {
                bmiValue < 18.5 -> "Sous-poids"
                bmiValue < 25 -> "Normal"
                bmiValue < 30 -> "Surpoids"
                else -> "Obésité"
            }
            _bmi.value = Pair(bmiValue, category)
        }
    }
}