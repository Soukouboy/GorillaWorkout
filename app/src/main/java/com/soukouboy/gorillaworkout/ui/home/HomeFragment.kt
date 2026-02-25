package com.soukouboy.gorillaworkout.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.soukouboy.gorillaworkout.R
import com.soukouboy.gorillaworkout.data.database.FitnessDatabase
import com.soukouboy.gorillaworkout.data.repository.ExerciseRepository
import com.soukouboy.gorillaworkout.data.repository.UserRepository
import com.soukouboy.gorillaworkout.data.repository.WorkoutRepository
import com.soukouboy.gorillaworkout.databinding.FragmentHomeBinding
import com.soukouboy.gorillaworkout.viewmodel.HomeViewModel
import com.soukouboy.gorillaworkout.viewmodel.ViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialiser le ViewModel
        val database = FitnessDatabase.getDatabase(requireContext())
        val workoutRepository = WorkoutRepository(database.workoutDao())
        val exerciseRepository = ExerciseRepository(database.exerciseDao())
        val userRepository = UserRepository(database.userProfileDao())
        
        val viewModelFactory = ViewModelFactory(workoutRepository, exerciseRepository, userRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]

        setupObservers()
        setupClickListeners()
    }

    private fun setupObservers() {
        // Observer les statistiques hebdomadaires
        viewModel.weeklyWorkouts.observe(viewLifecycleOwner) { count ->
            binding.weeklyWorkoutsCount.text = count.toString()
        }

        viewModel.weeklyTime.observe(viewLifecycleOwner) { seconds ->
            binding.weeklyTimeValue.text = viewModel.formatDuration(seconds)
        }

        viewModel.weeklyCalories.observe(viewLifecycleOwner) { calories ->
            binding.weeklyCaloriesValue.text = calories.toString()
        }

        viewModel.currentStreak.observe(viewLifecycleOwner) { streak ->
            binding.currentStreakValue.text = streak.toString()
        }

        // Observer le dernier entraînement
        viewModel.lastWorkout.observe(viewLifecycleOwner) { workout ->
            if (workout != null) {
                val lastWorkoutInfo = """
                    ${workout.name}
                    ${workout.getFormattedDate()} • ${workout.getFormattedDuration()}
                """.trimIndent()
                binding.lastWorkoutInfo.text = lastWorkoutInfo
            } else {
                binding.lastWorkoutInfo.text = getString(R.string.home_no_last_workout)
            }
        }
    }

    private fun setupClickListeners() {
        binding.startWorkoutButton.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_workout)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}