package com.udacity.asteroidradar.features.main.data.datasource.local

import android.app.Application
import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.database.AppDatabase
import com.udacity.asteroidradar.features.main.domain.entities.AsteroidsFeedItem


class AsteroidsFeedLocalDataSourceImpl(
    application: Application
): AsteroidsFeedLocalDataSource {

    private val asteroidDao = AppDatabase.getDatabase(application).asteroidsDao()

    override suspend fun getLocalAsteroidsFeed(): LiveData<List<AsteroidsFeedItem>> {
        return asteroidDao.getAllAsteroids()
    }

    override suspend fun saveFeedToLocalDatabase(asteroidsFeed: List<AsteroidsFeedItem>) {
        asteroidsFeed.forEach {
            it.createdAt = System.currentTimeMillis()
            it.modifiedAt = System.currentTimeMillis()
            asteroidDao.insert(it)
        }
    }
}