package com.project.climbing.data.repository

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.project.climbing.domain.repository.AuthRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

@Singleton
class AuthRepositoryImpl
    @Inject
    constructor() : AuthRepository {
        override suspend fun loginWithKakao(context: Context): Result<Unit> =
            suspendCancellableCoroutine { continuation ->
                val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                    if (error != null) {
                        continuation.resume(Result.failure(error))
                    } else if (token != null) {
                        continuation.resume(Result.success(Unit))
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
                            continuation.resume(Result.success(Unit))
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
                        continuation.resume(Result.success(Unit))
                    }
                }
            }
    }
