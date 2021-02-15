package com.udacity.asteroidradar.features.mainscreen.data.datasource.local

import android.app.Application
import com.udacity.asteroidradar.core.database.AppDatabase
import com.udacity.asteroidradar.core.extensions.getCurrentDate
import com.udacity.asteroidradar.features.mainscreen.domain.entities.PictureOfDay


class PictureOfTheDayLocalDataSourceImpl(
    application: Application
): PictureOfTheDayLocalDataSource {

    private val pictureOfTheDayDao = AppDatabase.getDatabase(application).pictureOfTheDayDao()

    override suspend fun getLocalPictureOfTheDay(date: String): PictureOfDay {
        return pictureOfTheDayDao.getLocalPictureOfTheDay(date = date)
    }

    override suspend fun getLocalPictureOfTheLastSevenDays(
        startDate: String,
        endDate: String
    ): List<PictureOfDay> {
        return pictureOfTheDayDao.getLocalPictureOfTheDayLastSevenDays(
            startDate = startDate,
            endDate = endDate
        )
    }

    override suspend fun savePictureOfTheDayToLocalDatabase(pictureOfDay: PictureOfDay) {
        pictureOfDay.createdAt = getCurrentDate()
        pictureOfDay.modifiedAt = getCurrentDate()
        pictureOfTheDayDao.insert(pictureOfDay)
    }
}