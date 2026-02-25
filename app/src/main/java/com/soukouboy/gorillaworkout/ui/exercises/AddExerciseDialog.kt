package com.soukouboy.gorillaworkout.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.soukouboy.gorillaworkout.data.model.Exercise
import com.soukouboy.gorillaworkout.data.model.ExerciseCategory
import com.soukouboy.gorillaworkout.data.model.DifficultyLevel
import com.soukouboy.gorillaworkout.databinding.DialogAddExerciseBinding
import kotlinx.coroutines.launch

class AddExerciseDialog(
    private val onExerciseAdded: (Exercise) -> Unit
) : DialogFragment() {

    private var _binding: DialogAddExerciseBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogAddExerciseBinding.inflate(LayoutInflater.from(context))

        setupDropdowns()
        setupButtons()

        return AlertDialog.Builder(requireContext())
            .setTitle("Nouvel Exercice")
            .setView(binding.root)
            .create()
    }

    private fun setupDropdowns() {
        // Catégories
        binding.categoryDropdown.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                ExerciseCategory.values().map { it.name }
            )
        )
        binding.categoryDropdown.setText(ExerciseCategory.STRENGTH.name, false)

        // Difficultés
        binding.difficultyDropdown.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                DifficultyLevel.values().map { it.name }
            )
        )
        binding.difficultyDropdown.setText(DifficultyLevel.BEGINNER.name, false)
    }

    private fun setupButtons() {
        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        binding.saveButton.setOnClickListener {
            lifecycleScope.launch {
                saveExercise()
            }
        }
    }

    private suspend fun saveExercise() {
        try {
            val name = binding.exerciseNameInput.text.toString().trim()
            if (name.isBlank()) {
                Toast.makeText(context, "Nom de l'exercice requis", Toast.LENGTH_SHORT).show()
                return
            }

            val exercise = Exercise(
                name = name,
                category = ExerciseCategory.valueOf(binding.categoryDropdown.text.toString()),
                muscleGroup = binding.muscleGroupInput.text.toString(),
                description = binding.descriptionInput.text.toString(),
                difficulty = DifficultyLevel.valueOf(binding.difficultyDropdown.text.toString()),
                equipment = binding.equipmentInput.text.toString(),
                isFavorite = false
            )

            onExerciseAdded(exercise)
            dismiss()
        } catch (e: Exception) {
            Log.e("AddExerciseDialog", "Erreur sauvegarde", e)
            Toast.makeText(context, "Erreur: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}