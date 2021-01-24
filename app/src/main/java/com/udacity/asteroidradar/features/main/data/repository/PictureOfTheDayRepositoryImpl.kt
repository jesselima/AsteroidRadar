package com.udacity.asteroidradar.features.main.data.repository

import com.udacity.asteroidradar.core.extensions.getCurrentDate
import com.udacity.asteroidradar.core.extensions.getDateForDaysBehind
import com.udacity.asteroidradar.features.main.data.datasource.local.PictureOfTheDayLocalDataSource
import com.udacity.asteroidradar.features.main.data.datasource.remote.PictureOfTheDayRemoteDataSource
import com.udacity.asteroidradar.features.main.data.models.mapToLocalDatabaseModel
import com.udacity.asteroidradar.features.main.domain.entities.PictureOfDay
import com.udacity.asteroidradar.features.main.domain.reposirory.PictureOfTheDayRepository

/**
 * Created by jesselima on 9/01/21.
 * This is a part of the project Asteroid Radar.
 */
class PictureOfTheDayRepositoryImpl(
		private val pictureOfTheDayLocalDataSource: PictureOfTheDayLocalDataSource,
		private val pictureOfTheDayRemoteDataSource: PictureOfTheDayRemoteDataSource
) : PictureOfTheDayRepository {

	override suspend fun getRemotePictureOfTheDay() {
		val pictureOfTheDayResponse = pictureOfTheDayRemoteDataSource.getRemotePictureOfTheDay()
		pictureOfTheDayResponse?.let {
			pictureOfTheDayLocalDataSource.savePictureOfTheDayToLocalDatabase(it.mapToLocalDatabaseModel())
		}
	}

	override suspend fun getRemotePictureOfTheDayByDate(date: String) {
		val pictureOfTheDayByDate = pictureOfTheDayRemoteDataSource.getRemotePictureOfTheDayByDate(date = date)
		pictureOfTheDayByDate?.mapToLocalDatabaseModel() ?: PictureOfDay()
	}

	override suspend fun getLocalPictureOfTheDayByDate(date: String): PictureOfDay {
		return pictureOfTheDayLocalDataSource.getLocalPictureOfTheDay(date = date)
	}

	override suspend fun getRemotePictureOfTheLastSevenDays() {
		val picturesOfTheLastSevenDays = pictureOfTheDayRemoteDataSource.getRemotePictureOfTheLastSevenDays(
			startDate = getDateForDaysBehind(),
			endDate = getCurrentDate()
		)
		picturesOfTheLastSevenDays?.forEach {
			pictureOfTheDayLocalDataSource.savePictureOfTheDayToLocalDatabase(it.mapToLocalDatabaseModel())
		}
	}

	override suspend fun getLocalPictureOfTheLastSevenDays(): List<PictureOfDay> {
		return  pictureOfTheDayLocalDataSource.getLocalPictureOfTheLastSevenDays(
			startDate = getDateForDaysBehind(),
			endDate = getCurrentDate()
		)
	}
}