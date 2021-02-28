package com.udacity.asteroidradar.features.mainscreen.workers.pictures

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

/**
 * Created by jesselima on 26/02/21.
 * This is a part of the project Asteroid Radar.
 */

private const val REPEAT_WORK_INTERVAL = 1L

class PicturesOfTheDayWorkerSetup {

	private val applicationScope = CoroutineScope(Dispatchers.Default)

	fun delayedInit() {
		applicationScope.launch {
			val constraints = Constraints.Builder()
				.setRequiredNetworkType(NetworkType.CONNECTED)
				.setRequiresBatteryNotLow(true)
				.setRequiresCharging(true)
				.build()

			val picturesPeriodicWorkRequest = PeriodicWorkRequestBuilder<PicturesOfTheDayWorkReceiver>(
				REPEAT_WORK_INTERVAL,
				TimeUnit.DAYS
			).setConstraints(constraints).build()
			WorkManager.getInstance().enqueueUniquePeriodicWork(
				PicturesOfTheDayWorkReceiver::class.java.simpleName,
				ExistingPeriodicWorkPolicy.KEEP,
				picturesPeriodicWorkRequest
			)
		}
	}
}