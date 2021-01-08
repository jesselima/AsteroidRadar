package com.udacity.asteroidradar.database.asteroids.datasource

import com.udacity.asteroidradar.database.asteroids.models.AsteroidsFeedItem


interface AsteroidsLocalDataSource {
    suspend fun getAsteroids(): List<AsteroidsFeedItem>
}