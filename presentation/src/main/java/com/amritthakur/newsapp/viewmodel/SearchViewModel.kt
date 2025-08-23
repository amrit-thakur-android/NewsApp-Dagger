package com.amritthakur.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amritthakur.newsapp.common.DispatcherProvider
import com.amritthakur.newsapp.common.Outcome
import com.amritthakur.newsapp.state.SearchUiState
import com.amritthakur.newsapp.state.UiState
import com.amritthakur.newsapp.usecase.SearchNewsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    private val _queryFlow = MutableStateFlow("")

    init {
        setupSearchFlow()
    }

    override val onQueryChange: (String) -> Unit = { query ->
        _uiState.value = _uiState.value.copy(
            query = query
        )
        _queryFlow.value = query
    }

    override val onTryAgain: () -> Unit = {
        val currentQuery = _uiState.value.query
        if (currentQuery.isNotBlank()) {
            _queryFlow.value = currentQuery
        }
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun setupSearchFlow() {
        _queryFlow
            .debounce(300)
            .distinctUntilChanged()
            .onEach { query ->
                if (query.isBlank()) {
                    _uiState.value = _uiState.value.copy(
                        articles = UiState.Success(emptyList())
                    )
                }
            }
            .filter { it.isNotBlank() }
            .flatMapLatest { query ->
                searchNews(query)
            }
            .flowOn(dispatcherProvider.io)
            .onEach { articles ->
                _uiState.value = _uiState.value.copy(articles = articles)
            }
            .launchIn(viewModelScope)
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
