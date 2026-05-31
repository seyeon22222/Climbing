package com.project.climbing.presentation.record

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.climbing.domain.repository.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RecordViewModel
    @Inject
    constructor(
        private val recordRepository: RecordRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(RecordUiState())
        val uiState: StateFlow<RecordUiState> = _uiState.asStateFlow()

        init {
            loadRecords()
        }

        private fun loadRecords() {
            recordRepository.getRecords()
                .onEach { records ->
                    _uiState.update { it.copy(records = records) }
                }
                .launchIn(viewModelScope)
        }
    }
