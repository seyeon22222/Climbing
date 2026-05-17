package com.project.climbing.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Login : Route

    @Serializable
    data object Home : Route

    @Serializable
    data object Gym : Route

    @Serializable
    data object Record : Route

    @Serializable
    data object Challenge : Route

    @Serializable
    data object Profile : Route
}
