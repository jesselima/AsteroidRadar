package com.udacity.asteroidradar.features.mainscreen.domain.usecase

import com.udacity.asteroidradar.features.mainscreen.domain.entities.PictureOfDay
import com.udacity.asteroidradar.features.mainscreen.domain.reposirory.PictureOfTheDayRepository

/**
 * Created by jesselima on 9/01/21.
 * This is a part of the project Asteroid Radar.
 */
class PictureOfTheDayUseCaseImpl(
	private val repository: PictureOfTheDayRepository
) : PictureOfTheDayUseCase {

	override suspend fun getRemotePictureOfTheDayLatestDays() {
		repository.getRemotePictureOfTheDayLatestDays()
	}

	override suspend fun getLocalPictureOfTheDayLastSevenDays(): List<PictureOfDay> {
		return repository.getLocalPictureOfTheDayLatestDays()
	}

	override suspend fun updateFavoritePictureState(pictureOfDay: PictureOfDay): Int {
		return repository.updateFavoritePictureState(pictureOfDay)
	}

	override suspend fun getLocalPictureOfTheDayByDate(date: String) : PictureOfDay? {
		return repository.getLocalPictureOfTheDayByDate(date = date)
	}

	override suspend fun getLocalPictureOfTheDayById(id: Long): PictureOfDay? {
		return repository.getLocalPictureOfTheDayById(id = id)
	}

	override suspend fun getAllLocalFavoritesPicturesOfTheDay(): List<PictureOfDay> {
		return repository.getAllLocalFavoritesPicturesOfTheDay()
	}

	override suspend fun getRemotePictureOfTheDayByDate(date: String) {
		return repository.getRemotePictureOfTheDayByDate(date = date)
	}

	override suspend fun deleteAllPictures(): Int {
		return repository.deleteAllPictures()
	}

	override suspend fun deleteFavoritesOnly(): Int {
		return repository.deleteFavoritesOnly()
	}

	override suspend fun deleteNotFavoritesOnly(): Int {
		return repository.deleteNotFavoritesOnly()
	}

	override suspend fun resetFavorites(): Int {
		return repository.resetFavorites()
	}

	override suspend fun deletePictureOfTheDayByDate(date: String): Int {
		return repository.deletePictureOfTheDayByDate(date = date)
	}

}