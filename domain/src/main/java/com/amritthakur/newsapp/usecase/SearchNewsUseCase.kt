package com.amritthakur.newsapp.usecase

import com.amritthakur.newsapp.common.Outcome
import com.amritthakur.newsapp.common.Result
import com.amritthakur.newsapp.common.toDomainError
import com.amritthakur.newsapp.entity.Article
import com.amritthakur.newsapp.repository.NewsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    suspend operator fun invoke(query: String): Outcome<List<Article>> {
        return when (val result = newsRepository.searchNews(query)) {
            is Result.Success -> Outcome.Success(result.data)
            is Result.Error -> Outcome.Error(result.toDomainError())
        }
    }
}
