package com.amritthakur.newsapp.usecase

import com.amritthakur.newsapp.entity.Article
import com.amritthakur.newsapp.repository.NewsRepository

class SearchNewsUseCase(
    private val newsRepository: NewsRepository
) {

    suspend operator fun invoke(query: String): List<Article> {
        return newsRepository.searchNews(query)
    }
}
