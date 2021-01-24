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

	override suspend fun getRemotePictureOfTheLastSevenDays() {
		repository.getRemotePictureOfTheLastSevenDays()
	}

	override suspend fun getLocalPictureOfTheDayLastSevenDays(): List<PictureOfDay> {
		return repository.getLocalPictureOfTheLastSevenDays()
	}

	override suspend fun getLocalPictureOfTheDayByDate(date: String) : PictureOfDay {
		return repository.getLocalPictureOfTheDayByDate(date = date)
	}

	override suspend fun getRemotePictureOfTheDayByDate(date: String) {
		return repository.getRemotePictureOfTheDayByDate(date = date)
	}

}