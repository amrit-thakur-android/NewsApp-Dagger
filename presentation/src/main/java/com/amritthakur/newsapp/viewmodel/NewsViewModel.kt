package com.amritthakur.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amritthakur.newsapp.common.DispatcherProvider
import com.amritthakur.newsapp.common.Outcome
import com.amritthakur.newsapp.state.NewsUiState
import com.amritthakur.newsapp.state.UiState
import com.amritthakur.newsapp.usecase.GetTopHeadlinesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface NewsInput {
    val onNews: () -> Unit
}

interface NewsOutput {
    val uiState: StateFlow<NewsUiState>
}

class NewsViewModel @Inject constructor(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel(), NewsInput, NewsOutput {

    private val _uiState = MutableStateFlow(NewsUiState())
    override val uiState: StateFlow<NewsUiState> = _uiState

    init {
        getNews()
    }

    private fun getNews() {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiState.value = _uiState.value.copy(
                articles = UiState.Loading
            )
            when (val outcome = getTopHeadlinesUseCase()) {
                is Outcome.Success -> {
                    _uiState.value = _uiState.value.copy(
                        articles = UiState.Success(outcome.data)
                    )
                }

                is Outcome.Error -> {
                    _uiState.value = _uiState.value.copy(
                        articles = UiState.Error(outcome.error.message)
                    )
                }
            }
        }
    }

    override val onNews: () -> Unit = {

    }
}
