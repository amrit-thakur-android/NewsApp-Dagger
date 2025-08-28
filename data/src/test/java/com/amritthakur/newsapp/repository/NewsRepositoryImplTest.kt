package com.amritthakur.newsapp.repository

import com.amritthakur.newsapp.common.Result
import com.amritthakur.newsapp.entity.TopHeadlinesParams
import com.amritthakur.newsapp.remote.datasource.NewsRemoteDataSource
import com.amritthakur.newsapp.remote.dto.SourceDto
import com.amritthakur.newsapp.remote.response.SourcesResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*

class NewsRepositoryImplTest {

    private val mockRemoteDataSource = mockk<NewsRemoteDataSource>()
    private val repository = NewsRepositoryImpl(mockRemoteDataSource)

    // ========== getSources Tests ==========

    @Test
    fun `getSources should return success with mapped sources when remote data source returns success`() =
        runTest {
            // Given
            val sourceDtos = listOf(
                SourceDto(
                    id = "bbc-news",
                    name = "BBC News",
                    description = "BBC News description",
                    url = "http://www.bbc.co.uk/news",
                    category = "general",
                    language = "en",
                    country = "gb"
                ),
                SourceDto(
                    id = "cnn",
                    name = "CNN",
                    description = "CNN description",
                    url = "http://www.cnn.com",
                    category = "general",
                    language = "en",
                    country = "us"
                )
            )
            val sourcesResponse = SourcesResponse(sources = sourceDtos)
            val remoteResult = Result.Success(sourcesResponse)

            coEvery { mockRemoteDataSource.getSources() } returns remoteResult

            // When
            val result = repository.getSources()

            // Then
            assertTrue(result is Result.Success)
            val sources = (result as Result.Success).data
            assertEquals(2, sources.size)

            // Verify mapping worked correctly
            assertEquals("bbc-news", sources[0].id)
            assertEquals("BBC News", sources[0].name)
            assertEquals("BBC News description", sources[0].description)
            assertEquals("http://www.bbc.co.uk/news", sources[0].url)
            assertEquals("general", sources[0].category)
            assertEquals("en", sources[0].language)
            assertEquals("gb", sources[0].country)

            assertEquals("cnn", sources[1].id)
            assertEquals("CNN", sources[1].name)

            coVerify(exactly = 1) { mockRemoteDataSource.getSources() }
        }

    @Test
    fun `getSources should return success with empty list when remote data source returns empty sources`() =
        runTest {
            // Given
            val sourcesResponse = SourcesResponse(sources = emptyList())
            val remoteResult = Result.Success(sourcesResponse)

            coEvery { mockRemoteDataSource.getSources() } returns remoteResult

            // When
            val result = repository.getSources()

            // Then
            assertTrue(result is Result.Success)
            val sources = (result as Result.Success).data
            assertTrue(sources.isEmpty())

            coVerify(exactly = 1) { mockRemoteDataSource.getSources() }
        }

    @Test
    fun `getSources should return success with empty list when remote data source returns null sources`() =
        runTest {
            // Given
            val sourcesResponse = SourcesResponse(sources = null)
            val remoteResult = Result.Success(sourcesResponse)

            coEvery { mockRemoteDataSource.getSources() } returns remoteResult

            // When
            val result = repository.getSources()

            // Then
            assertTrue(result is Result.Success)
            val sources = (result as Result.Success).data
            assertTrue(sources.isEmpty())

            coVerify(exactly = 1) { mockRemoteDataSource.getSources() }
        }

    @Test
    fun `getSources should filter out invalid sources during mapping`() = runTest {
        // Given
        val sourceDtos = listOf(
            // Valid source
            SourceDto(
                id = "bbc-news",
                name = "BBC News",
                description = "BBC News description",
                url = "http://www.bbc.co.uk/news",
                category = "general",
                language = "en",
                country = "gb"
            ),
            // Invalid source - null id
            SourceDto(
                id = null,
                name = "Invalid Source",
                description = "Invalid description",
                url = "http://invalid.com",
                category = "general",
                language = "en",
                country = "us"
            ),
            // Invalid source - blank name
            SourceDto(
                id = "invalid-source",
                name = "   ",
                description = "Invalid description",
                url = "http://invalid.com",
                category = "general",
                language = "en",
                country = "us"
            ),
            // Valid source
            SourceDto(
                id = "cnn",
                name = "CNN",
                description = null, // null optional fields should work
                url = null,
                category = null,
                language = null,
                country = null
            )
        )
        val sourcesResponse = SourcesResponse(sources = sourceDtos)
        val remoteResult = Result.Success(sourcesResponse)

        coEvery { mockRemoteDataSource.getSources() } returns remoteResult

        // When
        val result = repository.getSources()

        // Then
        assertTrue(result is Result.Success)
        val sources = (result as Result.Success).data
        assertEquals(2, sources.size) // Only 2 valid sources should remain

        assertEquals("bbc-news", sources[0].id)
        assertEquals("BBC News", sources[0].name)

        assertEquals("cnn", sources[1].id)
        assertEquals("CNN", sources[1].name)
        assertEquals("", sources[1].description) // null mapped to empty string
        assertEquals("", sources[1].url) // null mapped to empty string

        coVerify(exactly = 1) { mockRemoteDataSource.getSources() }
    }

