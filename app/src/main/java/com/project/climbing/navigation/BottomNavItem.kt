package com.project.climbing.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: Route,
    val icon: ImageVector,
    val label: String,
) {
    data object Home : BottomNavItem(
        route = Route.Home,
        icon = Icons.Default.Home,
        label = "홈",
    )

    data object Gym : BottomNavItem(
        route = Route.Gym,
        icon = Icons.Default.Map,
        label = "암장",
    )

    data object Profile : BottomNavItem(
        route = Route.Profile,
        icon = Icons.Default.Person,
        label = "프로필",
    )

    companion object {
        val items = listOf(Home, Gym, Profile)
    }
}
