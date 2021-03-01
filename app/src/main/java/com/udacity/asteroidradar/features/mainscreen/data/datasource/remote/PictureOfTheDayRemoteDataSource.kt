package com.udacity.asteroidradar.features.mainscreen.data.datasource.remote

import com.udacity.asteroidradar.core.api.ErrorHandler
import com.udacity.asteroidradar.core.api.ResultEither
import com.udacity.asteroidradar.features.mainscreen.data.models.PictureOfDayResponse


interface PictureOfTheDayRemoteDataSource {
    suspend fun getRemotePictureOfTheDay() : ResultEither<PictureOfDayResponse?, ErrorHandler>
    suspend fun getRemotePictureOfTheDayByDate(
        date: String
    ) : ResultEither<PictureOfDayResponse?, ErrorHandler>
    suspend fun getRemotePictureOfTheDayLatestDays(
        startDate: String,
    ) : ResultEither<List<PictureOfDayResponse>?, ErrorHandler>
}