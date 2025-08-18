package com.amritthakur.newsapp.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object NavigationChannel {

    private val _navigationEvent = MutableStateFlow<NavigationEvent?>(null)

    val navigationEvent: StateFlow<NavigationEvent?> = _navigationEvent

    fun postEvent(navigationEvent: NavigationEvent) {
        _navigationEvent.value = navigationEvent
    }
}
