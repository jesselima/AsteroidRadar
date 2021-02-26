package com.udacity.asteroidradar.core.work.asteroids

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.features.mainscreen.domain.reposirory.AsteroidsFeedRepository
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.HttpException

/**
 * Created by jesselima on 10/01/21.
 * This is a part of the project Asteroid Radar.
 */
class AsteroidsWorkReceiver(
	appContext: Context,
	params: WorkerParameters,
) : CoroutineWorker(appContext, params), KoinComponent {

	private val asteroidsFeedRepository: AsteroidsFeedRepository by inject()

	override suspend fun doWork(): Result {
		return try {
			asteroidsFeedRepository.getRemoteFeed()
			Result.success()
		} catch (exception: HttpException) {
			Result.retry()
		}
	}
}