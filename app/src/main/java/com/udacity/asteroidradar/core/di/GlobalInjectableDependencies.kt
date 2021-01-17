package com.udacity.asteroidradar.core.di

import android.content.Context
import com.udacity.asteroidradar.core.sharedprefs.di.SharedPrefModule
import com.udacity.asteroidradar.features.main.di.AsteroidsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


internal class GlobalInjectableDependencies(
    private val applicationContext: Context
) {
    fun initKoin() {
        startKoin {
            androidLogger(level = Level.DEBUG)
            androidContext(applicationContext)
            modules(
                listOf(
                    AsteroidsModule.loadModuleDependency(),
                    SharedPrefModule.loadModuleDependency(),
                )
            )
        }
    }
}

