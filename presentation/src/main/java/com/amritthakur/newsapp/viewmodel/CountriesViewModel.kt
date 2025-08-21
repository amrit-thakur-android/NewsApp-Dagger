package com.amritthakur.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import com.amritthakur.newsapp.navigation.NavigationChannel
import com.amritthakur.newsapp.navigation.NavigationEvent
import com.amritthakur.newsapp.state.CountriesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

interface CountriesInput {
    val onCountry: (String) -> Unit
}

interface CountriesOutput {
    val uiState: StateFlow<CountriesUiState>
}

class CountriesViewModel @Inject constructor(
    private val navigationChannel: NavigationChannel
) : ViewModel(), CountriesInput, CountriesOutput {

    private val _uiState = MutableStateFlow(CountriesUiState())
    override val uiState: StateFlow<CountriesUiState> = _uiState

    override val onCountry: (String) -> Unit = { countryCode ->
        navigationChannel.postEvent(CountriesNavigationEvent.NavigateToNews(countryCode))
    }
}

sealed class CountriesNavigationEvent : NavigationEvent {
    data class NavigateToNews(val countryCode: String) : CountriesNavigationEvent()
}
