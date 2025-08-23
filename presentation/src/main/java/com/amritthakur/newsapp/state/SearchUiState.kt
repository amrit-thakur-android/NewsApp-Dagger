package com.amritthakur.newsapp.state

import com.amritthakur.newsapp.entity.Article

data class SearchUiState(
    val query: String = "",
    val articles: UiState<List<Article>> = UiState.Success(emptyList())
)
