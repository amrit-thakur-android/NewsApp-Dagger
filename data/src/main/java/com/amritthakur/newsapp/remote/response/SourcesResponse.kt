package com.amritthakur.newsapp.remote.response

import com.amritthakur.newsapp.remote.dto.SourceDto
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SourcesResponse(
    val sources: List<SourceDto>?
)
