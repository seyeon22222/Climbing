package com.project.climbing.presentation.record.add

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.climbing.domain.model.Difficulty
import com.project.climbing.domain.model.Record
import com.project.climbing.domain.repository.GymRepository
import com.project.climbing.domain.repository.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RecordAddViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val gymRepository: GymRepository,
        private val recordRepository: RecordRepository,
    ) : ViewModel() {
        private val gymId: String = checkNotNull(savedStateHandle["gymId"])

        private val _uiState = MutableStateFlow(RecordAddUiState())
        val uiState: StateFlow<RecordAddUiState> = _uiState.asStateFlow()

        init {
            loadGymInfo()
        }

        private fun loadGymInfo() {
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true) }
                val gym = gymRepository.getGymById(gymId)
                if (gym != null) {
                    _uiState.update { it.copy(isLoading = false, gym = gym) }
                } else {
                    _uiState.update { it.copy(isLoading = false, error = "암장 정보를 찾을 수 없습니다.") }
                }
            }
        }

        fun onDifficultySelected(difficulty: Difficulty) {
            _uiState.update { it.copy(selectedDifficulty = difficulty) }
        }

        fun onMemoChanged(memo: String) {
            _uiState.update { it.copy(memo = memo) }
        }

        fun saveRecord() {
            val currentState = _uiState.value
            val gym = currentState.gym
            val difficulty = currentState.selectedDifficulty

            if (gym == null || difficulty == null) return

            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true) }
                @Suppress("TooGenericExceptionCaught")
                try {
                    val newRecord =
                        Record(
                            id = UUID.randomUUID().toString(),
                            // 임시 사용자 ID
                            userId = "user1",
                            gymId = gym.id,
                            gymName = gym.name,
                            date = LocalDateTime.now(),
                            difficulty = difficulty,
                            memo = currentState.memo,
                        )
                    recordRepository.addRecord(newRecord)
                    _uiState.update { it.copy(isLoading = false, isSaved = true) }
                } catch (e: Exception) {
                    _uiState.update { it.copy(isLoading = false, error = e.message) }
                }
            }
        }
    }
