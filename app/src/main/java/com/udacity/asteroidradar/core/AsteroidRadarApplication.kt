package com.udacity.asteroidradar.core

import android.app.Application
import com.udacity.asteroidradar.core.di.GlobalInjectableDependencies
import timber.log.Timber

/**
 * Created by jesselima on 3/01/21.
 * This is a part of the project AsteroidsFeedItem Radar.
 */
class AsteroidRadarApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        GlobalInjectableDependencies(this).initKoin()
    }
}