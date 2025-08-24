package com.amritthakur.newsapp.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.amritthakur.newsapp.common.Result
import com.amritthakur.newsapp.entity.Article
import com.amritthakur.newsapp.entity.TopHeadlinesParams
import com.amritthakur.newsapp.remote.datasource.NewsRemoteDataSource
import com.amritthakur.newsapp.remote.dto.toEntity
import javax.inject.Inject

class TopHeadlinesPagingSource @Inject constructor(
    private val remoteDataSource: NewsRemoteDataSource,
    private val topHeadlinesParams: TopHeadlinesParams
) : PagingSource<Int, Article>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
        private const val PAGE_SIZE = 20
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val pageSize = PAGE_SIZE
        val page = params.key ?: STARTING_PAGE_INDEX

        return try {
            val result = remoteDataSource.getTopHeadlines(
                params = topHeadlinesParams,
                pageSize = pageSize,
                page = page
            )

            when (result) {
                is Result.Success -> {
                    val rawArticles = result.data.articles ?: emptyList()
                    val articles = rawArticles.toEntity()
                    val totalResults = result.data.totalResults ?: 0

                    // Handle NewsAPI's unpredictable behavior: stop if no articles returned
                    val hasNextPage = rawArticles.isNotEmpty() && totalResults > 0

                    LoadResult.Page(
                        data = articles,
                        prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                        nextKey = if (hasNextPage) page + 1 else null
                    )
                }

                is Result.Error -> {
                    LoadResult.Error(
                        Exception("Code: ${result.code}, ErrorCode: ${result.errorCode}, ErrorMessage: ${result.errorMessage}")
                    )
                }
            }
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
