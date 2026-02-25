package com.soukouboy.gorillaworkout.ui.dialog

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.appcompat.app.AlertDialog
import com.soukouboy.gorillaworkout.databinding.DialogExerciseDetailsBinding
import com.soukouboy.gorillaworkout.data.model.Exercise
import com.soukouboy.gorillaworkout.data.model.DifficultyLevel
import com.soukouboy.gorillaworkout.R
import com.soukouboy.gorillaworkout.ui.exercises.ExerciseDetailsDialog

class ExerciseDetailsDialog(
    private val exercise: Exercise
) : DialogFragment() {

    companion object {
        fun newInstance(exercise: Exercise): com.soukouboy.gorillaworkout.ui.exercises.ExerciseDetailsDialog {
            return ExerciseDetailsDialog(exercise)
        }
    }

    private var _binding: DialogExerciseDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogExerciseDetailsBinding.inflate(layoutInflater)

        // Configuration du texte
        binding.exerciseNameText.text = exercise.name
        binding.muscleGroupText.text = "Groupes musculaires : ${exercise.muscleGroup}"
        binding.equipmentText.text = "Équipement : ${exercise.equipment}"
        binding.difficultyText.text = "Niveau : ${exercise.difficulty.name}"
        binding.descriptionText.text = exercise.description

        // Style personnalisé selon la difficulté
        styleDifficultyText(exercise.difficulty)

        // Style optionnel pour le titre
        binding.exerciseNameText.setTextColor(requireContext().getColor(R.color.primary))

        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setPositiveButton("Fermer") { _, _ -> dismiss() }
            .create()
    }

    /**
     * Applique les couleurs et style selon le niveau de difficulté
     * Configurez vos couleurs dans res/values/colors.xml
     */
    private fun styleDifficultyText(difficulty: DifficultyLevel) {
        val context = requireContext()

        val (colorResId, backgroundResId) = when (difficulty) {
            DifficultyLevel.BEGINNER -> Pair(
                R.color.difficulty_beginner_text,      // Vert
                R.drawable.bg_difficulty_beginner       // Fond vert clair
            )
            DifficultyLevel.INTERMEDIATE -> Pair(
                R.color.difficulty_intermediate_text,  // Orange
                R.drawable.bg_difficulty_intermediate   // Fond orange clair
            )
            DifficultyLevel.ADVANCED -> Pair(
                R.color.difficulty_advanced_text,      // Rouge
                R.drawable.bg_difficulty_advanced       // Fond rouge clair
            )
        }

        binding.difficultyText.apply {
            setTextColor(context.getColor(colorResId))
            background = context.getDrawable(backgroundResId)
            setPadding(16, 8, 16, 8) // padding en pixels
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Prévient les fuites mémoire
    }
}