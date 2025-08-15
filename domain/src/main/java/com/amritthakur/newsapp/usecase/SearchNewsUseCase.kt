package com.amritthakur.newsapp.usecase

import com.amritthakur.newsapp.common.DomainResult
import com.amritthakur.newsapp.common.Result
import com.amritthakur.newsapp.common.toDomainError
import com.amritthakur.newsapp.entity.Article
import com.amritthakur.newsapp.repository.NewsRepository

class SearchNewsUseCase(
    private val newsRepository: NewsRepository
) {

    suspend operator fun invoke(query: String): DomainResult<List<Article>> {
        return when (val result = newsRepository.searchNews(query)) {
            is Result.Success -> DomainResult.Success(result.data)
            is Result.Error -> DomainResult.Error(result.toDomainError())
        }
    }
}
