package com.soukouboy.gorillaworkout.ui.exercises

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.soukouboy.gorillaworkout.R
import com.soukouboy.gorillaworkout.data.model.Exercise
import com.soukouboy.gorillaworkout.data.model.DifficultyLevel
import com.soukouboy.gorillaworkout.data.model.ExerciseCategory
import com.soukouboy.gorillaworkout.databinding.ItemExerciseBinding

class ExerciseAdapter(
    private val onExerciseClick: (Exercise) -> Unit,
    private val onFavoriteClick: (Exercise) -> Unit
) : ListAdapter<Exercise, ExerciseAdapter.ExerciseViewHolder>(ExerciseDiffCallback()) {

    // CACHE pour éviter de recharger les drawables à chaque bind
    private val drawableCache = mutableMapOf<Int, Drawable?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ItemExerciseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ExerciseViewHolder(
        private val binding: ItemExerciseBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(exercise: Exercise) {
            binding.apply {
                exerciseName.text = exercise.name
                muscleGroup.text = exercise.muscleGroup

                // Sécurisé : ne jamais échouer même si la ressource manque
                difficultyBadge.text = exercise.difficulty.getDisplayName()

                // Icône avec fallback
                categoryIcon.setImageResource(exercise.category.getIconRes())

                // Background avec cache
                val bgRes = exercise.category.getBackgroundRes()
                categoryIcon.background = drawableCache.getOrPut(bgRes) {
                    ContextCompat.getDrawable(itemView.context, bgRes)
                }

                // Favori
                favoriteButton.setImageResource(
                    if (exercise.isFavorite) R.drawable.ic_fitness_center
                    else R.drawable.ic_favorite_border
                )

                // Listeners
                root.setOnClickListener { onExerciseClick(exercise) }
                favoriteButton.setOnClickListener { onFavoriteClick(exercise) }
            }
        }
    }

    // Extensions sécurisées
    private fun DifficultyLevel.getDisplayName(): String = when (this) {
        DifficultyLevel.BEGINNER -> "Débutant"
        DifficultyLevel.INTERMEDIATE -> "Intermédiaire"
        DifficultyLevel.ADVANCED -> "Avancé"
    }

    private fun ExerciseCategory.getIconRes(): Int = when (this) {
        ExerciseCategory.STRENGTH -> R.drawable.ic_fitness_center
        ExerciseCategory.CARDIO -> R.drawable.ic_timer
        ExerciseCategory.FLEXIBILITY -> R.drawable.ic_favorite
        ExerciseCategory.BALANCE -> R.drawable.ic_check
        ExerciseCategory.SPORTS -> R.drawable.ic_favorite_border
    }

    private fun ExerciseCategory.getBackgroundRes(): Int = when (this) {
        ExerciseCategory.STRENGTH -> R.drawable.circle_background_strength
        ExerciseCategory.CARDIO -> R.drawable.circle_background_cardio
        else -> R.drawable.circle_background_primary
    }
}

// DiffCallback séparé pour plus de clarté
class ExerciseDiffCallback : DiffUtil.ItemCallback<Exercise>() {
    override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise) = oldItem == newItem
}