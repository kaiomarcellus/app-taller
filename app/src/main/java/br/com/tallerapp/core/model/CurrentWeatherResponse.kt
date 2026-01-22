package br.com.tallerapp.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherResponse(
    val temperature: String? = null,
    @SerialName("current_weather")
    val currentWeather: CurrentWeatherItem? = null
)