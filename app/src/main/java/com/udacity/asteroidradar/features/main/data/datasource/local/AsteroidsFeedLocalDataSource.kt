package com.udacity.asteroidradar.features.main.data.datasource.local

import com.udacity.asteroidradar.features.main.domain.entities.AsteroidsFeedItem


interface AsteroidsFeedLocalDataSource {
    suspend fun getLocalAsteroidsFeed(): List<AsteroidsFeedItem>
	suspend fun saveFeedToLocalDatabase(asteroidsFeed: List<AsteroidsFeedItem>)
}