package com.udacity.asteroidradar.features.main.data.datasource.local

import android.app.Application
import com.udacity.asteroidradar.database.AppDatabase
import com.udacity.asteroidradar.features.main.domain.entities.AsteroidsFeedItem


class AsteroidsLocalDataSourceImpl(
    application: Application
): AsteroidsLocalDataSource {

    private val asteroidDao = AppDatabase.getDatabase(application).asteroidsDao()

    override suspend fun getAsteroids(): List<AsteroidsFeedItem> {
        return asteroidDao.getAsteroids()
    }

}