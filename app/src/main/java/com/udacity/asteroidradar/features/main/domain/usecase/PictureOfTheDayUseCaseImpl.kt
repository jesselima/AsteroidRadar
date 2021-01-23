package com.udacity.asteroidradar.features.main.domain.usecase

import com.udacity.asteroidradar.features.main.domain.entities.PictureOfDay
import com.udacity.asteroidradar.features.main.domain.reposirory.PictureOfTheDayRepository

/**
 * Created by jesselima on 9/01/21.
 * This is a part of the project Asteroid Radar.
 */
class PictureOfTheDayUseCaseImpl(
	private val repository: PictureOfTheDayRepository
) : PictureOfTheDayUseCase {

	override suspend fun getRemotePictureOfTheDay() {
		repository.getRemotePictureOfTheDay()
	}

	override suspend fun getRemotePictureOfTheDayByDate(date: String): PictureOfDay {
		return repository.getRemotePictureOfTheDayByDate(date = date)
	}

	override suspend fun getLocalPictureOfTheDay() : PictureOfDay {
		return repository.getLocalPictureOfTheDay()
	}
}