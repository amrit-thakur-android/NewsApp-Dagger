package com.amritthakur.newsapp.common

sealed class DomainError {

    // All backend errors

    // 1. API Key errors
    data class ApiKeyDisabled(val message: String) : DomainError()
    data class ApiKeyExhausted(val message: String) : DomainError()
    data class ApiKeyInvalid(val message: String) : DomainError()
    data class ApiKeyMissing(val message: String) : DomainError()

    // 2. Parameter errors
    data class ParameterInvalid(val message: String) : DomainError()
    data class ParametersMissing(val message: String) : DomainError()

    // 3. Rate limiting errors
    data class RateLimited(val message: String) : DomainError()

    // 4. Source related errors
    data class SourcesTooMany(val message: String) : DomainError()
    data class SourceDoesNotExist(val message: String) : DomainError()

    // Other errors

    data class UnexpectedError(val message: String) : DomainError()
    object NetworkError : DomainError()
    object ParsingError : DomainError()
}
