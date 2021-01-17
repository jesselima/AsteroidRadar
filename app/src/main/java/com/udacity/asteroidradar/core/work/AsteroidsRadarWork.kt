package com.udacity.asteroidradar.core.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.features.main.domain.reposirory.AsteroidsFeedRepository
import com.udacity.asteroidradar.features.main.domain.reposirory.PictureOfTheDayRepository
import retrofit2.HttpException
import timber.log.Timber

/**
 * Created by jesselima on 10/01/21.
 * This is a part of the project Asteroid Radar.
 */
class AsteroidsRadarWork(
	appContext: Context,
	params: WorkerParameters,
	private val asteroidsFeedRepository: AsteroidsFeedRepository,
	private val pictureOfTheDayRepository: PictureOfTheDayRepository
) : CoroutineWorker(appContext, params) {

	override suspend fun doWork(): Result {

		Timber.d("AsteroidsRadarWork - Starting work...")

		return try {
			asteroidsFeedRepository.getRemoteFeed()
			pictureOfTheDayRepository.getRemotePictureOfTheDay()
			Timber.d("AsteroidsRadarWork - Work Success!")
			Result.success()
		} catch (exception: HttpException) {
			Timber.d("AsteroidsRadarWork - Work Failure!!! It will retry soon")
			Result.retry()
		}
	}
}