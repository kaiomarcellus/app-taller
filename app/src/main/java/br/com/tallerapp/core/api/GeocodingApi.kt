package br.com.tallerapp.core.api

import br.com.tallerapp.core.network.ApiResult
import br.com.tallerapp.core.network.safeRequest
import br.com.tallerapp.core.api.GeocodingApi.Companion.GEOCODING_API
import br.com.tallerapp.core.model.CityGeocodingResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.contentType

interface GeocodingApi {

    companion object {
        const val GEOCODING_API = "search"
    }

    suspend fun searchCoordinatesByCity(
        cityName: String
    ): ApiResult<List<CityGeocodingResponse>>

}

class GeocodingApiImpl(
    private val baseUrl: String,
    private val httpClient: HttpClient
) : GeocodingApi {

    override suspend fun searchCoordinatesByCity(
        cityName: String
    ): ApiResult<List<CityGeocodingResponse>> {
        return httpClient.safeRequest {
            get("$baseUrl/$GEOCODING_API") {
                contentType(ContentType.Application.Json)
                parameter("name", cityName)
            }
        }
    }

}