package com.udacity.asteroidradar.core

import android.app.Application
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.core.di.GlobalInjectableDependencies
import com.udacity.asteroidradar.features.mainscreen.workers.asteroids.AsteroidsWorkSetup
import com.udacity.asteroidradar.features.mainscreen.workers.pictures.PicturesOfTheDayWorkerSetup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Created by jesselima on 3/01/21.
 * This is a part of the project AsteroidsFeedItem Radar.
 */

class AsteroidRadarApplication : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        GlobalInjectableDependencies(this).initKoin()
        workersDelayedInit()
        setupPicassoInstance()
    }

    private fun setupPicassoInstance() {
        val picassoBuilder = Picasso.Builder(this)
        picassoBuilder.downloader(OkHttp3Downloader(this, Long.MAX_VALUE))
        val picasso = picassoBuilder.build()
        picasso.setIndicatorsEnabled(true)
        picasso.isLoggingEnabled = true
        Picasso.setSingletonInstance(picasso)
    }

    private fun workersDelayedInit() {
        applicationScope.launch {
            AsteroidsWorkSetup().delayedInit()
            PicturesOfTheDayWorkerSetup().delayedInit()
        }
    }
}