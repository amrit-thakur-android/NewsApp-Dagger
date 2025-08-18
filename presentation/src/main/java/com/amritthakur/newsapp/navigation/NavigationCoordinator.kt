package com.amritthakur.newsapp.navigation

import androidx.navigation.NavController
import com.amritthakur.newsapp.viewmodel.HomeNavigationEvent

class NavigationCoordinator(
    private val navController: NavController
) {

    fun handleNavigationEvent(navigationEvent: NavigationEvent) {
        when (navigationEvent) {
            is HomeNavigationEvent -> {
                handleHomeNavigationEvent(navigationEvent)
            }
        }
    }

    private fun handleHomeNavigationEvent(homeNavigationEvent: HomeNavigationEvent) {
        when (homeNavigationEvent) {
            HomeNavigationEvent.NavigateToTopHeadlines -> {
                navController.navigate(Screen.News.route)
            }

            HomeNavigationEvent.NavigateToNewsSources -> {
                navController.navigate(Screen.Sources.route)
            }

            HomeNavigationEvent.NavigateToCountries -> {
                navController.navigate(Screen.Countries.route)
            }

            HomeNavigationEvent.NavigateToLanguages -> {
                navController.navigate(Screen.Languages.route)
            }

            HomeNavigationEvent.NavigateToSearch -> {
                navController.navigate(Screen.Search.route)
            }
        }
    }
}
