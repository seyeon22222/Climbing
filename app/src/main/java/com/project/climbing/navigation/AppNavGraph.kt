package com.project.climbing.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.Login,
    ) {
        composable<Route.Login> {
            PlaceholderScreen(name = "Login Screen")
        }
        composable<Route.Home> {
            PlaceholderScreen(name = "Home Screen")
        }
        composable<Route.Gym> {
            PlaceholderScreen(name = "Gym Screen")
        }
        composable<Route.Profile> {
            PlaceholderScreen(name = "Profile Screen")
        }
    }
}

@Composable
fun PlaceholderScreen(name: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = name)
    }
}
