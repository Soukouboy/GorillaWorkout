package com.soukouboy.gorillaworkout.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.soukouboy.gorillaworkout.data.model.Exercise
import com.soukouboy.gorillaworkout.databinding.ItemExercisePickerBinding

class ExercisePickerAdapter(
    private val onExerciseSelected: (Exercise) -> Unit
) : ListAdapter<Exercise, ExercisePickerAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemExercisePickerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemExercisePickerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(exercise: Exercise) {
            binding.exerciseNameText.text = exercise.name
            binding.muscleGroupText.text = exercise.muscleGroup
            binding.difficultyText.text = exercise.difficulty.name

            binding.root.setOnClickListener {
                onExerciseSelected(exercise)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Exercise>() {
        override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise) = oldItem == newItem
    }
}

private fun ExercisePickerAdapter.ViewHolder.onExerciseSelected(exercise: Exercise) {}
