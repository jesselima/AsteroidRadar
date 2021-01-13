package com.udacity.asteroidradar.features.main.data.datasource.remote

import com.udacity.asteroidradar.core.api.ApiService
import com.udacity.asteroidradar.core.extensions.getCurrentDate
import com.udacity.asteroidradar.database.asteroids.models.AsteroidsFeedResponse
import com.udacity.asteroidradar.features.main.data.datasource.remote.api.AsteroidRadarService


class AsteroidsFeedRemoteDataSourceImpl : AsteroidsFeedRemoteDataSource {

    private val service : AsteroidRadarService by lazy { ApiService.createService(AsteroidRadarService::class.java) }

    override suspend fun getRemoteAsteroidsFeed() : AsteroidsFeedResponse? {
        return service.getRemoteAsteroidFeed(
            startDate = getCurrentDate(),
        )
    }
}