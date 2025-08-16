package com.amritthakur.newsapp.common

fun Result.Error.toDomainError(): DomainError {
    return when (code) {
        400 -> {
            when (errorCode) {
                "parameterInvalid" -> DomainError.ParameterInvalid(
                    errorMessage ?: "Invalid parameter in request"
                )

                "parametersMissing" -> DomainError.ParametersMissing(
                    errorMessage ?: "Required parameters are missing"
                )

                "sourcesTooMany" -> DomainError.SourcesTooMany(errorMessage ?: "Too many sources")
                "sourceDoesNotExist" -> DomainError.SourceDoesNotExist(
                    errorMessage ?: "Source does not exist"
                )

                else -> DomainError.UnexpectedError(errorMessage ?: "Unexpected error occurred")
            }
        }

        401 -> {
            when (errorCode) {
                "apiKeyDisabled" -> DomainError.ApiKeyDisabled(errorMessage ?: "API key disabled")
                "apiKeyExhausted" -> DomainError.ApiKeyExhausted(
                    errorMessage ?: "API key exhausted"
                )

                "apiKeyInvalid" -> DomainError.ApiKeyInvalid(errorMessage ?: "API key invalid")
                "apiKeyMissing" -> DomainError.ApiKeyMissing(errorMessage ?: "API key missing")
                else -> DomainError.UnexpectedError(errorMessage ?: "Unexpected error occurred")
            }
        }

        429 -> {
            when (errorCode) {
                "rateLimited" -> DomainError.RateLimited(errorMessage ?: "Rate limited")
                else -> DomainError.UnexpectedError(errorMessage ?: "Unexpected error occurred")
            }
        }

        500 -> {
            DomainError.UnexpectedError(errorMessage ?: "Unexpected error occurred")
        }

        -1 -> {
            when (errorCode) {
                "IOException",
                "SocketTimeoutException",
                "ConnectException",
                "UnknownHostException",
                "SocketException" -> DomainError.NetworkError("Network error occurred")

                else -> DomainError.UnexpectedError(errorMessage ?: "Unknown exception occurred")
            }
        }

        -2 -> {
            DomainError.ParsingError("Parsing error occurred")
        }

        else -> DomainError.UnexpectedError(errorMessage ?: "Unexpected error occurred")
    }
}
