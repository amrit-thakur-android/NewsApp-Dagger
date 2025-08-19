package com.amritthakur.newsapp.remote.api

import com.amritthakur.newsapp.remote.response.NewsResponse
import com.amritthakur.newsapp.remote.response.SourcesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NewsApiService {

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Header("X-Api-Key") apiKey: String = "f26da49a11a6415593a21e293ade2072",
        @Query("sources") source: String? = null,
        @Query("country") country: String? = null,
        @Query("language") language: String? = null
    ): Response<NewsResponse>

    @GET("v2/top-headlines/sources")
    suspend fun getSources(
        @Header("X-Api-Key") apiKey: String = "f26da49a11a6415593a21e293ade2072"
    ): Response<SourcesResponse>

    @GET("v2/everything")
    suspend fun searchNews(
        @Header("X-Api-Key") apiKey: String = "f26da49a11a6415593a21e293ade2072",
        @Query("q") query: String
    ): Response<NewsResponse>
}
