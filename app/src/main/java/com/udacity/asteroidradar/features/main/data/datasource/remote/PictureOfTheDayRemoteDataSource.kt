package com.udacity.asteroidradar.features.main.data.datasource.remote

import com.udacity.asteroidradar.core.api.ErrorHandler
import com.udacity.asteroidradar.core.api.ResultEither
import com.udacity.asteroidradar.features.main.data.models.PictureOfDayResponse


interface PictureOfTheDayRemoteDataSource {
    suspend fun getRemotePictureOfTheDay() : ResultEither<PictureOfDayResponse?, ErrorHandler>
    suspend fun getRemotePictureOfTheDayByDate(
        date: String
    ) : ResultEither<PictureOfDayResponse?, ErrorHandler>
    suspend fun getRemotePictureOfTheLastSevenDays(
        startDate: String,
        endDate: String
    ) : ResultEither<List<PictureOfDayResponse>?, ErrorHandler>
}