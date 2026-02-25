package com.soukouboy.gorillaworkout.ui.exercises

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.soukouboy.gorillaworkout.data.model.Exercise
import com.soukouboy.gorillaworkout.databinding.DialogExerciseDetailsBinding

class ExerciseDetailsDialog(
    private val exercise: Exercise
) : DialogFragment() {




    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogExerciseDetailsBinding.inflate(layoutInflater)

        // Remplir les champs
        binding.exerciseNameText.text = exercise.name
        binding.descriptionText.text = exercise.description
        binding.muscleGroupText.text = "Groupes musculaires: ${exercise.muscleGroup}"
        binding.equipmentText.text = "Équipement: ${exercise.equipment}"
        binding.difficultyText.text = "Niveau: ${exercise.difficulty.name}"

        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setPositiveButton("Fermer", null)
            .create()
    }
}