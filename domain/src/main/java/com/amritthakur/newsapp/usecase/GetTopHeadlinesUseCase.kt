package com.amritthakur.newsapp.usecase

import com.amritthakur.newsapp.common.Outcome
import com.amritthakur.newsapp.common.Result
import com.amritthakur.newsapp.common.toDomainError
import com.amritthakur.newsapp.entity.Article
import com.amritthakur.newsapp.entity.TopHeadlinesParams
import com.amritthakur.newsapp.repository.NewsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTopHeadlinesUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    suspend operator fun invoke(params: TopHeadlinesParams = TopHeadlinesParams()): Outcome<List<Article>> {
        return when (val result = newsRepository.getTopHeadlines(params)) {
            is Result.Success -> Outcome.Success(result.data)
            is Result.Error -> Outcome.Error(result.toDomainError())
        }
    }
}
