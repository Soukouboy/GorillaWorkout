package com.soukouboy.gorillaworkout.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.soukouboy.gorillaworkout.R
import com.soukouboy.gorillaworkout.data.database.FitnessDatabase
import com.soukouboy.gorillaworkout.data.repository.ExerciseRepository
import com.soukouboy.gorillaworkout.data.repository.UserRepository
import com.soukouboy.gorillaworkout.data.repository.WorkoutRepository
import com.soukouboy.gorillaworkout.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var viewModelFactory: ViewModelFactory



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialiser la base de données et les repositories
        val database = FitnessDatabase.getDatabase(this)
        val workoutRepository = WorkoutRepository(database.workoutDao())
        val exerciseRepository = ExerciseRepository(database.exerciseDao())
        val userRepository = UserRepository(database.userProfileDao())

        // Créer la ViewModelFactory
        viewModelFactory = ViewModelFactory(workoutRepository, exerciseRepository, userRepository)

        // Configurer la navigation
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Configurer la Bottom Navigation
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setupWithNavController(navController)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Configurer l'ActionBar
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.exercisesFragment,
                R.id.workoutFragment,
                R.id.statsFragment,
                R.id.profileFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    companion object {
        lateinit var instance: MainActivity
            private set
    }

    init {
        instance = this
    }
}