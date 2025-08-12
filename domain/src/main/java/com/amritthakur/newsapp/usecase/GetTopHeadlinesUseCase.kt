package com.amritthakur.newsapp.usecase

import com.amritthakur.newsapp.entity.Article
import com.amritthakur.newsapp.entity.TopHeadlinesParams
import com.amritthakur.newsapp.repository.NewsRepository

class GetTopHeadlinesUseCase(
    private val newsRepository: NewsRepository
) {

    suspend operator fun invoke(params: TopHeadlinesParams = TopHeadlinesParams()): List<Article> {
        return newsRepository.getTopHeadlines(params)
    }
}
