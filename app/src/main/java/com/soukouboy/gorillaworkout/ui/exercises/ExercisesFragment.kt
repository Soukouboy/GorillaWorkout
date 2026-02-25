package com.soukouboy.gorillaworkout.ui.exercises

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.soukouboy.gorillaworkout.databinding.FragmentExercisesBinding
import com.soukouboy.gorillaworkout.ui.exercises.ExerciseAdapter
import com.soukouboy.gorillaworkout.ui.dialog.AddExerciseDialog
import com.soukouboy.gorillaworkout.viewmodel.ExerciseViewModel
import com.soukouboy.gorillaworkout.data.database.FitnessDatabase
import com.soukouboy.gorillaworkout.data.repository.ExerciseRepository
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.util.Log

class ExercisesFragment : Fragment() {

    private var _binding: FragmentExercisesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ExerciseViewModel
    private lateinit var adapter: ExerciseAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Initialisation ViewModel
        val database = FitnessDatabase.getDatabase(requireContext())
        val exerciseRepository = ExerciseRepository(database.exerciseDao())

        val viewModelFactory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(ExerciseViewModel::class.java)) {
                    return ExerciseViewModel(exerciseRepository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }

        viewModel = ViewModelProvider(this, viewModelFactory)[ExerciseViewModel::class.java]

        _binding = FragmentExercisesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupFab()

        // Observer les exercices
        viewModel.allExercises.observe(viewLifecycleOwner) { exercises ->
            adapter.submitList(exercises)
        }
    }

    private fun setupRecyclerView() {
        adapter = ExerciseAdapter(
            onExerciseClick = { exercise ->
                // Ouvrir détails
                Toast.makeText(context, "Exercice: ${exercise.name}", Toast.LENGTH_SHORT).show()
            },
            onFavoriteClick = { exercise ->
                lifecycleScope.launch {
                    viewModel.toggleFavorite(exercise.id, !exercise.isFavorite)
                }
            }
        )

        binding.exercisesRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.exercisesRecyclerView.adapter = adapter
    }

    private fun setupFab() {
        binding.addExerciseFab.setOnClickListener {
            Log.d("ExercisesFragment", "FAB cliqué - ouverture dialog")

            val dialog = AddExerciseDialog { newExercise ->
                Log.d("ExercisesFragment", "Nouvel exercice reçu: ${newExercise.name}")

                lifecycleScope.launch {
                    try {
                        viewModel.addExercise(newExercise)
                        Toast.makeText(context, "Exercice ajouté", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Log.e("ExercisesFragment", "Erreur ajout exercice", e)
                        Toast.makeText(context, "Erreur: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }

            dialog.show(parentFragmentManager, "AddExerciseDialog")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}