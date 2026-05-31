package com.project.climbing.data.repository

import com.project.climbing.domain.model.DifficultyLevel
import com.project.climbing.domain.model.Gym
import com.project.climbing.domain.repository.GymRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GymRepositoryImpl
    @Inject
    constructor() : GymRepository {
        private val fakeGyms =
            listOf(
                Gym(
                    id = "1",
                    name = "더클라이밍 마포점",
                    address = "서울 마포구 양화로 125",
                    imageUrl =
                        "https://images.unsplash.com/photo-1522163182402-834f871fd851" +
                            "?ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=60",
                    difficultyLevels =
                        listOf(
                            DifficultyLevel("하얀색", "#FFFFFF"),
                            DifficultyLevel("노란색", "#FFFF00"),
                            DifficultyLevel("주황색", "#FFA500"),
                            DifficultyLevel("초록색", "#008000"),
                            DifficultyLevel("파란색", "#0000FF"),
                            DifficultyLevel("빨간색", "#FF0000"),
                            DifficultyLevel("보라색", "#800080"),
                            DifficultyLevel("회색", "#808080"),
                            DifficultyLevel("검정색", "#000000"),
                        ),
                    latitude = 37.5552,
                    longitude = 126.9212,
                ),
                Gym(
                    id = "2",
                    name = "서울볼더스 클라이밍",
                    address = "서울 영등포구 선유로 13길 25",
                    imageUrl =
                        "https://images.unsplash.com/photo-1516631141151-24959146522c" +
                            "?ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=60",
                    difficultyLevels =
                        listOf(
                            DifficultyLevel("빨강", "#FF0000"),
                            DifficultyLevel("주황", "#FFA500"),
                            DifficultyLevel("노랑", "#FFFF00"),
                            DifficultyLevel("초록", "#008000"),
                            DifficultyLevel("파랑", "#0000FF"),
                            DifficultyLevel("남색", "#000080"),
                            DifficultyLevel("보라", "#800080"),
                        ),
                    latitude = 37.5187,
                    longitude = 126.8864,
                ),
                Gym(
                    id = "3",
                    name = "피커스 클라이밍",
                    address = "서울 종로구 삼일대로 428",
                    imageUrl =
                        "https://images.unsplash.com/photo-1502120412854-8e4f16a69502" +
                            "?ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=60",
                    difficultyLevels =
                        listOf(
                            DifficultyLevel("VB", "#FFFFFF"),
                            DifficultyLevel("V0", "#FFFF00"),
                            DifficultyLevel("V1", "#FFA500"),
                            DifficultyLevel("V2", "#008000"),
                            DifficultyLevel("V3", "#0000FF"),
                        ),
                    latitude = 37.5744,
                    longitude = 126.9882,
                ),
            )

        override fun getGyms(): Flow<List<Gym>> = flowOf(fakeGyms)

        override suspend fun getGymById(id: String): Gym? = fakeGyms.find { it.id == id }
    }
