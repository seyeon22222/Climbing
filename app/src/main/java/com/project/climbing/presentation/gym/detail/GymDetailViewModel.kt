package com.project.climbing.presentation.gym.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.project.climbing.domain.repository.GymRepository
import com.project.climbing.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GymDetailViewModel
    @Inject
    constructor(
        private val gymRepository: GymRepository,
        savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        private val gymDetail = savedStateHandle.toRoute<Route.GymDetail>()
        private val gymId = gymDetail.gymId

        private val _uiState = MutableStateFlow(GymDetailUiState())
        val uiState: StateFlow<GymDetailUiState> = _uiState.asStateFlow()

        init {
            loadGymDetail()
        }

        private fun loadGymDetail() {
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true) }
                try {
                    val gym = gymRepository.getGymById(gymId)
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            gym = gym,
                        )
                    }
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = e.message,
                        )
                    }
                }
            }
        }
    }
