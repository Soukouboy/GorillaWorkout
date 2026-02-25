package com.soukouboy.gorillaworkout.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.soukouboy.gorillaworkout.data.model.UserProfile

@Dao
interface UserProfileDao {
    
    @Query("SELECT * FROM user_profile WHERE id = 1")
    fun getUserProfile(): LiveData<UserProfile>
    
    @Query("SELECT * FROM user_profile WHERE id = 1")
    suspend fun getUserProfileSync(): UserProfile?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProfile(userProfile: UserProfile)
    
    @Update
    suspend fun updateUserProfile(userProfile: UserProfile)
    
    @Query("UPDATE user_profile SET name = :name WHERE id = 1")
    suspend fun updateUserName(name: String)
    
    @Query("UPDATE user_profile SET weeklyGoal = :weeklyGoal WHERE id = 1")
    suspend fun updateWeeklyGoal(weeklyGoal: Int)
    
    @Query("UPDATE user_profile SET fitnessGoal = :fitnessGoal WHERE id = 1")
    suspend fun updateFitnessGoal(fitnessGoal: String)
    
    @Query("DELETE FROM user_profile")
    suspend fun deleteAllProfiles()
    
    @Query("SELECT COUNT(*) FROM user_profile")
    suspend fun getProfileCount(): Int
}