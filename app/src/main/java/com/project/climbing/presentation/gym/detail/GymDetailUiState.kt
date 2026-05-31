package com.project.climbing.presentation.gym.detail

import com.project.climbing.domain.model.Gym

data class GymDetailUiState(
    val isLoading: Boolean = false,
    val gym: Gym? = null,
    val error: String? = null,
)
