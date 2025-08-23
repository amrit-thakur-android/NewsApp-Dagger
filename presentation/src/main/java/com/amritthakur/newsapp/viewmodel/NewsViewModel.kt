package com.amritthakur.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amritthakur.newsapp.common.DispatcherProvider
import com.amritthakur.newsapp.common.Outcome
import com.amritthakur.newsapp.entity.TopHeadlinesParams
import com.amritthakur.newsapp.state.NewsUiState
import com.amritthakur.newsapp.state.UiState
import com.amritthakur.newsapp.usecase.GetTopHeadlinesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface NewsInput {
    val onTryAgain: () -> Unit
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

    private var currentParams = TopHeadlinesParams()

    fun updateParams(source: String?, country: String?, language: String?) {
        currentParams = TopHeadlinesParams(
            source = source,
            country = country,
            language = language
        )
        getNews()
    }

    private fun getNews() {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiState.value = _uiState.value.copy(
                articles = UiState.Loading
            )
            when (val outcome = getTopHeadlinesUseCase(
                params = currentParams
            )) {
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

    override val onTryAgain: () -> Unit = {
        getNews()
    }
}
