package com.project.climbing.domain.repository

import android.content.Context

interface AuthRepository {
    suspend fun loginWithKakao(context: Context): Result<Unit>

    suspend fun logout(): Result<Unit>
}
