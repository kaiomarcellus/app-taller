package br.com.tallerapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.tallerapp.ui.states.CityCoordinatesUiState
import br.com.tallerapp.ui.states.CityWeatherUiState
import br.com.tallerapp.ui.theme.TallerappTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun MainScreen() {
    TallerappTheme {

        val viewModel: MainViewModel = koinViewModel()
        val cityQueryChange by viewModel.citySearchQuery.collectAsStateWithLifecycle()
        val cityStateFlow by viewModel.cityStateFlow.collectAsStateWithLifecycle()
        val weatherStateFlow by viewModel.weatherStateFlow.collectAsStateWithLifecycle()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text("Taller Interview")
                    }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    value = cityQueryChange,
                    onValueChange = viewModel::onCitySearchQueryChanged,
                    placeholder = { Text("Type the name of a city..") },
                    singleLine = true
                )

                when (cityStateFlow) {
                    is CityCoordinatesUiState.Loading -> {
                        LoadingWeatherComponent("Loading Coordinates..")
                    }

                    is CityCoordinatesUiState.Error -> {
                        ErrorWeatherComponent()
                    }

                    is CityCoordinatesUiState.Success -> {
                        val cityCoordinates =
                            (cityStateFlow as? CityCoordinatesUiState.Success)?.data
                        LaunchedEffect(cityCoordinates?.latitude, cityCoordinates?.longitude) {
                            if (cityCoordinates != null) {
                                viewModel.fetchWeather(
                                    cityCoordinates.latitude,
                                    cityCoordinates.longitude
                                )
                            }
                        }
                    }

                    else -> {}
                }

                when (weatherStateFlow) {
                    is CityWeatherUiState.Loading -> {
                        LoadingWeatherComponent()
                    }

                    is CityWeatherUiState.Error -> {
                        ErrorWeatherComponent()
                    }

                    is CityWeatherUiState.Success -> {
                        val weatherData = (weatherStateFlow as CityWeatherUiState.Success).data
                        Card(
                            modifier = Modifier.padding(16.dp),
                            colors = CardDefaults.elevatedCardColors()
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("City: $cityQueryChange")
                                Text("The Current Weather is: ${weatherData.weather}")
                            }
                        }
                    }

                    else -> {}
                }

            }
        }
    }
}

@Composable
private fun LoadingWeatherComponent(message: String? = null) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message ?: "Loading Weather..")
        CircularProgressIndicator(
            modifier = Modifier
                .size(24.dp)
        )
    }
}

@Composable
private fun ErrorWeatherComponent(message: String? = null) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message ?: "Try another city name, please.")
    }
}