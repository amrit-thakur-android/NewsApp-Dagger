package com.amritthakur.newsapp.usecase

import androidx.paging.PagingData
import com.amritthakur.newsapp.entity.Article
import com.amritthakur.newsapp.entity.TopHeadlinesParams
import com.amritthakur.newsapp.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTopHeadlinesUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    operator fun invoke(params: TopHeadlinesParams = TopHeadlinesParams()): Flow<PagingData<Article>> {
        return newsRepository.getTopHeadlines(params)
    }
}
