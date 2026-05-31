package com.project.climbing.presentation.record

import com.project.climbing.domain.model.Record

data class RecordUiState(
    val isLoading: Boolean = false,
    val records: List<Record> = emptyList(),
)
