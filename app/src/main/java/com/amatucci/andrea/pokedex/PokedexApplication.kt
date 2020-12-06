package com.amatucci.andrea.pokedex

import android.app.Application
import com.amatucci.andrea.pokedex.diModules.appModule
import com.amatucci.andrea.pokedex.diModules.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

@Suppress("unused")
class PokedexApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@PokedexApplication)
            modules(networkModule, appModule)
        }
    }
}