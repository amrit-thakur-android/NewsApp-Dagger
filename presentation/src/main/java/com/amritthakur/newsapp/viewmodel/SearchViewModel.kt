package com.amritthakur.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import com.amritthakur.newsapp.common.DispatcherProvider
import com.amritthakur.newsapp.common.Outcome
import com.amritthakur.newsapp.state.SearchUiState
import com.amritthakur.newsapp.state.UiState
import com.amritthakur.newsapp.usecase.SearchNewsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface SearchInput {
    val onQueryChange: (String) -> Unit
    val onTryAgain: () -> Unit
}

interface SearchOutput {
    val uiState: StateFlow<SearchUiState>
}

class SearchViewModel @Inject constructor(
    private val searchNewsUseCase: SearchNewsUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel(), SearchInput, SearchOutput {

    private val _uiState = MutableStateFlow(SearchUiState())
    override val uiState: StateFlow<SearchUiState> = _uiState

    override val onQueryChange: (String) -> Unit = { query ->

    }

    override val onTryAgain: () -> Unit = {

    }

    private fun searchNews(query: String) = flow {
        emit(UiState.Loading)
        when (val outcome = searchNewsUseCase(query)) {
            is Outcome.Success -> {
                emit(UiState.Success(outcome.data))
            }

            is Outcome.Error -> {
                emit(UiState.Error(outcome.error.message))
            }
        }
    }
}
