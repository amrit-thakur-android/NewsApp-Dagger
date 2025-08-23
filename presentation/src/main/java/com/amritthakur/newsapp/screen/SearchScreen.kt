package com.amritthakur.newsapp.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amritthakur.newsapp.R
import com.amritthakur.newsapp.component.EmptyView
import com.amritthakur.newsapp.component.ErrorView
import com.amritthakur.newsapp.component.LoadingView
import com.amritthakur.newsapp.component.NewsItem
import com.amritthakur.newsapp.state.SearchUiState
import com.amritthakur.newsapp.state.UiState
import com.amritthakur.newsapp.viewmodel.SearchInput
import com.amritthakur.newsapp.viewmodel.SearchOutput

@Composable
fun SearchScreen(
    input: SearchInput,
    output: SearchOutput
) {
    val context = LocalContext.current
    val errorMessage = stringResource(R.string.custom_tab_failed)

    SearchContent(
        uiState = output.uiState.collectAsStateWithLifecycle().value,
        onQueryChange = input.onQueryChange,
        onNews = { url ->
            launchCustomTab(
                context,
                url,
                errorMessage
            )
        },
        onTryAgain = input.onTryAgain
    )
}

@Composable
fun SearchContent(
    uiState: SearchUiState,
    onQueryChange: (String) -> Unit,
    onNews: (String) -> Unit,
    onTryAgain: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        OutlinedTextField(
            value = uiState.query,
            onValueChange = onQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = {
                Text(text = stringResource(R.string.search_hint))
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search)
                )
            },
            singleLine = true
        )

        when (uiState.articles) {
            is UiState.Loading -> {
                LoadingView()
            }

            is UiState.Success -> {
                val articles = uiState.articles.data

                if (articles.isEmpty() && uiState.query.isBlank()) {
                    EmptyView(
                        message = stringResource(R.string.enter_query)
                    )
                } else if (articles.isEmpty() && uiState.query.isNotBlank()) {
                    EmptyView(
                        message = stringResource(R.string.no_news_found),
                        messageInfo = stringResource(R.string.no_news_found_info_search)
                    )
                } else {
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
                                onNews = { onNews(article.url) }
                            )
                        }
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
}
