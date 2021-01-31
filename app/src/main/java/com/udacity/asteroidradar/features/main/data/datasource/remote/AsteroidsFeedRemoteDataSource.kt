package com.udacity.asteroidradar.features.main.data.datasource.remote

import com.udacity.asteroidradar.core.api.ErrorHandler
import com.udacity.asteroidradar.core.api.ResultEither
import com.udacity.asteroidradar.database.asteroids.models.AsteroidsFeedResponse


interface AsteroidsFeedRemoteDataSource {
    suspend fun getRemoteAsteroidsFeed() : ResultEither<AsteroidsFeedResponse?, ErrorHandler>
}