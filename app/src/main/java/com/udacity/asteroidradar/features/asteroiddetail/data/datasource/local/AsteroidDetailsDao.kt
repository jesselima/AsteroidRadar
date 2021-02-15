package com.udacity.asteroidradar.features.asteroiddetail.data.datasource.local

import androidx.room.Dao
import androidx.room.Query
import com.udacity.asteroidradar.features.mainscreen.domain.entities.AsteroidsFeedItem

@Dao
interface AsteroidDetailsDao {
    @Query("SELECT * FROM asteroids WHERE id = :id")
    fun getLocalAsteroidById(id: Long) : AsteroidsFeedItem
}