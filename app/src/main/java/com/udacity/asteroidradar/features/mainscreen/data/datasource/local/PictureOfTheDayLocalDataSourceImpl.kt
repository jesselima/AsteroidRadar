package com.udacity.asteroidradar.features.mainscreen.data.datasource.local

import android.app.Application
import com.udacity.asteroidradar.core.database.AppDatabase
import com.udacity.asteroidradar.core.extensions.getCurrentDate
import com.udacity.asteroidradar.features.mainscreen.domain.entities.PictureOfDay


class PictureOfTheDayLocalDataSourceImpl(
    application: Application
): PictureOfTheDayLocalDataSource {

    private val pictureOfTheDayDao = AppDatabase.getDatabase(application).pictureOfTheDayDao()

    override suspend fun getLocalPictureOfTheDay(date: String): PictureOfDay? {
        return pictureOfTheDayDao.getLocalPictureOfTheDay(date = date)
    }

	override suspend fun getLocalPictureOfTheDayById(id: Long): PictureOfDay? {
		return pictureOfTheDayDao.getLocalPictureOfTheDayById(id = id)
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

	override suspend fun getAllLocalFavoritesPicturesOfTheDay(): List<PictureOfDay> {
		return pictureOfTheDayDao.getAllLocalFavoritesPicturesOfTheDay()
	}

	override suspend fun savePictureOfTheDayToLocalDatabase(pictureOfDay: PictureOfDay) {
        pictureOfDay.createdAt = getCurrentDate()
        pictureOfDay.modifiedAt = getCurrentDate()
        pictureOfTheDayDao.insert(pictureOfDay)
    }

    override suspend fun updateFavoritePictureState(pictureOfDay: PictureOfDay): Int {
        return pictureOfTheDayDao.update(pictureOfDay)
    }

	override suspend fun deleteAllPictures(): Int {
		return pictureOfTheDayDao.deleteAllPictures()
	}

	override suspend fun deleteFavoritesOnly(): Int {
		return pictureOfTheDayDao.deleteFavoritesOnly()
	}

	override suspend fun deleteNotFavoritesOnly(): Int {
		return pictureOfTheDayDao.deleteNotFavoritesOnly()
	}

	override suspend fun resetFavorites(): Int {
		return pictureOfTheDayDao.resetFavorites()
	}

	override suspend fun deletePictureOfTheDayByDate(date: String): Int {
		return pictureOfTheDayDao.deletePictureOfTheDayByDate(date = date)
	}
}