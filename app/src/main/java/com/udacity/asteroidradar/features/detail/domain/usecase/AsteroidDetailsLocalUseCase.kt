package com.udacity.asteroidradar.features.detail.domain.usecase

import com.udacity.asteroidradar.features.main.domain.entities.AsteroidsFeedItem

/**
 * Created by jesselima on 27/01/21.
 * This is a part of the project Asteroid Radar.
 */
interface AsteroidDetailsLocalUseCase {
	suspend fun getLocalAsteroidById(id: Long) : AsteroidsFeedItem
}