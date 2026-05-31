package com.project.climbing.domain.model

import java.time.LocalDateTime

data class Record(
    val id: String,
    val userId: String,
    val gymId: String,
    val gymName: String,
    val date: LocalDateTime,
    val difficulty: Difficulty,
    val memo: String = "",
)
