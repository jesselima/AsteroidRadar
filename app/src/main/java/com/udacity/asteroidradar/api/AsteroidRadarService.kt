package com.udacity.asteroidradar.api

import retrofit2.http.GET
import retrofit2.http.Query
import com.udacity.asteroidradar.database.asteroids.models.AsteroidsFeedResponse

/**
 * Created by jesselima on 4/01/21.
 * This is a part of the project AsteroidsFeedItem Radar.
 */
interface AsteroidRadarService {

    @GET("feed")
    suspend fun getAsteroidFeed(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String
    ) : AsteroidsFeedResponse
}