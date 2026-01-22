package br.com.tallerapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TallerApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TallerApp)
            modules(tallerModules)
        }
    }

}