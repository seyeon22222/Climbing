package com.project.climbing.domain.repository

import android.content.Context
import com.project.climbing.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val authState: Flow<AuthState>

    suspend fun loginWithKakao(context: Context): Result<Unit>

    suspend fun logout(): Result<Unit>

    suspend fun getUserInfo(): Result<User>

    suspend fun isLoggedIn(): Boolean
}

sealed interface AuthState {
    data object Uninitialized : AuthState
    data class Authenticated(val user: User) : AuthState
    data object Unauthenticated : AuthState
}
