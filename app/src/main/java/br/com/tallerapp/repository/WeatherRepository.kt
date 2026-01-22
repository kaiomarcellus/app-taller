package br.com.tallerapp.repository

import br.com.tallerapp.ui.states.CityCoordinatesUiState
import br.com.tallerapp.ui.states.CityWeatherUiState
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun fetchCityCoordinates(cityName: String): Flow<CityCoordinatesUiState>

    suspend fun fetchWeather(latitude: Double, longitude: Double): Flow<CityWeatherUiState>

}