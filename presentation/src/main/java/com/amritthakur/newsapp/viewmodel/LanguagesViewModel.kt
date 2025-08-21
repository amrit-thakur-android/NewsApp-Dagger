package com.amritthakur.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import com.amritthakur.newsapp.navigation.NavigationChannel
import com.amritthakur.newsapp.navigation.NavigationEvent
import com.amritthakur.newsapp.state.LanguagesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

interface LanguagesInput {
    val onLanguage: (String) -> Unit
}

interface LanguagesOutput {
    val uiState: StateFlow<LanguagesUiState>
}

class LanguagesViewModel @Inject constructor(
    private val navigationChannel: NavigationChannel
) : ViewModel(), LanguagesInput, LanguagesOutput {

    private val _uiState = MutableStateFlow(LanguagesUiState())
    override val uiState: StateFlow<LanguagesUiState> = _uiState

    override val onLanguage: (String) -> Unit = { languageCode ->
        navigationChannel.postEvent(LanguagesNavigationEvent.NavigateToNews(languageCode))
    }
}

sealed class LanguagesNavigationEvent : NavigationEvent {
    data class NavigateToNews(val languageCode: String) : LanguagesNavigationEvent()
}
