package com.amritthakur.newsapp.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amritthakur.newsapp.viewmodel.HomeInput
import com.amritthakur.newsapp.viewmodel.HomeOutput

data class HomeActions(
    val onTopHeadLines: () -> Unit,
    val onNewsSources: () -> Unit,
    val onCountries: () -> Unit,
    val onLanguages: () -> Unit,
    val onSearch: () -> Unit
)

@Composable
fun HomeScreen(
    input: HomeInput,
    output: HomeOutput
) {
    val actions = HomeActions(
        onTopHeadLines = input.onTopHeadLines,
        onNewsSources = input.onNewsSources,
        onCountries = input.onCountries,
        onLanguages = input.onLanguages,
        onSearch = input.onSearch
    )

    HomeContent(
        actions = actions
    )
}

@Composable
fun HomeContent(
    actions: HomeActions
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(modifier = Modifier.weight(1f))

        HomeButton(
            title = "Top Headlines",
            onClick = actions.onTopHeadLines
        )

        Spacer(modifier = Modifier.weight(0.25f))

        HomeButton(
            title = "News Sources",
            onClick = actions.onNewsSources
        )

        Spacer(modifier = Modifier.weight(0.25f))

        HomeButton(
            title = "Countries",
            onClick = actions.onCountries
        )

        Spacer(modifier = Modifier.weight(0.25f))

        HomeButton(
            title = "Languages",
            onClick = actions.onLanguages
        )

        Spacer(modifier = Modifier.weight(0.25f))

        HomeButton(
            title = "Search",
            onClick = actions.onSearch
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun HomeButton(
    title: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeContentPreview() {
    val mockActions = HomeActions(
        onTopHeadLines = {},
        onNewsSources = {},
        onCountries = {},
        onLanguages = {},
        onSearch = {}
    )

    HomeContent(
        actions = mockActions
    )
}
