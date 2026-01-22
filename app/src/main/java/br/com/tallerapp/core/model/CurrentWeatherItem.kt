package br.com.tallerapp.core.model

import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherItem(
    val temperature: Double? = null
)