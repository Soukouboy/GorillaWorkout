package com.soukouboy.gorillaworkout.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.soukouboy.gorillaworkout.data.model.Workout
import com.soukouboy.gorillaworkout.data.model.WorkoutExercise
import java.util.*

@Dao
interface WorkoutDao {

    // === OPERATIONS SUR WORKOUTS ===

    @Query("SELECT * FROM workouts ORDER BY date DESC")
    fun getAllWorkouts(): LiveData<List<Workout>>

    @Query("SELECT * FROM workouts WHERE isCompleted = 1 ORDER BY date DESC")
    fun getCompletedWorkouts(): LiveData<List<Workout>>

    @Query("SELECT * FROM workouts WHERE isCompleted = 1 ORDER BY date DESC LIMIT 10")
    fun getRecentWorkouts(): LiveData<List<Workout>>

    @Query("SELECT * FROM workouts WHERE id = :workoutId")
    suspend fun getWorkoutById(workoutId: Int): Workout?

    @Query("SELECT * FROM workout_exercises WHERE workoutId = :workoutId")
    suspend fun getWorkoutExercisesByWorkoutId(workoutId: Int): List<WorkoutExercise>

    @Query("SELECT * FROM workouts WHERE isCompleted = 1 AND date >= :startDate ORDER BY date DESC")
    fun getWorkoutsSince(startDate: Long): LiveData<List<Workout>>

    @Query("SELECT * FROM workouts WHERE isCompleted = 1 AND date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getWorkoutsBetween(startDate: Long, endDate: Long): LiveData<List<Workout>>


    @Query("SELECT SUM(duration) FROM workouts WHERE isCompleted = 1")
    fun getTotalWorkoutTime(): LiveData<Int>

    @Insert
    suspend fun insertWorkout(workout: Workout): Long

    @Update
    suspend fun updateWorkout(workout: Workout)

    @Delete
    suspend fun deleteWorkout(workout: Workout)

    // === STATISTIQUES ===

    @Query("SELECT COUNT(*) FROM workouts WHERE isCompleted = 1")
    fun getTotalCompletedWorkouts(): LiveData<Int>

    @Query("SELECT SUM(duration) FROM workouts WHERE isCompleted = 1")
    fun getTotalWorkoutTimes(): LiveData<Int>

    @Query("SELECT SUM(totalCalories) FROM workouts WHERE isCompleted = 1")
    fun getTotalCaloriesBurned(): LiveData<Int>

    @Query("SELECT AVG(duration) FROM workouts WHERE isCompleted = 1")
    fun getAverageWorkoutDuration(): LiveData<Double>

    @Query("SELECT MAX(duration) FROM workouts WHERE isCompleted = 1")
    fun getLongestWorkoutDuration(): LiveData<Int>

    // === OPERATIONS SUR WORKOUT_EXERCISES ===

    @Query("SELECT * FROM workout_exercises WHERE workoutId = :workoutId ORDER BY `order` ASC")
    fun getWorkoutExercises(workoutId: Int): LiveData<List<WorkoutExercise>>

    @Query("SELECT * FROM workout_exercises WHERE workoutId = :workoutId AND exerciseId = :exerciseId")
    suspend fun getWorkoutExercise(workoutId: Int, exerciseId: Int): WorkoutExercise?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutExercise(workoutExercise: WorkoutExercise)

    @Update
    suspend fun updateWorkoutExercise(workoutExercise: WorkoutExercise)

    @Delete
    suspend fun deleteWorkoutExercise(workoutExercise: WorkoutExercise)

    @Query("DELETE FROM workout_exercises WHERE workoutId = :workoutId")
    suspend fun deleteAllWorkoutExercises(workoutId: Int)

    // === JOURS CONSÉCUTIFS ===

    @Query("SELECT date FROM workouts WHERE isCompleted = 1 ORDER BY date DESC LIMIT 1")
    suspend fun getLastWorkoutDate(): Long?

    @Query("SELECT date FROM workouts WHERE isCompleted = 1 ORDER BY date ASC")
    suspend fun getAllWorkoutDates(): List<Long>



}