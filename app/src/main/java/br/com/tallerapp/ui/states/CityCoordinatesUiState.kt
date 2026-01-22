package br.com.tallerapp.ui.states

import br.com.tallerapp.model.CityCoordinates

sealed interface CityCoordinatesUiState {

    data object Idle : CityCoordinatesUiState
    data object Loading : CityCoordinatesUiState

    data class Success(
        val data: CityCoordinates
    ) : CityCoordinatesUiState

    data class Error(
        val message: String
    ) : CityCoordinatesUiState
}