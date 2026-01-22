package br.com.tallerapp.core.api

import br.com.tallerapp.core.network.ApiResult
import br.com.tallerapp.core.network.safeRequest
import br.com.tallerapp.core.api.WeatherApi.Companion.FORECAST_API
import br.com.tallerapp.core.model.CurrentWeatherResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.contentType

interface WeatherApi {

    companion object {
        const val FORECAST_API = "forecast"
    }

    suspend fun currentWeather(
        latitude: Double,
        longitude: Double
    ): ApiResult<CurrentWeatherResponse>

}

class WeatherApiImpl(
    private val baseUrl: String,
    private val httpClient: HttpClient
) : WeatherApi {

    override suspend fun currentWeather(
        latitude: Double,
        longitude: Double
    ): ApiResult<CurrentWeatherResponse> {
        return httpClient.safeRequest {
            get("$baseUrl/$FORECAST_API") {
                contentType(ContentType.Application.Json)
                parameter("latitude", latitude)
                parameter("longitude", longitude)
                parameter("current_weather", true)
            }
        }
    }

}