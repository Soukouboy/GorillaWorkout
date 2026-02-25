package com.soukouboy.gorillaworkout.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.soukouboy.gorillaworkout.databinding.FragmentProfileBinding
import com.soukouboy.gorillaworkout.data.database.FitnessDatabase
import com.soukouboy.gorillaworkout.data.repository.UserRepository
import com.soukouboy.gorillaworkout.viewmodel.ProfileViewModel
import com.soukouboy.gorillaworkout.data.model.Gender
import com.soukouboy.gorillaworkout.data.model.FitnessGoal
import com.soukouboy.gorillaworkout.data.model.DifficultyLevel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Initialisation ViewModel
        val database = FitnessDatabase.getDatabase(requireContext())
        val userRepository = UserRepository(database.userProfileDao())

        val viewModelFactory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
                    return ProfileViewModel(userRepository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }

        viewModel = ViewModelProvider(this, viewModelFactory)[ProfileViewModel::class.java]

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinners()
        setupAutoComplete()
        setupSlider()
        observeViewModel()
        setupButtons()

        // Charger le profil
        viewModel.loadProfile()
    }

    private fun setupSpinners() {
        // Genre
        val genders = Gender.values().map { it.name }
        binding.genderSpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            genders
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
    }

    private fun setupAutoComplete() {
        // Objectif Fitness
        val goals = FitnessGoal.values().map { it.name }
        val goalAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            goals
        )
        binding.fitnessGoalDropdown.setAdapter(goalAdapter)

        // Niveau d'expérience
        val levels = DifficultyLevel.values().map { it.name }
        val levelAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            levels
        )
        binding.experienceDropdown.setAdapter(levelAdapter)

        // Temps de repos par défaut
        val restTimes = listOf("30s", "45s", "60s", "90s", "120s")
        val restAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            restTimes
        )
        binding.restTimeDropdown.setAdapter(restAdapter)
        binding.restTimeDropdown.setText("60s", false)
    }

    private fun setupSlider() {
        binding.weeklyGoalSlider.value = 3f
        binding.weeklyGoalSlider.addOnChangeListener { _, value, _ ->
            binding.weeklyGoalValue.text = value.toInt().toString()
        }
    }

    private fun observeViewModel() {
        viewModel.userProfile.observe(viewLifecycleOwner) { profile ->
            profile?.let {
                // Mettre à jour les vues
                binding.nameInput.setText(it.name)
                binding.userName.text = it.name.ifEmpty { "Utilisateur" }
                binding.ageInput.setText(it.age.toString())
                binding.weightInput.setText(it.weight.toString())
                binding.heightInput.setText(it.height.toString())

                // Spinners
                binding.genderSpinner.setSelection(Gender.valueOf(it.gender.name).ordinal)

                // AutoCompleteTextView
                binding.fitnessGoalDropdown.setText(it.fitnessGoal.name, false)
                binding.experienceDropdown.setText(it.experienceLevel.name, false)

                // Slider
                binding.weeklyGoalSlider.value = it.weeklyGoal.toFloat()
                binding.weeklyGoalValue.text = it.weeklyGoal.toString()

                // Objectif summary
                binding.fitnessGoalSummary.text = "Objectif : ${it.fitnessGoal.name}"
            }
        }

        viewModel.bmi.observe(viewLifecycleOwner) { (bmiValue, category) ->
            // Le BMI est calculé automatiquement quand poids/taille changent
        }
    }

    private fun setupButtons() {
        binding.saveButton.setOnClickListener {
            saveProfile()
        }

        binding.resetExercisesButton.setOnClickListener {
            // À implémenter : réinitialiser les exercices
            Toast.makeText(context, "Fonctionnalité à venir", Toast.LENGTH_SHORT).show()
        }

        binding.exportDataButton.setOnClickListener {
            // À implémenter : exporter les données
            Toast.makeText(context, "Fonctionnalité à venir", Toast.LENGTH_SHORT).show()
        }

        binding.resetAllButton.setOnClickListener {
            // À implémenter : tout réinitialiser
            Toast.makeText(context, "Fonctionnalité à venir", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveProfile() {
        val name = binding.nameInput.text.toString()
        val age = binding.ageInput.text.toString().toIntOrNull() ?: 0
        val weight = binding.weightInput.text.toString().toDoubleOrNull() ?: 0.0
        val height = binding.heightInput.text.toString().toDoubleOrNull() ?: 0.0

        val gender = Gender.values()[binding.genderSpinner.selectedItemPosition]
        val fitnessGoal = FitnessGoal.valueOf(binding.fitnessGoalDropdown.text.toString())
        val experienceLevel = DifficultyLevel.valueOf(binding.experienceDropdown.text.toString())
        val weeklyGoal = binding.weeklyGoalSlider.value.toInt()

        // Validation
        if (name.isBlank()) {
            Toast.makeText(context, "Le nom ne peut pas être vide", Toast.LENGTH_SHORT).show()
            return
        }

        if (age <= 0 || age > 120) {
            Toast.makeText(context, "Âge invalide", Toast.LENGTH_SHORT).show()
            return
        }

        if (weight <= 0 || weight > 500) {
            Toast.makeText(context, "Poids invalide", Toast.LENGTH_SHORT).show()
            return
        }

        if (height <= 0 || height > 300) {
            Toast.makeText(context, "Taille invalide", Toast.LENGTH_SHORT).show()
            return
        }

        // Sauvegarder via ViewModel
        viewModel.saveProfile(name, age, weight, height, gender, fitnessGoal, experienceLevel, weeklyGoal)

        Toast.makeText(context, "Profil sauvegardé avec succès", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}