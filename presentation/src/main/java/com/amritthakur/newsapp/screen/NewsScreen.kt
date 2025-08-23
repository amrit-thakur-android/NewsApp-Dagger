package com.amritthakur.newsapp.screen

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amritthakur.newsapp.component.ErrorView
import com.amritthakur.newsapp.component.LoadingView
import com.amritthakur.newsapp.component.NewsItem
import com.amritthakur.newsapp.state.NewsUiState
import com.amritthakur.newsapp.state.UiState
import com.amritthakur.newsapp.viewmodel.NewsInput
import com.amritthakur.newsapp.viewmodel.NewsOutput
import com.amritthakur.newsapp.R
import com.amritthakur.newsapp.component.EmptyView

@Composable
fun NewsScreen(
    input: NewsInput,
    output: NewsOutput
) {
    val context = LocalContext.current
    val errorMessage = stringResource(R.string.custom_tab_failed)

    NewsContent(
        uiState = output.uiState.collectAsStateWithLifecycle().value,
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
fun NewsContent(
    uiState: NewsUiState,
    onNews: (String) -> Unit,
    onTryAgain: () -> Unit
) {
    when (uiState.articles) {
        is UiState.Loading -> {
            LoadingView()
        }

        is UiState.Success -> {
            val articles = uiState.articles.data

            if (articles.isEmpty()) {
                EmptyView(
                    message = stringResource(R.string.no_news_found)
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

fun launchCustomTab(
    context: Context,
    url: String,
    errorMessage: String
) {
    try {
        if (url.isBlank()) {
            return
        }

        val customTabsIntent = CustomTabsIntent.Builder()
            .setDefaultColorSchemeParams(
                CustomTabColorSchemeParams.Builder()
                    .build()
            )
            .setShowTitle(true)
            .setStartAnimations(
                context,
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right
            )
            .setExitAnimations(
                context,
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right
            )
            .build()

        customTabsIntent.launchUrl(context, url.toUri())
    } catch (_: Exception) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                context,
                e.message ?: errorMessage,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
