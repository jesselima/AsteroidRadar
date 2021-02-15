package com.udacity.asteroidradar.features.asteroiddetail.data.datasource.remote

import retrofit2.http.GET
import retrofit2.http.Query
import com.udacity.asteroidradar.database.asteroids.models.AsteroidsFeedResponse
import retrofit2.http.Path

/**
 * Created by jesselima on 4/01/21.
 * This is a part of the project AsteroidsFeedItem Radar.
 */
interface AsteroidDetailsService {

    @GET("neo/rest/v1/neo/{id}")
    suspend fun getAsteroidDetails(
        @Path("id") id: String,
        @Query("api_key") apiKey: String
    ) : AsteroidsFeedResponse
}