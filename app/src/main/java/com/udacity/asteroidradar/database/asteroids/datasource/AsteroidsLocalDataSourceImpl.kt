package com.udacity.asteroidradar.database.asteroids.datasource

import android.app.Application
import com.udacity.asteroidradar.database.AppDatabase
import com.udacity.asteroidradar.database.asteroids.models.AsteroidsFeedItem


class AsteroidsLocalDataSourceImpl(
    application: Application
): AsteroidsLocalDataSource {

    private val asteroidDao = AppDatabase.getDatabase(application).asteroidsDao()

    override suspend fun getAsteroids(): List<AsteroidsFeedItem> {
        return asteroidDao.getAsteroids()
    }

}