    @Test
    fun `getSources should return error when remote data source returns error`() = runTest {
        // Given
        val remoteError = Result.Error(
            code = 401,
            errorCode = "apiKeyInvalid",
            errorMessage = "Invalid API key"
        )

        coEvery { mockRemoteDataSource.getSources() } returns remoteError

        // When
        val result = repository.getSources()

        // Then
        assertTrue(result is Result.Error)
        val error = result as Result.Error
        assertEquals(401, error.code)
        assertEquals("apiKeyInvalid", error.errorCode)
        assertEquals("Invalid API key", error.errorMessage)

        coVerify(exactly = 1) { mockRemoteDataSource.getSources() }
    }

    @Test
    fun `getSources should pass through parameterInvalid error unchanged`() = runTest {
        // Given
        val expectedError = Result.Error(400, "parameterInvalid", "Invalid parameter")
        coEvery { mockRemoteDataSource.getSources() } returns expectedError

        // When
        val result = repository.getSources()

        // Then
        assertEquals(expectedError, result)
        coVerify(exactly = 1) { mockRemoteDataSource.getSources() }
    }

    @Test
    fun `getSources should pass through rateLimited error unchanged`() = runTest {
        // Given
        val expectedError = Result.Error(429, "rateLimited", "Rate limit exceeded")
        coEvery { mockRemoteDataSource.getSources() } returns expectedError

        // When
        val result = repository.getSources()

        // Then
        assertEquals(expectedError, result)
        coVerify(exactly = 1) { mockRemoteDataSource.getSources() }
    }

    @Test
    fun `getSources should pass through serverError unchanged`() = runTest {
        // Given
        val expectedError = Result.Error(500, "serverError", "Internal server error")
        coEvery { mockRemoteDataSource.getSources() } returns expectedError

        // When
        val result = repository.getSources()

        // Then
        assertEquals(expectedError, result)
        coVerify(exactly = 1) { mockRemoteDataSource.getSources() }
    }

    @Test
    fun `getSources should pass through network error unchanged`() = runTest {
        // Given
        val expectedError = Result.Error(-1, "IOException", "Network error")
        coEvery { mockRemoteDataSource.getSources() } returns expectedError

        // When
        val result = repository.getSources()

        // Then
        assertEquals(expectedError, result)
        coVerify(exactly = 1) { mockRemoteDataSource.getSources() }
    }

    // ========== getTopHeadlines Tests ==========
    // Note: getTopHeadlines creates a Pager with PagingConfig and TopHeadlinesPagingSource.
    // The actual paging logic and parameter passing will be tested in TopHeadlinesPagingSource tests.
    // Here we only verify that the method doesn't crash and returns a Flow.

    @Test
    fun `getTopHeadlines should return non-null Flow for any TopHeadlinesParams`() {
        // Given
        val testParams = listOf(
            TopHeadlinesParams(), // default params
            TopHeadlinesParams(country = "us"),
            TopHeadlinesParams(source = "bbc-news"),
            TopHeadlinesParams(language = "en"),
            TopHeadlinesParams(country = "us", source = "bbc-news", language = "en")
        )

        testParams.forEach { params ->
            // When
            val result = repository.getTopHeadlines(params)

            // Then
            assertNotNull("Failed for params: $params", result)
        }
    }

    // ========== searchNews Tests ==========
    // Note: searchNews creates a Pager with PagingConfig and SearchNewsPagingSource.
    // The actual paging logic and query handling will be tested in SearchNewsPagingSource tests.
    // Here we only verify that the method doesn't crash and returns a Flow.

    @Test
    fun `searchNews should return non-null Flow for any query`() {
        // Given
        val testQueries = listOf(
            "android development",
            "",
            "covid-19 & vaccine news!",
            "special chars !@#$%"
        )

        testQueries.forEach { query ->
            // When
            val result = repository.searchNews(query)

            // Then
            assertNotNull("Failed for query: '$query'", result)
        }
    }
}
