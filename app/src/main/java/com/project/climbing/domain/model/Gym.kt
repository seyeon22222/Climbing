package com.project.climbing.domain.model

data class Gym(
    val id: String,
    val name: String,
    val address: String,
    val imageUrl: String?,
    val difficultyLevels: List<DifficultyLevel> = emptyList(),
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
)

data class DifficultyLevel(
    val name: String,
    // Hex color code (e.g., "#FF0000")
    val color: String,
)
