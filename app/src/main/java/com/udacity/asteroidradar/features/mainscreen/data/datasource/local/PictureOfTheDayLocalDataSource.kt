package com.udacity.asteroidradar.features.mainscreen.data.datasource.local

import com.udacity.asteroidradar.features.mainscreen.domain.entities.PictureOfDay


interface PictureOfTheDayLocalDataSource {
	suspend fun getLocalPictureOfTheDay(date: String): PictureOfDay
	suspend fun getLocalPictureOfTheLastSevenDays(startDate: String, endDate: String): List<PictureOfDay>
	suspend fun getAllLocalFavoritesPicturesOfTheDay(): List<PictureOfDay>
	suspend fun savePictureOfTheDayToLocalDatabase(pictureOfDay: PictureOfDay)
	suspend fun toggleFavoritePictureState(pictureOfDay: PictureOfDay) : Int
}