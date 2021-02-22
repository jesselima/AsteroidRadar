package com.udacity.asteroidradar.features.mainscreen.domain.reposirory

import com.udacity.asteroidradar.features.mainscreen.domain.entities.PictureOfDay

/**
 * Created by jesselima on 9/01/21.
 * This is a part of the project Asteroid Radar.
 */
interface PictureOfTheDayRepository {
	suspend fun getRemotePictureOfTheDay()
	suspend fun getRemotePictureOfTheDayByDate(date: String)
	suspend fun getLocalPictureOfTheDayByDate(date: String) : PictureOfDay
	suspend fun getLocalPictureOfTheDayById(id: Long) : PictureOfDay
	suspend fun getRemotePictureOfTheLastSevenDays()
	suspend fun getLocalPictureOfTheLastSevenDays() : List<PictureOfDay>
	suspend fun getAllLocalFavoritesPicturesOfTheDay() : List<PictureOfDay>
	suspend fun updateFavoritePictureState(pictureOfDay: PictureOfDay) : Int
	suspend fun deleteAllPictures() : Int
	suspend fun deleteFavoritesOnly() : Int
}