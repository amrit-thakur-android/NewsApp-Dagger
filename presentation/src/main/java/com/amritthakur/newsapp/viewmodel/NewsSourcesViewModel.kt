package com.amritthakur.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amritthakur.newsapp.common.DispatcherProvider
import com.amritthakur.newsapp.common.Outcome
import com.amritthakur.newsapp.navigation.NavigationChannel
import com.amritthakur.newsapp.navigation.NavigationEvent
import com.amritthakur.newsapp.state.NewsSourcesUiState
import com.amritthakur.newsapp.state.UiState
import com.amritthakur.newsapp.usecase.GetSourcesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface NewsSourcesInput {
    val onSource: (String) -> Unit
    val onTryAgain: () -> Unit
}

interface NewsSourcesOutput {
    val uiState: StateFlow<NewsSourcesUiState>
}

class NewsSourcesViewModel @Inject constructor(
    private val getSourcesUseCase: GetSourcesUseCase,
    private val dispatcherProvider: DispatcherProvider,
    private val navigationChannel: NavigationChannel
) : ViewModel(), NewsSourcesInput, NewsSourcesOutput {

    private val _uiState = MutableStateFlow(NewsSourcesUiState())
    override val uiState: StateFlow<NewsSourcesUiState> = _uiState

    init {
        getSources()
    }

    private fun getSources() {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiState.value = _uiState.value.copy(
                sources = UiState.Loading
            )
            when (val outcome = getSourcesUseCase()) {
                is Outcome.Success -> {
                    _uiState.value = _uiState.value.copy(
                        sources = UiState.Success(outcome.data)
                    )
                }

                is Outcome.Error -> {
                    _uiState.value = _uiState.value.copy(
                        sources = UiState.Error(outcome.error.message)
                    )
                }
            }
        }
    }

    override val onSource: (String) -> Unit = { sourceId ->
        navigationChannel.postEvent(NewsSourcesNavigationEvent.NavigateToNews(sourceId))
    }

    override val onTryAgain: () -> Unit = {
        getSources()
    }
}

sealed class NewsSourcesNavigationEvent : NavigationEvent {
    data class NavigateToNews(val sourceId: String) : NewsSourcesNavigationEvent()
}
