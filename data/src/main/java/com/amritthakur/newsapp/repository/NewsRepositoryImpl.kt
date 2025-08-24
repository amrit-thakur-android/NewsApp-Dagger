package com.amritthakur.newsapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.amritthakur.newsapp.common.Result
import com.amritthakur.newsapp.entity.Article
import com.amritthakur.newsapp.entity.Source
import com.amritthakur.newsapp.entity.TopHeadlinesParams
import com.amritthakur.newsapp.remote.datasource.NewsRemoteDataSource
import com.amritthakur.newsapp.remote.dto.toEntity
import com.amritthakur.newsapp.remote.paging.SearchNewsPagingSource
import com.amritthakur.newsapp.remote.paging.TopHeadlinesPagingSource
import kotlinx.coroutines.flow.Flow

class NewsRepositoryImpl(
    private val remoteDataSource: NewsRemoteDataSource
) : NewsRepository {

    companion object {
        private const val PAGE_SIZE = 20
        private const val PREFETCH_DISTANCE = 5
        private const val MAX_SIZE = 200
    }

    override fun getTopHeadlines(params: TopHeadlinesParams): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PREFETCH_DISTANCE,
                maxSize = MAX_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                TopHeadlinesPagingSource(
                    remoteDataSource = remoteDataSource,
                    topHeadlinesParams = params
                )
            }
        ).flow
    }

    override suspend fun getSources(): Result<List<Source>> {
        return when (val result = remoteDataSource.getSources()) {
            is Result.Success -> {
                Result.Success(result.data.sources.toEntity())
            }

            is Result.Error -> result
        }
    }

    override fun searchNews(query: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PREFETCH_DISTANCE,
                maxSize = MAX_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                SearchNewsPagingSource(
                    remoteDataSource = remoteDataSource,
                    query = query
                )
            }
        ).flow
    }
}
