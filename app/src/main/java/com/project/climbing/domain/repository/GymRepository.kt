package com.project.climbing.domain.repository

import com.project.climbing.domain.model.Gym
import kotlinx.coroutines.flow.Flow

interface GymRepository {
    fun getGyms(): Flow<List<Gym>>

    suspend fun getGymById(id: String): Gym?
}
