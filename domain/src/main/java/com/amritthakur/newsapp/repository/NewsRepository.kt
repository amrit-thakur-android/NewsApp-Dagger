package com.amritthakur.newsapp.repository

import com.amritthakur.newsapp.entity.Article
import com.amritthakur.newsapp.entity.Source
import com.amritthakur.newsapp.entity.TopHeadlinesParams

interface NewsRepository {

    suspend fun getTopHeadlines(params: TopHeadlinesParams = TopHeadlinesParams()): List<Article>

    suspend fun getSources(): List<Source>

    suspend fun searchNews(query: String): List<Article>
}
