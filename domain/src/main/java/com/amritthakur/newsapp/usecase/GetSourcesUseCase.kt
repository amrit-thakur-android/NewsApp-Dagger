package com.amritthakur.newsapp.usecase

import com.amritthakur.newsapp.entity.Source
import com.amritthakur.newsapp.repository.NewsRepository

class GetSourcesUseCase(
    private val newsRepository: NewsRepository
) {

    suspend operator fun invoke(): List<Source> {
        return newsRepository.getSources()
    }
}
