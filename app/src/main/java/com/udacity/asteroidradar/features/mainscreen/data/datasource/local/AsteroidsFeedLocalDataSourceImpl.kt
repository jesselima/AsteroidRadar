package com.udacity.asteroidradar.features.mainscreen.data.datasource.local

import android.app.Application
import com.udacity.asteroidradar.core.database.AppDatabase
import com.udacity.asteroidradar.core.extensions.getCurrentDate
import com.udacity.asteroidradar.features.mainscreen.domain.entities.AsteroidsFeedItem


class AsteroidsFeedLocalDataSourceImpl(
    application: Application
): AsteroidsFeedLocalDataSource {

    private val asteroidDao = AppDatabase.getDatabase(application).asteroidsDao()

    override suspend fun getLocalAsteroidsFeed(): List<AsteroidsFeedItem> {
        return asteroidDao.getAllAsteroids()
    }

    override suspend fun getTodayAsteroids(): List<AsteroidsFeedItem> {
        return asteroidDao.getTodayAsteroids(date = getCurrentDate())
    }

    override suspend fun saveFeedToLocalDatabase(asteroidsFeed: List<AsteroidsFeedItem>) {
        asteroidDao.deleteOldAsteroids(currentDate = getCurrentDate())
        asteroidsFeed.forEach {
            it.createdAt = getCurrentDate()
            it.modifiedAt = getCurrentDate()
            asteroidDao.insert(it)
        }
    }
}