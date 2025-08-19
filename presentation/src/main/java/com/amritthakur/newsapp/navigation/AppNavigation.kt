package com.amritthakur.newsapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation(
    modifier: Modifier
) {
    val navController = rememberNavController()
    val navigationCoordinator = remember { NavigationCoordinator(navController) }

    val navigationEvent by NavigationChannel.navigationEvent
        .collectAsStateWithLifecycle()

    LaunchedEffect(navigationEvent) {
        navigationEvent?.let { event ->
            navigationCoordinator.handleNavigationEvent(event)
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {

        }

        composable(Screen.News.route) {

        }

        composable(Screen.Sources.route) {

        }

        composable(Screen.Countries.route) {

        }

        composable(Screen.Languages.route) {

        }

        composable(Screen.Search.route) {

        }
    }
}
