package br.com.tallerapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.tallerapp.repository.WeatherRepository
import br.com.tallerapp.ui.states.CityCoordinatesUiState
import br.com.tallerapp.ui.states.CityWeatherUiState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class MainViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _citySearchQuery = MutableStateFlow("")
    val citySearchQuery: StateFlow<String> = _citySearchQuery

    private val _cityCoordinates =
        MutableStateFlow<CityCoordinatesUiState>(CityCoordinatesUiState.Idle)
    val cityStateFlow: StateFlow<CityCoordinatesUiState> = _cityCoordinates

    private val _currentWeather = MutableStateFlow<CityWeatherUiState>(CityWeatherUiState.Idle)
    val currentWeather: StateFlow<CityWeatherUiState> = _currentWeather


    init {
        viewModelScope.launch {
            _citySearchQuery
                .debounce(500)
                .filter { it.length > 2 }
                .distinctUntilChanged()
                .collectLatest { query ->
                    fetchCityCoordinates(query)
                }
        }
    }

    fun onCitySearchQueryChanged(query: String) {
        _citySearchQuery.value = query
    }

    private fun fetchCityCoordinates(cityName: String) {
        viewModelScope.launch {
            weatherRepository.fetchCityCoordinates(cityName).collect {
                _cityCoordinates.value = it
            }
        }
    }

    fun fetchWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            weatherRepository.fetchWeather(latitude, longitude).collect {
                _currentWeather.value = it
            }
        }
    }

}