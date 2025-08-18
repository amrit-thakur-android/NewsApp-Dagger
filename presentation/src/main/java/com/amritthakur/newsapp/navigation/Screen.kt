package com.amritthakur.newsapp.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object News : Screen("news")
    object Sources : Screen("sources")
    object Countries : Screen("countries")
    object Languages : Screen("languages")
    object Search : Screen("search")
}
