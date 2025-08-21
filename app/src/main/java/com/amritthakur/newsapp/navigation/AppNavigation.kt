package com.amritthakur.newsapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.amritthakur.newsapp.NewsApplication
import com.amritthakur.newsapp.screen.CountriesScreen
import com.amritthakur.newsapp.screen.HomeScreen
import com.amritthakur.newsapp.screen.NewsScreen
import com.amritthakur.newsapp.screen.NewsSourcesScreen

@Composable
fun AppNavigation(
    modifier: Modifier
) {
    val context = LocalContext.current
    val applicationComponent = remember {
        (context.applicationContext as NewsApplication).applicationComponent
    }

    val navController = rememberNavController()
    val navigationCoordinator = remember { NavigationCoordinator(navController) }

    val navigationChannel = remember { applicationComponent.navigationChannel() }

    val navigationEvent by navigationChannel.navigationEvent
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
            val homeViewModel = remember { applicationComponent.homeViewModel() }
            HomeScreen(
                input = homeViewModel,
                output = homeViewModel
            )
        }

        composable(Screen.News.route) {
            val newsViewModel = remember { applicationComponent.newsViewModel() }
            NewsScreen(
                input = newsViewModel,
                output = newsViewModel
            )
        }

        composable(Screen.Sources.route) {
            val newsSourcesViewModel = remember { applicationComponent.newsSourcesViewModel() }
            NewsSourcesScreen(
                input = newsSourcesViewModel,
                output = newsSourcesViewModel
            )
        }

        composable(Screen.Countries.route) {
            val countriesViewModel = remember { applicationComponent.countriesViewModel() }
            CountriesScreen(
                input = countriesViewModel,
                output = countriesViewModel
            )
        }

        composable(Screen.Languages.route) {

        }

        composable(Screen.Search.route) {

        }
    }
}
