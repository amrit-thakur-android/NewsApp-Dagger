package com.amritthakur.newsapp.viewmodel

import com.amritthakur.newsapp.navigation.NavigationChannel
import com.amritthakur.newsapp.navigation.NavigationEvent
import javax.inject.Inject

interface HomeInput {
    val onTopHeadLines: () -> Unit
    val onNewsSources: () -> Unit
    val onCountries: () -> Unit
    val onLanguages: () -> Unit
    val onSearch: () -> Unit
}

interface HomeOutput

class HomeViewModel @Inject constructor(
    navigationChannel: NavigationChannel
) : HomeInput, HomeOutput {

    override val onTopHeadLines: () -> Unit = {
        navigationChannel.postEvent(HomeNavigationEvent.NavigateToTopHeadlines)
    }

    override val onNewsSources: () -> Unit = {
        navigationChannel.postEvent(HomeNavigationEvent.NavigateToNewsSources)
    }

    override val onCountries: () -> Unit = {
        navigationChannel.postEvent(HomeNavigationEvent.NavigateToCountries)
    }

    override val onLanguages: () -> Unit = {
        navigationChannel.postEvent(HomeNavigationEvent.NavigateToLanguages)
    }

    override val onSearch: () -> Unit = {
        navigationChannel.postEvent(HomeNavigationEvent.NavigateToSearch)
    }
}

sealed class HomeNavigationEvent : NavigationEvent {
    object NavigateToTopHeadlines : HomeNavigationEvent()
    object NavigateToNewsSources : HomeNavigationEvent()
    object NavigateToCountries : HomeNavigationEvent()
    object NavigateToLanguages : HomeNavigationEvent()
    object NavigateToSearch : HomeNavigationEvent()
}
