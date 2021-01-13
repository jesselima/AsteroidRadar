package com.udacity.asteroidradar.features.main.data.datasource.local

import android.app.Application
import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.database.AppDatabase
import com.udacity.asteroidradar.features.main.domain.entities.PictureOfDay


class PictureOfTheDayLocalDataSourceImpl(
    application: Application
): PictureOfTheDayLocalDataSource {

    private val pictureOfTheDayDao = AppDatabase.getDatabase(application).pictureOfTheDay()

    override suspend fun getLocalPictureOfTheDay(date: String): LiveData<PictureOfDay> {
        return pictureOfTheDayDao.getLocalPictureOfTheDay(date = date)
    }

    override suspend fun savePictureOfTheDayToLocalDatabase(pictureOfDay: PictureOfDay) {
        pictureOfDay.createdAt = System.currentTimeMillis()
        pictureOfDay.modifiedAt = System.currentTimeMillis()
        pictureOfTheDayDao.insert(pictureOfDay)
    }
}