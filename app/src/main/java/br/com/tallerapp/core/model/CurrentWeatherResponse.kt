package br.com.tallerapp.core.model

import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherResponse(
    val temperature: Double? = null
)