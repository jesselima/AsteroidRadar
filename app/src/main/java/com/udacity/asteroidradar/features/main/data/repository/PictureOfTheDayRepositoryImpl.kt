package com.udacity.asteroidradar.features.main.data.repository

import com.udacity.asteroidradar.core.extensions.getCurrentDate
import com.udacity.asteroidradar.core.extensions.getDateSixDaysBehind
import com.udacity.asteroidradar.core.extensions.getLastSevenDays
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

	/**
	 * This request must have a connection available validation
	 */
	override suspend fun getRemotePictureOfTheDayByDate(date: String): PictureOfDay {
		val pictureOfTheDayByDate = pictureOfTheDayRemoteDataSource.getRemotePictureOfTheDayByDate(date = date)
		return pictureOfTheDayByDate?.mapToLocalDatabaseModel() ?: PictureOfDay()
	}

	override suspend fun getRemotePictureOfTheLastSevenDays() {
		val picturesOfTheLastSevenDays = pictureOfTheDayRemoteDataSource.getRemotePictureOfTheLastSevenDays(
			startDate = getCurrentDate(),
			endDate = getDateSixDaysBehind()
		)
		picturesOfTheLastSevenDays?.forEach {
			pictureOfTheDayLocalDataSource.savePictureOfTheDayToLocalDatabase(it.mapToLocalDatabaseModel())
		}
	}

	override suspend fun getLocalPictureOfTheDay() : PictureOfDay {
		return pictureOfTheDayLocalDataSource.getLocalPictureOfTheDay(date = getCurrentDate())
	}
}