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

	override suspend fun getRemotePictureOfTheLastSevenDays() {
		repository.getRemotePictureOfTheLastSevenDays()
	}

	override suspend fun getLocalPictureOfTheDayLastSevenDays(): List<PictureOfDay> {
		return repository.getLocalPictureOfTheLastSevenDays()
	}

	override suspend fun toggleFavoritePictureState(pictureOfDay: PictureOfDay): Int {
		return repository.toggleFavoritePictureState(pictureOfDay)
	}

	override suspend fun getLocalPictureOfTheDayByDate(date: String) : PictureOfDay {
		return repository.getLocalPictureOfTheDayByDate(date = date)
	}

	override suspend fun getAllLocalFavoritesPicturesOfTheDay(): List<PictureOfDay> {
		return repository.getAllLocalFavoritesPicturesOfTheDay()
	}

	override suspend fun getRemotePictureOfTheDayByDate(date: String) {
		return repository.getRemotePictureOfTheDayByDate(date = date)
	}

}