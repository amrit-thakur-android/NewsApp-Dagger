package com.amritthakur.newsapp.repository

import com.amritthakur.newsapp.common.Result
import com.amritthakur.newsapp.entity.Article
import com.amritthakur.newsapp.entity.Source
import com.amritthakur.newsapp.entity.TopHeadlinesParams

interface NewsRepository {

    suspend fun getTopHeadlines(params: TopHeadlinesParams = TopHeadlinesParams()): Result<List<Article>>

    suspend fun getSources(): Result<List<Source>>

    suspend fun searchNews(query: String): Result<List<Article>>
}
