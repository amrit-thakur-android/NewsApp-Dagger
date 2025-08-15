package com.amritthakur.newsapp.viewmodel

interface HomeInput {
    val onTopHeadLines: () -> Unit
    val onNewsSources: () -> Unit
    val onCountries: () -> Unit
    val onLanguages: () -> Unit
    val onSearch: () -> Unit
}

interface HomeOutput

class HomeViewModel : HomeInput, HomeOutput {

    override val onTopHeadLines: () -> Unit = {

    }

    override val onNewsSources: () -> Unit = {

    }

    override val onCountries: () -> Unit = {

    }

    override val onLanguages: () -> Unit = {

    }

    override val onSearch: () -> Unit = {

    }
}
