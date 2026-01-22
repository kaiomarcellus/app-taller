package br.com.tallerapp.core.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.SerializationException

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Failure(val errorMessage: String) : ApiResult<Nothing>()
}

suspend inline fun <reified T> HttpResponse.handleResponse(): ApiResult<T> {
    return when (status.value) {
        in 200..299 -> {
            try {
                val result = if (T::class == Unit::class) Unit as T else body<T>()
                ApiResult.Success(result)
            } catch (e: SerializationException) {
                ApiResult.Failure("Serialization error: ${e.message}")
            }
        }
        else -> {
            val errorBody = bodyAsText()
            ApiResult.Failure("Error ${status.value}: $errorBody")
        }
    }
}

suspend inline fun <reified T> HttpClient.safeRequest(
    crossinline block: suspend HttpClient.() -> HttpResponse
): ApiResult<T> {
    return try {
        val response = block()
        response.handleResponse<T>()
    } catch (e: Exception) {
        ApiResult.Failure("Network error: ${e.message}")
    }
}