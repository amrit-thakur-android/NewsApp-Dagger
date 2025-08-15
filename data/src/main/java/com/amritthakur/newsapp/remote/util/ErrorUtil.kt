package com.amritthakur.newsapp.remote.util

import com.amritthakur.newsapp.common.Result.Error
import com.amritthakur.newsapp.remote.dto.ErrorDto
import com.squareup.moshi.Moshi
import retrofit2.Response

fun Response<*>.toError(): Error {
    val backendError = this.errorBody()?.string().toErrorDto()
    return Error(
        code = this.code(),
        errorCode = backendError?.code,
        errorMessage = backendError?.message
    )
}

fun Exception.toError(): Error {
    return Error(
        code = -1, // Custom code for exceptions
        errorCode = this::class.simpleName,
        errorMessage = this.message
    )
}

private fun String?.toErrorDto(): ErrorDto? {
    return try {
        if (this.isNullOrBlank()) return null
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(ErrorDto::class.java)
        adapter.fromJson(this)
    } catch (_: Exception) {
        null // If parsing fails, return null
    }
}
