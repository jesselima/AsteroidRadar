package com.udacity.asteroidradar.features.detail.data.datasource.local

import android.app.Application
import com.udacity.asteroidradar.core.database.AppDatabase
import com.udacity.asteroidradar.features.main.domain.entities.AsteroidsFeedItem


class AsteroidDetailsLocalDataSourceImpl(
    application: Application
): AsteroidDetailsLocalDataSource {

    private val asteroidDetailsDao = AppDatabase.getDatabase(application).asteroidDetailsDao()

    override suspend fun getLocalAsteroidById(id: Long): AsteroidsFeedItem {
        return asteroidDetailsDao.getLocalAsteroidById(id = id)
    }
}