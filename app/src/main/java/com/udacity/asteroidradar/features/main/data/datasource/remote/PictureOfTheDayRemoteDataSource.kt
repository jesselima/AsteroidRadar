package com.udacity.asteroidradar.features.main.data.datasource.remote

import com.udacity.asteroidradar.features.main.data.models.PictureOfDayResponse


interface PictureOfTheDayRemoteDataSource {
    suspend fun getRemotePictureOfTheDay() : PictureOfDayResponse?
}