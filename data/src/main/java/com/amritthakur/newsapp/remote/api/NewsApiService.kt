package com.amritthakur.newsapp.remote.api

import com.amritthakur.newsapp.remote.response.NewsResponse
import com.amritthakur.newsapp.remote.response.SourcesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("sources") source: String? = null,
        @Query("country") country: String? = null,
        @Query("language") language: String? = null,
        @Query("pageSize") pageSize: Int? = null,
        @Query("page") page: Int? = null
    ): Response<NewsResponse>

    @GET("v2/top-headlines/sources")
    suspend fun getSources(): Response<SourcesResponse>

    @GET("v2/everything")
    suspend fun searchNews(
        @Query("q") query: String,
        @Query("pageSize") pageSize: Int? = null,
        @Query("page") page: Int? = null
    ): Response<NewsResponse>
}
