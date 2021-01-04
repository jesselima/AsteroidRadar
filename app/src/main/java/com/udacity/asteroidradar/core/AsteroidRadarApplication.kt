package com.udacity.asteroidradar.core

import android.app.Application
import timber.log.Timber

/**
 * Created by jesselima on 3/01/21.
 * This is a part of the project Asteroid Radar.
 */
class AsteroidRadarApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}