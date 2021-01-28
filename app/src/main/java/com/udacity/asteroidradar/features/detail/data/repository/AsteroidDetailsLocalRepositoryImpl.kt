package com.udacity.asteroidradar.features.detail.data.repository

import com.udacity.asteroidradar.features.detail.data.datasource.local.AsteroidDetailsLocalDataSource
import com.udacity.asteroidradar.features.detail.domain.reposirory.AsteroidDetailsLocalRepository
import com.udacity.asteroidradar.features.main.domain.entities.AsteroidsFeedItem

/**
 * Created by jesselima on 27/01/21.
 * This is a part of the project Asteroid Radar.
 */
class AsteroidDetailsLocalRepositoryImpl(
	private val asteroidDetailsLocalDataSource: AsteroidDetailsLocalDataSource,
) : AsteroidDetailsLocalRepository {
	override suspend fun getLocalAsteroidById(id: Long): AsteroidsFeedItem {
		return asteroidDetailsLocalDataSource.getLocalAsteroidById(id = id)
	}

}