package com.udacity.asteroidradar.features.detail.data.datasource.local

import androidx.room.Dao
import androidx.room.Query
import com.udacity.asteroidradar.features.main.domain.entities.AsteroidsFeedItem

@Dao
interface AsteroidDetailsDao {
    @Query("SELECT * FROM asteroids WHERE id = :id")
    fun getLocalAsteroidById(id: Long) : AsteroidsFeedItem
}