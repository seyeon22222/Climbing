package com.project.climbing.presentation.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.climbing.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(LoginUiState())
        val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

        fun onLoginClick(
            context: Context,
            onSuccess: () -> Unit,
        ) {
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true) }
                val result = authRepository.loginWithKakao(context)

                result.onSuccess {
                    _uiState.update { it.copy(isLoading = false, isLoggedIn = true) }
                    onSuccess()
                }.onFailure { error ->
                    _uiState.update { it.copy(isLoading = false, error = error.message) }
                }
            }
        }
    }
