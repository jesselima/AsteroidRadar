package com.udacity.asteroidradar.features.asteroiddetail.domain.usecase

import com.udacity.asteroidradar.features.asteroiddetail.domain.reposirory.AsteroidDetailsLocalRepository
import com.udacity.asteroidradar.features.mainscreen.domain.entities.AsteroidsFeedItem

/**
 * Created by jesselima on 27/01/21.
 * This is a part of the project Asteroid Radar.
 */
class AsteroidDetailsLocalUseCaseImpl(
	private val asteroidDetailsLocalRepository: AsteroidDetailsLocalRepository
) : AsteroidDetailsLocalUseCase {
	override suspend fun getLocalAsteroidById(id: Long): AsteroidsFeedItem {
		return asteroidDetailsLocalRepository.getLocalAsteroidById(id = id)
	}
}