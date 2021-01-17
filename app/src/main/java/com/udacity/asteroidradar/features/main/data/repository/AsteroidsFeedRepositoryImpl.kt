package com.udacity.asteroidradar.features.main.data.repository

import com.udacity.asteroidradar.database.asteroids.models.mapToLocalDatabaseModel
import com.udacity.asteroidradar.features.main.data.datasource.local.AsteroidsFeedLocalDataSource
import com.udacity.asteroidradar.features.main.data.datasource.remote.AsteroidsFeedRemoteDataSource
import com.udacity.asteroidradar.features.main.domain.entities.AsteroidsFeedItem
import com.udacity.asteroidradar.features.main.domain.reposirory.AsteroidsFeedRepository

/**
 * Created by jesselima on 9/01/21.
 * This is a part of the project Asteroid Radar.
 */
class AsteroidsFeedRepositoryImpl(
		private val asteroidsFeedLocalDataSource: AsteroidsFeedLocalDataSource,
		private val asteroidsFeedRemoteDataSource: AsteroidsFeedRemoteDataSource
) : AsteroidsFeedRepository {

	override suspend fun getRemoteFeed() {
		val asteroidsFeedResponse = asteroidsFeedRemoteDataSource.getRemoteAsteroidsFeed()
		asteroidsFeedResponse?.let {
			asteroidsFeedLocalDataSource.saveFeedToLocalDatabase(asteroidsFeed = it.mapToLocalDatabaseModel())
		}
	}

	override suspend fun getLocalFeed(): List<AsteroidsFeedItem> {
		return asteroidsFeedLocalDataSource.getLocalAsteroidsFeed()
	}
}