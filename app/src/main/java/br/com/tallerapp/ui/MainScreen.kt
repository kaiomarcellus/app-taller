package br.com.tallerapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
        val cityCoordinatesUiState = viewModel.cityStateFlow.collectAsStateWithLifecycle()
        val currentWeatherUiState = viewModel.currentWeather.collectAsStateWithLifecycle()

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
                    .padding(padding)
            ) {

                TextField(
                    value = cityQueryChange,
                    onValueChange = { viewModel.onCitySearchQueryChanged(it) },
                    placeholder = { Text("Type the name of a city..") }
                )

                when (val state = cityCoordinatesUiState.value) {
                    is CityCoordinatesUiState.Error -> {

                    }

                    is CityCoordinatesUiState.Success -> {
                        viewModel.fetchWeather(state.data.latitude, state.data.longitude)
                    }

                    else -> {}
                }

                when (val state = currentWeatherUiState.value) {
                    is CityWeatherUiState.Error -> {

                    }

                    is CityWeatherUiState.Success -> {

                    }

                    else -> {}
                }

            }
        }
    }
}