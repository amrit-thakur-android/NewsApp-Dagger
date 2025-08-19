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

class HomeViewModel @Inject constructor() : HomeInput, HomeOutput {

    override val onTopHeadLines: () -> Unit = {
        NavigationChannel.postEvent(HomeNavigationEvent.NavigateToTopHeadlines)
    }

    override val onNewsSources: () -> Unit = {
        NavigationChannel.postEvent(HomeNavigationEvent.NavigateToNewsSources)
    }

    override val onCountries: () -> Unit = {
        NavigationChannel.postEvent(HomeNavigationEvent.NavigateToCountries)
    }

    override val onLanguages: () -> Unit = {
        NavigationChannel.postEvent(HomeNavigationEvent.NavigateToLanguages)
    }

    override val onSearch: () -> Unit = {
        NavigationChannel.postEvent(HomeNavigationEvent.NavigateToSearch)
    }
}

sealed class HomeNavigationEvent : NavigationEvent {
    object NavigateToTopHeadlines : HomeNavigationEvent()
    object NavigateToNewsSources : HomeNavigationEvent()
    object NavigateToCountries : HomeNavigationEvent()
    object NavigateToLanguages : HomeNavigationEvent()
    object NavigateToSearch : HomeNavigationEvent()
}
