package com.udacity.asteroidradar.features.detail.data.datasource.remote

import retrofit2.http.GET
import retrofit2.http.Query
import com.udacity.asteroidradar.database.asteroids.models.AsteroidsFeedResponse

/**
 * Created by jesselima on 4/01/21.
 * This is a part of the project AsteroidsFeedItem Radar.
 */
interface AsteroidDetailsService {

    @GET("feed")
    suspend fun getAsteroidDetails(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String
    ) : AsteroidsFeedResponse
}