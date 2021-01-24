package com.udacity.asteroidradar.core.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by jesselima on 4/01/21.
 * This is a part of the project AsteroidsFeedItem Radar.
 */

private const val BASE_URL = "https://api.nasa.gov/"

private val moshi = Moshi.Builder()
	.add(KotlinJsonAdapterFactory())
	.build()

private val retrofit = setupRetrofit()

fun setupRetrofit() : Retrofit {
	val httpLoggingInterceptor = HttpLoggingInterceptor()
	httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

	val okHttpClient = OkHttpClient()
		.newBuilder()
		.addInterceptor(httpLoggingInterceptor)
		.connectTimeout(30, TimeUnit.SECONDS)
		.callTimeout(30, TimeUnit.SECONDS)
		.build()

	return Retrofit.Builder()
		.baseUrl(BASE_URL)
		.client(okHttpClient)
		.addConverterFactory(MoshiConverterFactory.create(moshi))
		.addCallAdapterFactory(CoroutineCallAdapterFactory())
		.build()
}

class ApiService {
	companion object {
		fun <T> createService(service: Class<T>) : T {
			return retrofit.create(service)
		}
	}
}