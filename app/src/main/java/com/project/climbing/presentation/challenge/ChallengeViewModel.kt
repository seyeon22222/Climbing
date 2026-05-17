package com.project.climbing.presentation.challenge

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ChallengeViewModel
    @Inject
    constructor() : ViewModel() {
        private val _uiState = MutableStateFlow(ChallengeUiState())
        val uiState: StateFlow<ChallengeUiState> = _uiState.asStateFlow()
    }
