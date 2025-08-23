package com.amritthakur.newsapp.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amritthakur.newsapp.R
import com.amritthakur.newsapp.component.EmptyView
import com.amritthakur.newsapp.component.ErrorView
import com.amritthakur.newsapp.component.LoadingView
import com.amritthakur.newsapp.component.PrimaryButton
import com.amritthakur.newsapp.state.NewsSourcesUiState
import com.amritthakur.newsapp.state.UiState
import com.amritthakur.newsapp.viewmodel.NewsSourcesInput
import com.amritthakur.newsapp.viewmodel.NewsSourcesOutput

@Composable
fun NewsSourcesScreen(
    input: NewsSourcesInput,
    output: NewsSourcesOutput
) {

    NewsSourcesContent(
        uiState = output.uiState.collectAsStateWithLifecycle().value,
        onSource = input.onSource,
        onTryAgain = input.onTryAgain
    )
}

@Composable
fun NewsSourcesContent(
    uiState: NewsSourcesUiState,
    onSource: (String) -> Unit,
    onTryAgain: () -> Unit
) {
    when (uiState.sources) {
        is UiState.Loading -> {
            LoadingView()
        }

        is UiState.Success -> {
            val sources = uiState.sources.data

            if (sources.isEmpty()) {
                EmptyView(
                    message = stringResource(R.string.no_sources_found)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(32.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    items(
                        items = sources,
                        key = { source -> source.id }
                    ) { source ->
                        PrimaryButton(
                            title = source.name,
                            onClick = { onSource(source.id) }
                        )
                    }
                }
            }
        }

        is UiState.Error -> {
            ErrorView(
                message = uiState.sources.message,
                onTryAgain = onTryAgain
            )
        }
    }
}
