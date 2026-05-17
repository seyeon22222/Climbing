package com.project.climbing.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.project.climbing.presentation.challenge.ChallengeScreen
import com.project.climbing.presentation.gym.GymScreen
import com.project.climbing.presentation.home.HomeScreen
import com.project.climbing.presentation.login.LoginScreen
import com.project.climbing.presentation.profile.ProfileScreen
import com.project.climbing.presentation.record.RecordScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.Login,
    ) {
        composable<Route.Login> {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Route.Home) {
                        popUpTo(Route.Login) { inclusive = true }
                    }
                },
            )
        }
        composable<Route.Home> {
            HomeScreen()
        }
        composable<Route.Gym> {
            GymScreen()
        }
        composable<Route.Record> {
            RecordScreen()
        }
        composable<Route.Challenge> {
            ChallengeScreen()
        }
        composable<Route.Profile> {
            ProfileScreen()
        }
    }
}
