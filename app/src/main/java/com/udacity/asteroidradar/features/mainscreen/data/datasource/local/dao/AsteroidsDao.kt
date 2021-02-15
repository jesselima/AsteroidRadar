package com.udacity.asteroidradar.features.mainscreen.data.datasource.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.udacity.asteroidradar.features.mainscreen.domain.entities.AsteroidsFeedItem

@Dao
interface AsteroidsDao {

    @Query("SELECT * FROM asteroids")
    fun getAllAsteroids(): List<AsteroidsFeedItem>

    @Query("SELECT * FROM asteroids WHERE date = :date")
    fun getTodayAsteroids(date: String) : LiveData<List<AsteroidsFeedItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(asteroidsFeedItem: AsteroidsFeedItem) : Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(asteroidsFeedItem: AsteroidsFeedItem) : Int

    @Query("DELETE FROM asteroids")
    fun deleteAll() : Int

    @Query("SELECT * FROM asteroids WHERE date < :currentDate")
    fun deleteOldAsteroids(currentDate: String) : LiveData<List<AsteroidsFeedItem>>

}