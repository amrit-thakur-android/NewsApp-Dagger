package com.amritthakur.newsapp.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.amritthakur.newsapp.entity.Article
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
        onNews = input.onNews
    )
}

@Composable
fun NewsContent(
    uiState: NewsUiState,
    onNews: () -> Unit
) {
    if (uiState.articles is UiState.Success) {
        val articles = (uiState.articles as UiState.Success).data

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
}

@Composable
private fun NewsItem(
    article: Article,
    onNews: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNews() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            // Image at top
            AsyncImage(
                model = article.urlToImage,
                contentDescription = "Article image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Title
            Text(
                text = article.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Description
            Text(
                text = article.description,
                fontSize = 14.sp,
                color = Color.Gray,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Source
            Text(
                text = "Source: ${article.sourceName}",
                fontSize = 12.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
