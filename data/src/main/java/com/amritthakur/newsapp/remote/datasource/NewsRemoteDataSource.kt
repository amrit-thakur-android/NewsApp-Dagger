package com.amritthakur.newsapp.remote.datasource

import com.amritthakur.newsapp.common.Result
import com.amritthakur.newsapp.entity.TopHeadlinesParams
import com.amritthakur.newsapp.remote.api.NewsApiService
import com.amritthakur.newsapp.remote.response.NewsResponse
import com.amritthakur.newsapp.remote.response.SourcesResponse
import com.amritthakur.newsapp.remote.util.toError

class NewsRemoteDataSource(
    private val newsApiService: NewsApiService
) {

    suspend fun getTopHeadlines(
        params: TopHeadlinesParams = TopHeadlinesParams()
    ): Result<NewsResponse> {
        return try {
            val response = newsApiService.getTopHeadlines(
                country = params.country,
                source = params.source,
                language = params.language
            )

            if (response.isSuccessful) {
                val newsResponse = response.body()
                if (newsResponse != null) {
                    Result.Success(newsResponse)
                } else {
                    Result.Error(-2, "parsingError", "Empty response body")
                }
            } else {
                response.toError()
            }
        } catch (exception: Exception) {
            exception.toError()
        }
    }

    suspend fun getSources(): Result<SourcesResponse> {
        return try {
            val response = newsApiService.getSources()

            if (response.isSuccessful) {
                val sourcesResponse = response.body()
                if (sourcesResponse != null) {
                    Result.Success(sourcesResponse)
                } else {
                    Result.Error(-2, "parsingError", "Empty response body")
                }
            } else {
                response.toError()
            }
        } catch (exception: Exception) {
            exception.toError()
        }
    }

    suspend fun searchNews(
        query: String
    ): Result<NewsResponse> {
        return try {
            val response = newsApiService.searchNews(query)

            if (response.isSuccessful) {
                val newsResponse = response.body()
                if (newsResponse != null) {
                    Result.Success(newsResponse)
                } else {
                    Result.Error(-2, "parsingError", "Empty response body")
                }
            } else {
                response.toError()
            }
        } catch (exception: Exception) {
            exception.toError()
        }
    }
}
