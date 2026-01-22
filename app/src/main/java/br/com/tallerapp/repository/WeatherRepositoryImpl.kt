package br.com.tallerapp.repository

import br.com.tallerapp.core.network.ApiResult
import br.com.tallerapp.core.api.GeocodingApi
import br.com.tallerapp.core.api.WeatherApi
import br.com.tallerapp.model.CityCoordinates
import br.com.tallerapp.model.CityWeather
import br.com.tallerapp.ui.states.CityCoordinatesUiState
import br.com.tallerapp.ui.states.CityWeatherUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WeatherRepositoryImpl(
    private val geocodingApi: GeocodingApi,
    private val weatherApi: WeatherApi
) : WeatherRepository {

    override suspend fun fetchCityCoordinates(cityName: String): Flow<CityCoordinatesUiState> {
        return flow {
            emit(value = CityCoordinatesUiState.Loading)
            when (val response = geocodingApi.searchCoordinatesByCity(cityName)) {
                is ApiResult.Success -> {
                    emit(
                        value = CityCoordinatesUiState.Success(
                            data = CityCoordinates(
                                latitude = response.data.results?.first()?.latitude ?: 0.0,
                                longitude = response.data.results?.first()?.longitude ?: 0.0,
                            )
                        )
                    )
                }

                is ApiResult.Failure -> {
                    emit(value = CityCoordinatesUiState.Error(response.errorMessage))
                }
            }
        }
    }

    override suspend fun fetchWeather(
        latitude: Double,
        longitude: Double
    ): Flow<CityWeatherUiState> {
        return flow {
            emit(value = CityWeatherUiState.Loading)
            when (val response = weatherApi.currentWeather(latitude, longitude)) {
                is ApiResult.Success -> {
                    emit(
                        value = CityWeatherUiState.Success(
                            data = CityWeather(
                                weather = "${response.data.currentWeather?.temperature ?: 0.0}${response.data.temperature ?: "ºC"}"
                            )
                        )
                    )
                }

                is ApiResult.Failure -> {
                    emit(value = CityWeatherUiState.Error(response.errorMessage))
                }
            }
        }
    }

}