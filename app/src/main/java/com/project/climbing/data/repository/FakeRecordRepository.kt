package com.project.climbing.data.repository

import com.project.climbing.domain.model.Difficulty
import com.project.climbing.domain.model.Record
import com.project.climbing.domain.repository.RecordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeRecordRepository
    @Inject
    constructor() : RecordRepository {
        private val recordsState =
            MutableStateFlow<List<Record>>(
                listOf(
                    Record(
                        id = UUID.randomUUID().toString(),
                        userId = "user1",
                        gymId = "1",
                        gymName = "더클라이밍 마포점",
                        date = LocalDateTime.now().minusDays(1),
                        difficulty = Difficulty.BLUE,
                        memo = "오늘 컨디션 좋음",
                    ),
                    Record(
                        id = UUID.randomUUID().toString(),
                        userId = "user1",
                        gymId = "1",
                        gymName = "더클라이밍 마포점",
                        date = LocalDateTime.now().minusDays(1),
                        difficulty = Difficulty.RED,
                        memo = "빨강 도전 성공!",
                    ),
                    Record(
                        id = UUID.randomUUID().toString(),
                        userId = "user1",
                        gymId = "2",
                        gymName = "서울볼더스 클라이밍",
                        date = LocalDateTime.now().minusDays(3),
                        difficulty = Difficulty.PURPLE,
                        memo = "어렵다..",
                    ),
                ),
            )

        override fun getRecords(): Flow<List<Record>> = recordsState.asStateFlow()

        override fun getRecordsByGymId(gymId: String): Flow<List<Record>> =
            recordsState.asStateFlow().map { it.filter { record -> record.gymId == gymId } }

        override suspend fun addRecord(record: Record) {
            val newRecord = record.copy(id = UUID.randomUUID().toString())
            recordsState.value = recordsState.value + newRecord
        }

        override suspend fun deleteRecord(recordId: String) {
            recordsState.value = recordsState.value.filter { it.id != recordId }
        }
    }
