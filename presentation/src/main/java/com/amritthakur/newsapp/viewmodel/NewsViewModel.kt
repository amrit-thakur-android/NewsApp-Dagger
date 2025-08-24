package com.amritthakur.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.amritthakur.newsapp.entity.Article
import com.amritthakur.newsapp.entity.TopHeadlinesParams
import com.amritthakur.newsapp.usecase.GetTopHeadlinesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

interface NewsInput

interface NewsOutput {
    val uiState: StateFlow<PagingData<Article>>
}

class NewsViewModel @Inject constructor(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase
) : ViewModel(), NewsInput, NewsOutput {

    private val _uiState = MutableStateFlow<PagingData<Article>>(PagingData.empty())
    override val uiState: StateFlow<PagingData<Article>> = _uiState

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
        getTopHeadlinesUseCase(currentParams)
            .cachedIn(viewModelScope)
            .onEach {
                _uiState.value = it
            }.launchIn(viewModelScope)
    }
}
