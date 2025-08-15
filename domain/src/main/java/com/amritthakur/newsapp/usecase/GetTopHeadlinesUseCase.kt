package com.amritthakur.newsapp.usecase

import com.amritthakur.newsapp.common.DomainResult
import com.amritthakur.newsapp.common.Result
import com.amritthakur.newsapp.common.toDomainError
import com.amritthakur.newsapp.entity.Article
import com.amritthakur.newsapp.entity.TopHeadlinesParams
import com.amritthakur.newsapp.repository.NewsRepository

class GetTopHeadlinesUseCase(
    private val newsRepository: NewsRepository
) {

    suspend operator fun invoke(params: TopHeadlinesParams = TopHeadlinesParams()): DomainResult<List<Article>> {
        return when (val result = newsRepository.getTopHeadlines(params)) {
            is Result.Success -> DomainResult.Success(result.data)
            is Result.Error -> DomainResult.Error(result.toDomainError())
        }
    }
}
