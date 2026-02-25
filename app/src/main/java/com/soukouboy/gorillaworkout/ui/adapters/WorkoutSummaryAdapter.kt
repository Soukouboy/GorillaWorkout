package com.soukouboy.gorillaworkout.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.soukouboy.gorillaworkout.databinding.ItemSummaryExerciseBinding



class WorkoutSummaryAdapter : ListAdapter<SummaryExercise, WorkoutSummaryAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSummaryExerciseBinding.inflate(
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
        private val binding: ItemSummaryExerciseBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SummaryExercise) {
            binding.apply {
                exerciseNameText.text = item.exerciseName
                setsRepsText.text = "${item.sets} séries × ${item.reps} répétitions"
                weightText.text = if (item.weight > 0) "${item.weight} kg" else "Poids du corps"
                restTimeText.text = "Repos: ${item.restTime}s"
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<SummaryExercise>() {
        override fun areItemsTheSame(oldItem: SummaryExercise, newItem: SummaryExercise) =
            oldItem.exerciseName == newItem.exerciseName

        override fun areContentsTheSame(oldItem: SummaryExercise, newItem: SummaryExercise) =
            oldItem == newItem
    }
}