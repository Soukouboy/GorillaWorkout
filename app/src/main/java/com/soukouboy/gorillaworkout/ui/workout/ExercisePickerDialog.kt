package com.soukouboy.gorillaworkout.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.soukouboy.gorillaworkout.data.model.Exercise
import com.soukouboy.gorillaworkout.databinding.DialogExercisePickerBinding
import com.soukouboy.gorillaworkout.ui.adapters.ExercisePickerAdapter
import com.soukouboy.gorillaworkout.viewmodel.ExerciseViewModel
import com.soukouboy.gorillaworkout.data.database.FitnessDatabase
import com.soukouboy.gorillaworkout.data.repository.ExerciseRepository

class ExercisePickerDialog(
    private val onExerciseSelected: (Exercise) -> Unit
) : DialogFragment() {

    private var _binding: DialogExercisePickerBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ExerciseViewModel
    private lateinit var adapter: ExercisePickerAdapter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogExercisePickerBinding.inflate(layoutInflater)

        setupViewModel()
        setupRecyclerView()
        setupSearch()

        return AlertDialog.Builder(requireContext())
            .setTitle("Sélectionner un exercice")
            .setView(binding.root)
            .create()
    }

    private fun setupViewModel() {
        val database = FitnessDatabase.getDatabase(requireContext())
        val exerciseRepository = ExerciseRepository(database.exerciseDao())
        val viewModelFactory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return ExerciseViewModel(exerciseRepository) as T
            }
        }
        viewModel = ViewModelProvider(this, viewModelFactory)[ExerciseViewModel::class.java]
    }

    private fun setupRecyclerView() {
        adapter = ExercisePickerAdapter { exercise ->
            onExerciseSelected(exercise)
            dismiss()
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        viewModel.allExercises.observe(this) { exercises ->
            adapter.submitList(exercises)
        }
    }

    private fun setupSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.setSearchQuery(newText ?: "")
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}