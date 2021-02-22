package com.udacity.asteroidradar.features.mainscreen.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.udacity.asteroidradar.features.mainscreen.domain.entities.PictureOfDay

@Dao
interface PictureOfTheDayDao {

    @Query("SELECT * FROM picture_of_the_day WHERE id = :id")
    fun getLocalPictureOfTheDayById(id: Long): PictureOfDay

    @Query("SELECT * FROM picture_of_the_day WHERE date = :date")
    fun getLocalPictureOfTheDay(date: String): PictureOfDay

    @Query("SELECT * FROM picture_of_the_day WHERE date >= :startDate AND date <= :endDate ORDER BY date DESC LIMIT 10")
    fun getLocalPictureOfTheDayLastSevenDays(startDate: String, endDate: String): List<PictureOfDay>

    @Query("SELECT * FROM picture_of_the_day WHERE is_favorite = 1 ORDER BY date DESC")
    fun getAllLocalFavoritesPicturesOfTheDay(): List<PictureOfDay>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pictureOfDay: PictureOfDay) : Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(pictureOfDay: PictureOfDay) : Int

    @Query("DELETE FROM picture_of_the_day")
    fun deleteAllPictures() : Int

    @Query("DELETE FROM picture_of_the_day WHERE is_favorite = 1")
    fun deleteFavoritesOnly() : Int

    @Query("DELETE FROM picture_of_the_day WHERE date = :date")
    fun deletePictureOfTheDayByDate(date: String) : Int

}