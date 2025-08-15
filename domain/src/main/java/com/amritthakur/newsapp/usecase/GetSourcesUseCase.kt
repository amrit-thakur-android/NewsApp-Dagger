package com.amritthakur.newsapp.usecase

import com.amritthakur.newsapp.common.DomainResult
import com.amritthakur.newsapp.common.Result
import com.amritthakur.newsapp.common.toDomainError
import com.amritthakur.newsapp.entity.Source
import com.amritthakur.newsapp.repository.NewsRepository

class GetSourcesUseCase(
    private val newsRepository: NewsRepository
) {

    suspend operator fun invoke(): DomainResult<List<Source>> {
        return when (val result = newsRepository.getSources()) {
            is Result.Success -> DomainResult.Success(result.data)
            is Result.Error -> DomainResult.Error(result.toDomainError())
        }
    }
}
