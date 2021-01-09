package com.udacity.asteroidradar.features.main.data.datasource.local

import com.udacity.asteroidradar.features.main.domain.entities.AsteroidsFeedItem


interface AsteroidsLocalDataSource {
    suspend fun getAsteroids(): List<AsteroidsFeedItem>
}