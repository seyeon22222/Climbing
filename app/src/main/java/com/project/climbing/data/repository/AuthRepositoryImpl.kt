package com.project.climbing.data.repository

import android.content.Context
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.project.climbing.domain.model.User
import com.project.climbing.domain.repository.AuthRepository
import com.project.climbing.domain.repository.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

@Singleton
class AuthRepositoryImpl
    @Inject
    constructor() : AuthRepository {
        private val _authState = MutableStateFlow<AuthState>(AuthState.Uninitialized)
        override val authState: StateFlow<AuthState> = _authState.asStateFlow()

        override suspend fun loginWithKakao(context: Context): Result<Unit> =
            suspendCancellableCoroutine { continuation ->
                val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                    if (error != null) {
                        continuation.resume(Result.failure(error))
                    } else if (token != null) {
                        // 로그인 성공 시 유저 정보 즉시 업데이트
                        UserApiClient.instance.me { kakaoUser, meError ->
                            if (meError != null) {
                                continuation.resume(Result.failure(meError))
                            } else if (kakaoUser != null) {
                                val domainUser =
                                    User(
                                        id = kakaoUser.id ?: 0L,
                                        nickname = kakaoUser.kakaoAccount?.profile?.nickname ?: "Unknown",
                                        profileImageUrl = kakaoUser.kakaoAccount?.profile?.thumbnailImageUrl,
                                        email = kakaoUser.kakaoAccount?.email,
                                    )
                                _authState.value = AuthState.Authenticated(domainUser)
                                continuation.resume(Result.success(Unit))
                            }
                        }
                    }
                }

                if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                    UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                        if (error != null) {
                            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                                continuation.resume(Result.failure(error))
                                return@loginWithKakaoTalk
                            }

                            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                        } else if (token != null) {
                            callback(token, null)
                        }
                    }
                } else {
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                }
            }

        override suspend fun logout(): Result<Unit> =
            suspendCancellableCoroutine { continuation ->
                UserApiClient.instance.logout { error ->
                    if (error != null) {
                        continuation.resume(Result.failure(error))
                    } else {
                        _authState.value = AuthState.Unauthenticated
                        continuation.resume(Result.success(Unit))
                    }
                }
            }

        override suspend fun getUserInfo(): Result<User> =
            suspendCancellableCoroutine { continuation ->
                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        _authState.value = AuthState.Unauthenticated
                        continuation.resume(Result.failure(error))
                    } else if (user != null) {
                        val domainUser =
                            User(
                                id = user.id ?: 0L,
                                nickname = user.kakaoAccount?.profile?.nickname ?: "Unknown",
                                profileImageUrl = user.kakaoAccount?.profile?.thumbnailImageUrl,
                                email = user.kakaoAccount?.email,
                            )
                        _authState.value = AuthState.Authenticated(domainUser)
                        continuation.resume(Result.success(domainUser))
                    }
                }
            }

        override suspend fun isLoggedIn(): Boolean =
            if (AuthApiClient.instance.hasToken()) {
                val result = getUserInfo()
                result.isSuccess
            } else {
                _authState.value = AuthState.Unauthenticated
                false
            }
    }
