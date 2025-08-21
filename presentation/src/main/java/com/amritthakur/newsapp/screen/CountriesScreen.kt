package com.amritthakur.newsapp.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amritthakur.newsapp.component.PrimaryButton
import com.amritthakur.newsapp.state.CountriesUiState
import com.amritthakur.newsapp.viewmodel.CountriesInput
import com.amritthakur.newsapp.viewmodel.CountriesOutput

@Composable
fun CountriesScreen(
    input: CountriesInput,
    output: CountriesOutput
) {

    CountriesContent(
        uiState = output.uiState.collectAsStateWithLifecycle().value,
        onCountry = input.onCountry
    )
}

@Composable
fun CountriesContent(
    uiState: CountriesUiState,
    onCountry: (String) -> Unit
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(32.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(
            items = uiState.countries.toList(),
            key = { (countryCode, _) -> countryCode }
        ) { (countryCode, countryName) ->
            PrimaryButton(
                title = countryName,
                onClick = { onCountry(countryCode) }
            )
        }
    }
}
