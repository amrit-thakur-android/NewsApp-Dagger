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

            }

            HomeNavigationEvent.NavigateToNewsSources -> {

            }

            HomeNavigationEvent.NavigateToCountries -> {

            }

            HomeNavigationEvent.NavigateToLanguages -> {

            }

            HomeNavigationEvent.NavigateToSearch -> {

            }
        }
    }
}
