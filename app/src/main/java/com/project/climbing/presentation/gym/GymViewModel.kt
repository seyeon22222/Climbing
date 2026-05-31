package com.project.climbing.presentation.gym

import GymUiState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.climbing.domain.repository.GymRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GymViewModel
    @Inject
    constructor(
        private val gymRepository: GymRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(GymUiState())
        val uiState: StateFlow<GymUiState> = _uiState.asStateFlow()

        init {
            loadGyms()
        }

        private fun loadGyms() {
            viewModelScope.launch {
                gymRepository.getGyms()
                    .onStart { _uiState.update { it.copy(isLoading = true) } }
                    .catch { e -> _uiState.update { it.copy(isLoading = false, error = e.message) } }
                    .collect { gyms ->
                        _uiState.update { it.copy(isLoading = false, gyms = gyms) }
                    }
            }
        }
    }
