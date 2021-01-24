package com.udacity.asteroidradar.features.main.domain.usecase

import com.udacity.asteroidradar.features.main.domain.entities.PictureOfDay

/**
 * Created by jesselima on 9/01/21.
 * This is a part of the project Asteroid Radar.
 */
interface PictureOfTheDayUseCase {
	suspend fun getRemotePictureOfTheLastSevenDays()
	suspend fun getRemotePictureOfTheDayByDate(date: String)
	suspend fun getLocalPictureOfTheDayByDate(date: String) : PictureOfDay
	suspend fun getLocalPictureOfTheDayLastSevenDays() : List<PictureOfDay>
}