package com.amritthakur.newsapp.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArticleDto(
    val source: ArticleSourceDto?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
)
