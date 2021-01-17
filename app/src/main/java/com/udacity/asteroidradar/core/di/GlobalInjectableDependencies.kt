package com.udacity.asteroidradar.core.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

import com.udacity.asteroidradar.features.main.di.AsteroidsModule
import com.udacity.asteroidradar.sharedprefs.di.SharedPrefModule


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

