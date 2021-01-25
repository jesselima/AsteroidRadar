package com.udacity.asteroidradar.core

import android.app.Application
import android.os.Build
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.core.di.GlobalInjectableDependencies
import com.udacity.asteroidradar.core.work.AsteroidsRadarWork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Created by jesselima on 3/01/21.
 * This is a part of the project AsteroidsFeedItem Radar.
 */

private const val REPEAT_WORK_INTERVAL = 1L

class AsteroidRadarApplication : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        GlobalInjectableDependencies(this).initKoin()
        delayedInit()
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

    private fun delayedInit() {
        applicationScope.launch {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresBatteryNotLow(true)
                .setRequiresCharging(false)
                .apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        setRequiresDeviceIdle(true)
                    }
                }.build()

            val repeatingWorkedRequest = PeriodicWorkRequestBuilder<AsteroidsRadarWork>(
                REPEAT_WORK_INTERVAL,
                TimeUnit.DAYS
            ).setConstraints(constraints)
            .build()

            WorkManager.getInstance().enqueueUniquePeriodicWork(
                AsteroidsRadarWork::class.java.simpleName,
                ExistingPeriodicWorkPolicy.KEEP,
                repeatingWorkedRequest
            )
        }
    }
}