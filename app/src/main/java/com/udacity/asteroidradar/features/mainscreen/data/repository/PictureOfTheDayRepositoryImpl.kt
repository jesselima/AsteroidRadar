package com.udacity.asteroidradar.features.mainscreen.data.repository

import com.udacity.asteroidradar.core.api.ResultEither
import com.udacity.asteroidradar.core.api.flow
import com.udacity.asteroidradar.core.extensions.getCurrentDate
import com.udacity.asteroidradar.core.extensions.getDateForDaysBehind
import com.udacity.asteroidradar.features.mainscreen.data.datasource.local.PictureOfTheDayLocalDataSource
import com.udacity.asteroidradar.features.mainscreen.data.datasource.remote.PictureOfTheDayRemoteDataSource
import com.udacity.asteroidradar.features.mainscreen.data.models.mapToLocalDatabaseModel
import com.udacity.asteroidradar.features.mainscreen.domain.entities.PictureOfDay
import com.udacity.asteroidradar.features.mainscreen.domain.reposirory.PictureOfTheDayRepository

/**
 * Created by jesselima on 9/01/21.
 * This is a part of the project Asteroid Radar.
 */
class PictureOfTheDayRepositoryImpl(
		private val pictureOfTheDayLocalDataSource: PictureOfTheDayLocalDataSource,
		private val pictureOfTheDayRemoteDataSource: PictureOfTheDayRemoteDataSource
) : PictureOfTheDayRepository {

	override suspend fun getRemotePictureOfTheDay() {
		pictureOfTheDayRemoteDataSource.getRemotePictureOfTheDay()
			.flow(
				{ pictureOfTheDayResponse ->
					pictureOfTheDayResponse?.let {
						pictureOfTheDayLocalDataSource.savePictureOfTheDayToLocalDatabase(
							it.mapToLocalDatabaseModel()
						)
					}
				},
				{
					ResultEither.Failure(it)
				}
			)
	}

	override suspend fun getRemotePictureOfTheDayByDate(date: String) {
		pictureOfTheDayRemoteDataSource.getRemotePictureOfTheDayByDate(date = date)
			.flow(
				{
					it?.mapToLocalDatabaseModel() ?: PictureOfDay()
				},
				{
					ResultEither.Failure(it)
				}
			)
	}

	override suspend fun getRemotePictureOfTheLastSevenDays() {
		pictureOfTheDayRemoteDataSource.getRemotePictureOfTheLastSevenDays(
			startDate = getDateForDaysBehind(),
			endDate = getCurrentDate()
		).flow(
			{ picturesOfTheDayResponse ->
				picturesOfTheDayResponse?.forEach {
					pictureOfTheDayLocalDataSource.savePictureOfTheDayToLocalDatabase(
						it.mapToLocalDatabaseModel()
					)
				}
			},
			{
				ResultEither.Failure(it)
			}
		)
	}

	override suspend fun getLocalPictureOfTheDayByDate(date: String): PictureOfDay {
		return pictureOfTheDayLocalDataSource.getLocalPictureOfTheDay(date = date)
	}

	override suspend fun getLocalPictureOfTheLastSevenDays(): List<PictureOfDay> {
		return  pictureOfTheDayLocalDataSource.getLocalPictureOfTheLastSevenDays(
			startDate = getDateForDaysBehind(),
			endDate = getCurrentDate()
		)
	}

	override suspend fun toggleFavoritePictureState(pictureOfDay: PictureOfDay): Int {
		return pictureOfTheDayLocalDataSource.toggleFavoritePictureState(pictureOfDay)
	}
}