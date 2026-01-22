package br.com.tallerapp.ui.states

import br.com.tallerapp.model.CityWeather

sealed interface CityWeatherUiState {

    data object Idle : CityWeatherUiState
    data object Loading : CityWeatherUiState

    data class Success(
        val data: CityWeather
    ) : CityWeatherUiState

    data class Error(
        val message: String
    ) : CityWeatherUiState
}