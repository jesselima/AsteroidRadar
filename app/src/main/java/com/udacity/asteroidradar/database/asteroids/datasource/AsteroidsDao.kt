package com.udacity.asteroidradar.database.asteroids.datasource

import androidx.room.Query
import androidx.room.Dao
import com.udacity.asteroidradar.database.asteroids.models.AsteroidsFeedItem

@Dao
interface AsteroidsDao {

    @Query("SELECT * FROM asteroids")
    suspend fun getAsteroids(): List<AsteroidsFeedItem>

}