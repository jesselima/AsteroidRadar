package com.udacity.asteroidradar.features.main.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.udacity.asteroidradar.features.main.domain.entities.PictureOfDay

@Dao
interface PictureOfTheDayDao {

    @Query("SELECT * FROM picture_of_the_day WHERE date = :date")
    fun getLocalPictureOfTheDay(date: String): PictureOfDay

    @Query("SELECT * FROM picture_of_the_day WHERE date >= :startDate AND date <= :endDate ORDER BY date DESC")
    fun getLocalPictureOfTheDayLastSevenDays(startDate: String, endDate: String): List<PictureOfDay>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pictureOfDay: PictureOfDay) : Long

    @Update
    fun update(pictureOfDay: PictureOfDay) : Int

    @Query("DELETE FROM picture_of_the_day")
    fun deleteAllPicturesOfTheDay() : Int

    @Query("DELETE FROM asteroids")
    fun deleteAllAsteroids() : Int

}