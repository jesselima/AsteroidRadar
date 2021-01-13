package com.udacity.asteroidradar.core.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.features.main.data.datasource.remote.api.AsteroidRadarService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by jesselima on 4/01/21.
 * This is a part of the project AsteroidsFeedItem Radar.
 */

private const val BASE_URL = "https://api.nasa.gov/neo/rest/v1/"

private val moshi = Moshi.Builder()
	.add(KotlinJsonAdapterFactory())
	.build()

private val retrofit = Retrofit.Builder()
	.baseUrl(BASE_URL)
	.addConverterFactory(MoshiConverterFactory.create(moshi))
	.addCallAdapterFactory(CoroutineCallAdapterFactory())
	.build()

class ApiService {
	companion object {
		fun <T> createService(service: Class<T>) : T {
			return retrofit.create(service)
		}
	}
}