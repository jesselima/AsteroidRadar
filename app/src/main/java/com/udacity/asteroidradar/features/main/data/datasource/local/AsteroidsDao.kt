package com.udacity.asteroidradar.features.main.data.datasource.local

import androidx.room.Query
import androidx.room.Dao
import com.udacity.asteroidradar.features.main.domain.entities.AsteroidsFeedItem

@Dao
interface AsteroidsDao {

    @Query("SELECT * FROM asteroids")
    suspend fun getAsteroids(): List<AsteroidsFeedItem>

}