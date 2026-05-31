package com.project.climbing.di

import com.project.climbing.data.repository.AuthRepositoryImpl
import com.project.climbing.data.repository.FakeRecordRepository
import com.project.climbing.data.repository.GymRepositoryImpl
import com.project.climbing.domain.repository.AuthRepository
import com.project.climbing.domain.repository.GymRepository
import com.project.climbing.domain.repository.RecordRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindGymRepository(gymRepositoryImpl: GymRepositoryImpl): GymRepository

    @Binds
    @Singleton
    abstract fun bindRecordRepository(fakeRecordRepository: FakeRecordRepository): RecordRepository
}
