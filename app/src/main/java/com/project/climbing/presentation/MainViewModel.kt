package com.project.climbing.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.climbing.domain.repository.AuthRepository
import com.project.climbing.domain.repository.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) : ViewModel() {
        val authState: StateFlow<AuthState> = authRepository.authState as StateFlow<AuthState>

        init {
            checkSession()
        }

        private fun checkSession() {
            viewModelScope.launch {
                authRepository.isLoggedIn()
            }
        }
    }
