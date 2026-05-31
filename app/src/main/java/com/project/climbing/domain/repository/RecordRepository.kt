package com.project.climbing.domain.repository

import com.project.climbing.domain.model.Record
import kotlinx.coroutines.flow.Flow

interface RecordRepository {
    fun getRecords(): Flow<List<Record>>

    fun getRecordsByGymId(gymId: String): Flow<List<Record>>

    suspend fun addRecord(record: Record)

    suspend fun deleteRecord(recordId: String)
}
