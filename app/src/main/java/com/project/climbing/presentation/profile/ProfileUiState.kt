package com.project.climbing.presentation.profile

import com.project.climbing.domain.model.User

data class ProfileUiState(
    val user: User? = null,
    val isEditing: Boolean = false,
    val nicknameInput: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
)
