package com.soukouboy.gorillaworkout.data.repository

import androidx.lifecycle.LiveData
import com.soukouboy.gorillaworkout.data.database.UserProfileDao
import com.soukouboy.gorillaworkout.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val userProfileDao: UserProfileDao) {
    
    val userProfile: LiveData<UserProfile> = userProfileDao.getUserProfile()
    
    suspend fun insertUserProfile(userProfile: UserProfile) {
        withContext(Dispatchers.IO) {
            userProfileDao.insertUserProfile(userProfile)
        }
    }
    
    suspend fun updateUserProfile(userProfile: UserProfile) {
        withContext(Dispatchers.IO) {
            userProfileDao.updateUserProfile(userProfile)
        }
    }
    
    suspend fun updateUserName(name: String) {
        withContext(Dispatchers.IO) {
            userProfileDao.updateUserName(name)
        }
    }
    
    suspend fun updateWeeklyGoal(weeklyGoal: Int) {
        withContext(Dispatchers.IO) {
            userProfileDao.updateWeeklyGoal(weeklyGoal)
        }
    }
    
    suspend fun updateFitnessGoal(fitnessGoal: FitnessGoal) {
        withContext(Dispatchers.IO) {
            userProfileDao.updateFitnessGoal(fitnessGoal.name)
        }
    }
    
    suspend fun getUserProfileSync(): UserProfile? {
        return withContext(Dispatchers.IO) {
            userProfileDao.getUserProfileSync()
        }
    }
    
    suspend fun resetUserData() {
        withContext(Dispatchers.IO) {
            userProfileDao.deleteAllProfiles()
            
            // Créer un nouveau profil par défaut
            val defaultProfile = UserProfile(
                name = "Utilisateur",
                age = 25,
                weight = 70.0,
                height = 175.0,
                gender = Gender.OTHER,
                fitnessGoal = FitnessGoal.GENERAL_FITNESS,
                experienceLevel = DifficultyLevel.BEGINNER,
                weeklyGoal = 3
            )
            userProfileDao.insertUserProfile(defaultProfile)
        }
    }
}