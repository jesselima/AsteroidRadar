package com.udacity.asteroidradar.features.main.data.datasource.remote

import com.udacity.asteroidradar.core.api.ApiService
import com.udacity.asteroidradar.features.main.data.datasource.remote.api.AsteroidRadarService
import com.udacity.asteroidradar.features.main.data.models.PictureOfDayResponse


class PictureOfTheDayRemoteDataSourceImpl : PictureOfTheDayRemoteDataSource {

    private val service : AsteroidRadarService by lazy { ApiService.createService(AsteroidRadarService::class.java) }

    override suspend fun getRemotePictureOfTheDay() : PictureOfDayResponse? {
        return  service.getRemotePictureOfTheDay()
    }

    override suspend fun getRemotePictureOfTheDayByDate(date: String): PictureOfDayResponse? {
        return service.getRemotePictureOfTheDayByDate(date = date)
    }

    override suspend fun getRemotePictureOfTheLastSevenDays(startDate: String, endDate: String): List<PictureOfDayResponse>? {
        return service.getRemotePictureOfTheLastSevenDays(startDate = startDate, endDate = endDate)
    }
}