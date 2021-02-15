package com.udacity.asteroidradar.features.asteroiddetail.data.datasource.local

import com.udacity.asteroidradar.features.mainscreen.domain.entities.AsteroidsFeedItem


interface AsteroidDetailsLocalDataSource {
    suspend fun getLocalAsteroidById(id: Long): AsteroidsFeedItem
}