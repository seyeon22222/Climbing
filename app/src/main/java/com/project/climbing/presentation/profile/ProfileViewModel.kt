package com.project.climbing.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.climbing.domain.repository.AuthRepository
import com.project.climbing.domain.repository.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(ProfileUiState())
        val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

        init {
            observeAuthState()
        }

        private fun observeAuthState() {
            viewModelScope.launch {
                authRepository.authState.collect { state ->
                    if (state is AuthState.Authenticated) {
                        _uiState.update {
                            it.copy(
                                user = state.user,
                                nicknameInput = state.user.nickname,
                            )
                        }
                    }
                }
            }
        }

        fun onEditClick() {
            _uiState.update { it.copy(isEditing = true) }
        }

        fun onNicknameChange(nickname: String) {
            _uiState.update { it.copy(nicknameInput = nickname) }
        }

        fun onSaveClick() {
            // TODO: DB 연동 시 실제 저장 로직 구현
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true) }
                // 임시로 상태만 업데이트
                val currentUser = _uiState.value.user
                if (currentUser != null) {
                    val updatedUser = currentUser.copy(nickname = _uiState.value.nicknameInput)
                    _uiState.update {
                        it.copy(
                            user = updatedUser,
                            isEditing = false,
                            isLoading = false,
                        )
                    }
                }
            }
        }

        fun onCancelClick() {
            _uiState.update {
                it.copy(
                    isEditing = false,
                    nicknameInput = it.user?.nickname ?: "",
                )
            }
        }

        fun onLogoutClick() {
            viewModelScope.launch {
                authRepository.logout()
            }
        }

        fun onImageEditClick() {
            // TODO: 이미지 선택기 호출 로직
        }
    }
