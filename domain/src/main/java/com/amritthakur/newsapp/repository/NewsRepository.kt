package com.amritthakur.newsapp.repository

import androidx.paging.PagingData
import com.amritthakur.newsapp.common.Result
import com.amritthakur.newsapp.entity.Article
import com.amritthakur.newsapp.entity.Source
import com.amritthakur.newsapp.entity.TopHeadlinesParams
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getTopHeadlines(params: TopHeadlinesParams = TopHeadlinesParams()): Flow<PagingData<Article>>

    suspend fun getSources(): Result<List<Source>>

    fun searchNews(query: String): Flow<PagingData<Article>>
}
