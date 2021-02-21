package com.udacity.asteroidradar.features.mainscreen.data.datasource.local

import com.udacity.asteroidradar.features.mainscreen.domain.entities.AsteroidsFeedItem


interface AsteroidsFeedLocalDataSource {
    suspend fun getLocalAsteroidsFeed(): List<AsteroidsFeedItem>
    suspend fun getTodayAsteroids(): List<AsteroidsFeedItem>
	suspend fun saveFeedToLocalDatabase(asteroidsFeed: List<AsteroidsFeedItem>)
}