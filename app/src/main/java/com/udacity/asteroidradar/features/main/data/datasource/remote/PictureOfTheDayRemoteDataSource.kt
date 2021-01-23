package com.udacity.asteroidradar.features.main.data.datasource.remote

import com.udacity.asteroidradar.features.main.data.models.PictureOfDayResponse


interface PictureOfTheDayRemoteDataSource {
    suspend fun getRemotePictureOfTheDay() : PictureOfDayResponse?
    suspend fun getRemotePictureOfTheDayByDate(date: String) : PictureOfDayResponse?
    suspend fun getRemotePictureOfTheLastSevenDays(startDate: String, endDate: String) : List<PictureOfDayResponse>?
}