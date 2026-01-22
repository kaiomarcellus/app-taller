package br.com.tallerapp

import br.com.tallerapp.core.network.createHttpClient
import br.com.tallerapp.core.api.GeocodingApi
import br.com.tallerapp.core.api.GeocodingApiImpl
import br.com.tallerapp.core.api.WeatherApi
import br.com.tallerapp.core.api.WeatherApiImpl
import br.com.tallerapp.ui.MainViewModel
import io.ktor.client.HttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val tallerModules = module {

    single<String>(named("BASE_URL")) { "https://geocoding-api.open-meteo.com/v1" }
    single<HttpClient> { createHttpClient() }
    single<WeatherApi> { WeatherApiImpl(get(named("BASE_URL")), get()) }
    single<GeocodingApi> { GeocodingApiImpl(get(named("BASE_URL")), get()) }

    viewModel { MainViewModel(get()) }

}