package com.amritthakur.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import com.amritthakur.newsapp.state.LanguagesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

interface LanguagesInput {
    val onLanguage: (String) -> Unit
}

interface LanguagesOutput {
    val uiState: StateFlow<LanguagesUiState>
}

class LanguagesViewModel @Inject constructor() : ViewModel(), LanguagesInput, LanguagesOutput {

    private val _uiState = MutableStateFlow(LanguagesUiState())
    override val uiState: StateFlow<LanguagesUiState> = _uiState

    override val onLanguage: (String) -> Unit = {

    }
}
