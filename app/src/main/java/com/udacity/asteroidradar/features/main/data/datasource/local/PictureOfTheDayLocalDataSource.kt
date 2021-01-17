package com.udacity.asteroidradar.features.main.data.datasource.local

import com.udacity.asteroidradar.features.main.domain.entities.PictureOfDay


interface PictureOfTheDayLocalDataSource {
	suspend fun getLocalPictureOfTheDay(date: String): PictureOfDay
	suspend fun savePictureOfTheDayToLocalDatabase(pictureOfDay: PictureOfDay)
}