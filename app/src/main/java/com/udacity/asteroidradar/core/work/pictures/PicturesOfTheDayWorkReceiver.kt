package com.udacity.asteroidradar.core.work.pictures

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.features.mainscreen.domain.reposirory.PictureOfTheDayRepository
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.HttpException

/**
 * Created by jesselima on 10/01/21.
 * This is a part of the project Asteroid Radar.
 */
class PicturesOfTheDayWorkReceiver(
	appContext: Context,
	params: WorkerParameters,
) : CoroutineWorker(appContext, params), KoinComponent {

	private val pictureOfTheDayRepository: PictureOfTheDayRepository by inject()

	override suspend fun doWork(): Result {
		return try {
			pictureOfTheDayRepository.getRemotePictureOfTheLastSevenDays()
			Result.success()
		} catch (exception: HttpException) {
			Result.retry()
		}
	}
}