package com.soukouboy.gorillaworkout.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.soukouboy.gorillaworkout.data.model.Exercise
import com.soukouboy.gorillaworkout.data.model.ExerciseCategory

@Dao
interface ExerciseDao {
    
    @Query("SELECT * FROM exercises ORDER BY name ASC")
    fun getAllExercises(): LiveData<List<Exercise>>
    
    @Query("SELECT * FROM exercises WHERE category = :category ORDER BY name ASC")
    fun getExercisesByCategory(category: ExerciseCategory): LiveData<List<Exercise>>
    
    @Query("SELECT * FROM exercises WHERE isFavorite = 1 ORDER BY name ASC")
    fun getFavoriteExercises(): LiveData<List<Exercise>>
    
    @Query("SELECT * FROM exercises WHERE name LIKE '%' || :searchQuery || '%' ORDER BY name ASC")
    fun searchExercises(searchQuery: String): LiveData<List<Exercise>>
    
    @Query("SELECT * FROM exercises WHERE id = :exerciseId")
    suspend fun getExerciseById(exerciseId: Int): Exercise?



    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: Exercise): Long
    
    @Update
    suspend fun updateExercise(exercise: Exercise)
    
    @Delete
    suspend fun deleteExercise(exercise: Exercise)
    
    @Query("DELETE FROM exercises WHERE id = :exerciseId")
    suspend fun deleteExerciseById(exerciseId: Int)
    
    @Query("UPDATE exercises SET isFavorite = :isFavorite WHERE id = :exerciseId")
    suspend fun toggleFavorite(exerciseId: Int, isFavorite: Boolean)
    
    @Query("SELECT COUNT(*) FROM exercises")
    fun getExerciseCount(): LiveData<Int>

 }