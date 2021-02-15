package com.udacity.asteroidradar.features.mainscreen.data.datasource.remote.api

import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.database.asteroids.models.AsteroidsFeedResponse
import com.udacity.asteroidradar.features.mainscreen.data.models.PictureOfDayResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by jesselima on 4/01/21.
 * This is a part of the project AsteroidsFeedItem Radar.
 */
interface AsteroidRadarService {

    @GET("neo/rest/v1/feed")
    suspend fun getRemoteAsteroidFeed(
        @Query("start_date") startDate: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ) : AsteroidsFeedResponse?

    @GET("planetary/apod")
    suspend fun getRemotePictureOfTheDay(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ) : PictureOfDayResponse?

    @GET("planetary/apod")
    suspend fun getRemotePictureOfTheDayByDate(
        @Query("date") date: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ) : PictureOfDayResponse?

    @GET("planetary/apod")
    suspend fun getRemotePictureOfTheLastSevenDays(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ) : List<PictureOfDayResponse>?

}