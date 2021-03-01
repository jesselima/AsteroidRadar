package com.udacity.asteroidradar.features.mainscreen.data.repository

import com.udacity.asteroidradar.core.api.ResultEither
import com.udacity.asteroidradar.core.api.flow
import com.udacity.asteroidradar.core.extensions.getCurrentDateApiQueryParamFormat
import com.udacity.asteroidradar.features.mainscreen.data.datasource.local.PictureOfTheDayLocalDataSource
import com.udacity.asteroidradar.features.mainscreen.data.datasource.remote.PictureOfTheDayRemoteDataSource
import com.udacity.asteroidradar.features.mainscreen.data.models.mapToLocalDatabaseModel
import com.udacity.asteroidradar.features.mainscreen.domain.entities.PictureOfDay
import com.udacity.asteroidradar.features.mainscreen.domain.reposirory.PictureOfTheDayRepository
import timber.log.Timber

/**
 * Created by jesselima on 9/01/21.
 * This is a part of the project Asteroid Radar.
 */
class PictureOfTheDayRepositoryImpl(
		private val pictureOfTheDayLocalDataSource: PictureOfTheDayLocalDataSource,
		private val pictureOfTheDayRemoteDataSource: PictureOfTheDayRemoteDataSource
) : PictureOfTheDayRepository {

	override suspend fun getRemotePictureOfTheDay() {
		pictureOfTheDayRemoteDataSource.getRemotePictureOfTheDay()
			.flow(
				{ pictureOfTheDayResponse ->
					pictureOfTheDayResponse?.let {
						pictureOfTheDayLocalDataSource.savePictureOfTheDayToLocalDatabase(
							it.mapToLocalDatabaseModel()
						)
					}
				},
				{
					ResultEither.Failure(it)
				}
			)
	}

	override suspend fun getRemotePictureOfTheDayByDate(date: String) {
		pictureOfTheDayRemoteDataSource.getRemotePictureOfTheDayByDate(date = date)
			.flow(
				{	pictureOfTheDayResponse ->
						pictureOfTheDayResponse?.let {
							pictureOfTheDayLocalDataSource.savePictureOfTheDayToLocalDatabase(
								it.mapToLocalDatabaseModel()
							)
						}
				},
				{
					ResultEither.Failure(it)
				}
			)
	}

	override suspend fun getRemotePictureOfTheDayLatestDays() {
		pictureOfTheDayRemoteDataSource.getRemotePictureOfTheDayLatestDays(
			startDate = getCurrentDateApiQueryParamFormat()
		).flow(
			{ picturesOfTheDayResponse ->
				picturesOfTheDayResponse?.forEach {
					Timber.d(it.imageUrl)
					pictureOfTheDayLocalDataSource.savePictureOfTheDayToLocalDatabase(
						it.mapToLocalDatabaseModel()
					)
				}
			},
			{
				ResultEither.Failure(it)
			}
		)
	}

	override suspend fun getLocalPictureOfTheDayByDate(date: String): PictureOfDay? {
		return pictureOfTheDayLocalDataSource.getLocalPictureOfTheDay(date = date)
	}

	override suspend fun getLocalPictureOfTheDayById(id: Long): PictureOfDay? {
		return pictureOfTheDayLocalDataSource.getLocalPictureOfTheDayById(id = id)
	}

	override suspend fun getLocalPictureOfTheDayLatestDays(): List<PictureOfDay> {
		return  pictureOfTheDayLocalDataSource.getLocalPictureOfTheDayLatestDays()
	}

	override suspend fun getAllLocalFavoritesPicturesOfTheDay(): List<PictureOfDay> {
		return pictureOfTheDayLocalDataSource.getAllLocalFavoritesPicturesOfTheDay()
	}

	override suspend fun updateFavoritePictureState(pictureOfDay: PictureOfDay): Int {
		return pictureOfTheDayLocalDataSource.updateFavoritePictureState(pictureOfDay)
	}

	override suspend fun deleteAllPictures(): Int {
		return pictureOfTheDayLocalDataSource.deleteAllPictures()
	}

	override suspend fun deleteFavoritesOnly(): Int {
		return pictureOfTheDayLocalDataSource.deleteFavoritesOnly()
	}

	override suspend fun deleteNotFavoritesOnly(): Int {
		return pictureOfTheDayLocalDataSource.deleteNotFavoritesOnly()
	}

	override suspend fun resetFavorites(): Int {
		return pictureOfTheDayLocalDataSource.resetFavorites()
	}

	override suspend fun deletePictureOfTheDayByDate(date: String): Int {
		return pictureOfTheDayLocalDataSource.deletePictureOfTheDayByDate(date = date)
	}
}