package com.amritthakur.newsapp.state

import com.amritthakur.newsapp.entity.Source

data class NewsSourcesUiState(
    val sources: UiState<List<Source>> = UiState.Loading
)
