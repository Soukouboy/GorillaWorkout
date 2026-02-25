package com.soukouboy.gorillaworkout.ui.workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.soukouboy.gorillaworkout.databinding.FragmentWorkoutSummaryBinding
import com.soukouboy.gorillaworkout.ui.adapters.WorkoutSummaryAdapter
import com.soukouboy.gorillaworkout.viewmodel.WorkoutSummaryViewModel
import com.soukouboy.gorillaworkout.data.database.FitnessDatabase
import com.soukouboy.gorillaworkout.data.repository.WorkoutRepository
import com.soukouboy.gorillaworkout.data.repository.ExerciseRepository
import androidx.lifecycle.ViewModelProvider

class WorkoutSummaryFragment : Fragment() {

    private var _binding: FragmentWorkoutSummaryBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: WorkoutSummaryViewModel
    private lateinit var adapter: WorkoutSummaryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Initialisation des repositories
        val database = FitnessDatabase.getDatabase(requireContext())
        val workoutRepository = WorkoutRepository(database.workoutDao())
        val exerciseRepository = ExerciseRepository(database.exerciseDao())

        // Factory inline pour le ViewModel
        val viewModelFactory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(WorkoutSummaryViewModel::class.java)) {
                    return WorkoutSummaryViewModel(workoutRepository, exerciseRepository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }

        viewModel = ViewModelProvider(this, viewModelFactory)[WorkoutSummaryViewModel::class.java]

        // Inflation du binding
        _binding = FragmentWorkoutSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configuration RecyclerView
        setupRecyclerView()

        // Observer les données du ViewModel
        viewModel.workoutSummary.observe(viewLifecycleOwner) { summary ->
            binding.workoutNameText.text = summary.name
            binding.totalDurationText.text = "${summary.duration / 60} min"
            binding.totalCaloriesText.text = "${summary.calories} kcal"

            // Mettre à jour la liste
            adapter.submitList(summary.exercises)
        }

        // Charger le workout avec ID = 1 (à adapter selon vos besoins)
        viewModel.loadWorkoutSummary(1)

        // Bouton Terminer
        binding.finishButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupRecyclerView() {
        adapter = WorkoutSummaryAdapter()
        binding.exercisesRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.exercisesRecyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}