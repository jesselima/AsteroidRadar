package com.udacity.asteroidradar.features.main.data.datasource.local

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.features.main.domain.entities.PictureOfDay


interface PictureOfTheDayLocalDataSource {
	suspend fun getLocalPictureOfTheDay(date: String): LiveData<PictureOfDay>
	suspend fun savePictureOfTheDayToLocalDatabase(pictureOfDay: PictureOfDay)
}