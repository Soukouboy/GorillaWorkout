package com.soukouboy.gorillaworkout.ui.workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.soukouboy.gorillaworkout.data.database.FitnessDatabase
import com.soukouboy.gorillaworkout.data.repository.ExerciseRepository
import com.soukouboy.gorillaworkout.data.repository.UserRepository
import com.soukouboy.gorillaworkout.data.repository.WorkoutRepository
import com.soukouboy.gorillaworkout.viewmodel.ViewModelFactory
import com.soukouboy.gorillaworkout.viewmodel.WorkoutViewModel

class WorkoutFragment : Fragment() {

    private lateinit var viewModel: WorkoutViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialiser le ViewModel
        val database = FitnessDatabase.getDatabase(requireContext())
        val workoutRepository = WorkoutRepository(database.workoutDao())
        val exerciseRepository = ExerciseRepository(database.exerciseDao())
        val userRepository = UserRepository(database.userProfileDao())

        val viewModelFactory = ViewModelFactory(workoutRepository, exerciseRepository, userRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[WorkoutViewModel::class.java]

        // Note: Dans une implémentation complète, on retournerait le binding
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}