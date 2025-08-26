package com.amritthakur.newsapp.usecase

import androidx.paging.PagingData
import com.amritthakur.newsapp.entity.Article
import com.amritthakur.newsapp.entity.TopHeadlinesParams
import com.amritthakur.newsapp.repository.NewsRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.Flow
import org.junit.Test
import org.junit.Assert.*

class GetTopHeadlinesUseCaseTest {

    private val mockRepository = mockk<NewsRepository>()
    private val getTopHeadlinesUseCase = GetTopHeadlinesUseCase(mockRepository)

    @Test
    fun `invoke with default params should delegate to repository with default params`() {
        // Given
        val expectedFlow = mockk<Flow<PagingData<Article>>>()
        every { mockRepository.getTopHeadlines(TopHeadlinesParams()) } returns expectedFlow

        // When
        val result = getTopHeadlinesUseCase.invoke()

        // Then
        assertSame(expectedFlow, result)
        verify(exactly = 1) { mockRepository.getTopHeadlines(TopHeadlinesParams()) }
    }

    @Test
    fun `invoke with custom params should delegate to repository with same params`() {
        // Given
        val customParams = TopHeadlinesParams(
            source = "bbc-news",
            country = "us",
            language = "en"
        )
        val expectedFlow = mockk<Flow<PagingData<Article>>>()
        every { mockRepository.getTopHeadlines(customParams) } returns expectedFlow

        // When
        val result = getTopHeadlinesUseCase.invoke(customParams)

        // Then
        assertSame(expectedFlow, result)
        verify(exactly = 1) { mockRepository.getTopHeadlines(customParams) }
    }

    @Test
    fun `invoke with partial params should delegate to repository with same partial params`() {
        // Given
        val partialParams = TopHeadlinesParams(
            source = null,
            country = "gb",
            language = null
        )
        val expectedFlow = mockk<Flow<PagingData<Article>>>()
        every { mockRepository.getTopHeadlines(partialParams) } returns expectedFlow

        // When
        val result = getTopHeadlinesUseCase.invoke(partialParams)

        // Then
        assertSame(expectedFlow, result)
        verify(exactly = 1) { mockRepository.getTopHeadlines(partialParams) }
    }
}
