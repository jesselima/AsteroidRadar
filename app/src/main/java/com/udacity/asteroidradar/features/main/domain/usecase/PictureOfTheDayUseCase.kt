package com.udacity.asteroidradar.features.main.domain.usecase

import com.udacity.asteroidradar.features.main.domain.entities.PictureOfDay

/**
 * Created by jesselima on 9/01/21.
 * This is a part of the project Asteroid Radar.
 */
interface PictureOfTheDayUseCase {
	suspend fun getRemotePictureOfTheDay()
	suspend fun getLocalPictureOfTheDay() : PictureOfDay
}