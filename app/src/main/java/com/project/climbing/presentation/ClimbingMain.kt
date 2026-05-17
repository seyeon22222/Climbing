package com.project.climbing.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.project.climbing.core.ui.theme.BackgroundBlack
import com.project.climbing.core.ui.theme.MainOrange
import com.project.climbing.core.ui.theme.TextGrey
import com.project.climbing.navigation.AppNavGraph
import com.project.climbing.navigation.BottomNavItem

@Composable
fun ClimbingMain() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // 하단 바를 표시할 경로들 확인
    val showBottomBar =
        BottomNavItem.items.any { item ->
            currentDestination?.hierarchy?.any { it.hasRoute(item.route::class) } == true
        }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = BackgroundBlack,
                    contentColor = TextGrey,
                ) {
                    BottomNavItem.items.forEach { item ->
                        val isSelected =
                            currentDestination?.hierarchy?.any {
                                it.hasRoute(item.route::class)
                            } == true

                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            selected = isSelected,
                            colors =
                                NavigationBarItemDefaults.colors(
                                    selectedIconColor = MainOrange,
                                    selectedTextColor = MainOrange,
                                    unselectedIconColor = TextGrey,
                                    unselectedTextColor = TextGrey,
                                    indicatorColor = Color.Transparent,
                                ),
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                        )
                    }
                }
            }
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            AppNavGraph(navController = navController)
        }
    }
}
