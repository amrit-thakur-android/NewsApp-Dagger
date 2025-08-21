package com.amritthakur.newsapp.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amritthakur.newsapp.component.ErrorView
import com.amritthakur.newsapp.component.LoadingView
import com.amritthakur.newsapp.component.NewsItem
import com.amritthakur.newsapp.state.NewsUiState
import com.amritthakur.newsapp.state.UiState
import com.amritthakur.newsapp.viewmodel.NewsInput
import com.amritthakur.newsapp.viewmodel.NewsOutput

@Composable
fun NewsScreen(
    input: NewsInput,
    output: NewsOutput
) {

    NewsContent(
        uiState = output.uiState.collectAsStateWithLifecycle().value,
        onNews = input.onNews,
        onTryAgain = input.onTryAgain
    )
}

@Composable
fun NewsContent(
    uiState: NewsUiState,
    onNews: () -> Unit,
    onTryAgain: () -> Unit
) {
    when (uiState.articles) {
        is UiState.Loading -> {
            LoadingView()
        }

        is UiState.Success -> {
            val articles = uiState.articles.data

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    items = articles,
                    key = { article -> article.url }
                ) { article ->
                    NewsItem(
                        article = article,
                        onNews = onNews
                    )
                }
            }
        }

        is UiState.Error -> {
            ErrorView(
                message = uiState.articles.message,
                onTryAgain = onTryAgain
            )
        }
    }
}
