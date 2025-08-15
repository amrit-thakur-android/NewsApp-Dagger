package com.amritthakur.newsapp.repository

import com.amritthakur.newsapp.common.Result
import com.amritthakur.newsapp.entity.Article
import com.amritthakur.newsapp.entity.Source
import com.amritthakur.newsapp.entity.TopHeadlinesParams
import com.amritthakur.newsapp.remote.datasource.NewsRemoteDataSource
import com.amritthakur.newsapp.remote.dto.toEntity

class NewsRepositoryImpl(
    private val remoteDataSource: NewsRemoteDataSource
) : NewsRepository {

    override suspend fun getTopHeadlines(params: TopHeadlinesParams): Result<List<Article>> {
        return when (val result = remoteDataSource.getTopHeadlines(params)) {
            is Result.Success -> {
                Result.Success(result.data.articles.toEntity())
            }

            is Result.Error -> result
        }
    }

    override suspend fun getSources(): Result<List<Source>> {
        return when (val result = remoteDataSource.getSources()) {
            is Result.Success -> {
                Result.Success(result.data.sources.toEntity())
            }

            is Result.Error -> result
        }
    }

    override suspend fun searchNews(query: String): Result<List<Article>> {
        return when (val result = remoteDataSource.searchNews(query)) {
            is Result.Success -> {
                Result.Success(result.data.articles.toEntity())
            }

            is Result.Error -> result
        }
    }
}
