package com.project.climbing.domain.model

data class User(
    val id: Long,
    val nickname: String,
    val profileImageUrl: String?,
    val email: String?,
)
