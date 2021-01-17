package com.udacity.asteroidradar.features.main.data.datasource.local

import android.app.Application
import com.udacity.asteroidradar.core.extensions.getCurrentDate
import com.udacity.asteroidradar.database.AppDatabase
import com.udacity.asteroidradar.features.main.domain.entities.AsteroidsFeedItem


class AsteroidsFeedLocalDataSourceImpl(
    application: Application
): AsteroidsFeedLocalDataSource {

    private val asteroidDao = AppDatabase.getDatabase(application).asteroidsDao()

    override suspend fun getLocalAsteroidsFeed(): List<AsteroidsFeedItem> {
        return asteroidDao.getAllAsteroids()
    }

    override suspend fun saveFeedToLocalDatabase(asteroidsFeed: List<AsteroidsFeedItem>) {
        asteroidsFeed.forEach {
            it.createdAt = getCurrentDate()
            it.modifiedAt = getCurrentDate()
            asteroidDao.insert(it)
        }
    }
}