package br.com.tallerapp.core.model

import kotlinx.serialization.Serializable

@Serializable
data class CityGeocodingResponse(
    val results: List<CityGeocodingItemResponse>? = null
)