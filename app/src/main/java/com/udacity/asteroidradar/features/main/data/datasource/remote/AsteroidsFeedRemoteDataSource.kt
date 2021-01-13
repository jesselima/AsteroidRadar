package com.udacity.asteroidradar.features.main.data.datasource.remote

import com.udacity.asteroidradar.database.asteroids.models.AsteroidsFeedResponse


interface AsteroidsFeedRemoteDataSource {
    suspend fun getRemoteAsteroidsFeed() : AsteroidsFeedResponse?
}