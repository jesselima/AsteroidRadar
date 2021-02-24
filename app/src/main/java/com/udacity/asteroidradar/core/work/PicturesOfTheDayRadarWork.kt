package com.udacity.asteroidradar.core.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.features.mainscreen.domain.reposirory.PictureOfTheDayRepository
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.HttpException
import timber.log.Timber

/**
 * Created by jesselima on 10/01/21.
 * This is a part of the project Asteroid Radar.
 */
class PicturesOfTheDayRadarWork(
	appContext: Context,
	params: WorkerParameters,
) : CoroutineWorker(appContext, params), KoinComponent {

	private val pictureOfTheDayRepository: PictureOfTheDayRepository by inject()

	override suspend fun doWork(): Result {
		Timber.d("PicturesOfTheDayRadarWork - Starting work...")
		return try {
			pictureOfTheDayRepository.getRemotePictureOfTheDay()
			Timber.d("PicturesOfTheDayRadarWork - Work Success!")
			Result.success()
		} catch (exception: HttpException) {
			Timber.d("PicturesOfTheDayRadarWork - Work Failure!!! It will retry soon")
			Result.retry()
		}
	}
}