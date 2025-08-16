package com.amritthakur.newsapp.state

import com.amritthakur.newsapp.entity.Article

data class NewsUiState(
    val articles: UiState<List<Article>> = UiState.Loading
)
