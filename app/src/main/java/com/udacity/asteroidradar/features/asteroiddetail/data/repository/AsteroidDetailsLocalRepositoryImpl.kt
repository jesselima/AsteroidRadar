package com.udacity.asteroidradar.features.asteroiddetail.data.repository

import com.udacity.asteroidradar.features.asteroiddetail.data.datasource.local.AsteroidDetailsLocalDataSource
import com.udacity.asteroidradar.features.asteroiddetail.domain.reposirory.AsteroidDetailsLocalRepository
import com.udacity.asteroidradar.features.mainscreen.domain.entities.AsteroidsFeedItem

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