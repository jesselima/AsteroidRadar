package com.udacity.asteroidradar.features.detail.data.datasource.local

import com.udacity.asteroidradar.features.main.domain.entities.AsteroidsFeedItem


interface AsteroidDetailsLocalDataSource {
    suspend fun getLocalAsteroidById(id: Long): AsteroidsFeedItem
}