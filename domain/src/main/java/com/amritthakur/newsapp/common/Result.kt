package com.amritthakur.newsapp.common

sealed class Result<out T> {

    data class Success<T>(val data: T) : Result<T>()

    data class Error(
        val code: Int,
        val errorCode: String?,
        val errorMessage: String?
    ) : Result<Nothing>()
}
