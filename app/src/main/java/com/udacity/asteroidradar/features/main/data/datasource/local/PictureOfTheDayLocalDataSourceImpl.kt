package com.udacity.asteroidradar.features.main.data.datasource.local

import android.app.Application
import com.udacity.asteroidradar.core.database.AppDatabase
import com.udacity.asteroidradar.core.extensions.getCurrentDate
import com.udacity.asteroidradar.features.main.domain.entities.PictureOfDay


class PictureOfTheDayLocalDataSourceImpl(
    application: Application
): PictureOfTheDayLocalDataSource {

    private val pictureOfTheDayDao = AppDatabase.getDatabase(application).pictureOfTheDay()

    override suspend fun getLocalPictureOfTheDay(date: String): PictureOfDay {
        return pictureOfTheDayDao.getLocalPictureOfTheDay(date = date)
    }

    override suspend fun savePictureOfTheDayToLocalDatabase(pictureOfDay: PictureOfDay) {
        pictureOfDay.createdAt = getCurrentDate()
        pictureOfDay.modifiedAt = getCurrentDate()
        pictureOfTheDayDao.insert(pictureOfDay)
    }
}