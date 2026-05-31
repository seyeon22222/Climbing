package com.project.climbing.presentation.record.add

import com.project.climbing.domain.model.Difficulty
import com.project.climbing.domain.model.Gym

data class RecordAddUiState(
    val isLoading: Boolean = false,
    val gym: Gym? = null,
    val selectedDifficulty: Difficulty? = null,
    val memo: String = "",
    val isSaved: Boolean = false,
    val error: String? = null,
)
