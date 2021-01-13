package com.udacity.asteroidradar.features.main.data.datasource.local

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.features.main.domain.entities.AsteroidsFeedItem


interface AsteroidsFeedLocalDataSource {
    suspend fun getLocalAsteroidsFeed(): LiveData<List<AsteroidsFeedItem>>
	suspend fun saveFeedToLocalDatabase(asteroidsFeed: List<AsteroidsFeedItem>)
}