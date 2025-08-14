package com.amritthakur.newsapp.remote.response

import com.amritthakur.newsapp.remote.dto.ArticleDto
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsResponse(
    val totalResults: Int?,
    val articles: List<ArticleDto>?
)
