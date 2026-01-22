package br.com.tallerapp.core.model

import kotlinx.serialization.Serializable

@Serializable
data class CityGeocodingItemResponse(
    val name: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null
)