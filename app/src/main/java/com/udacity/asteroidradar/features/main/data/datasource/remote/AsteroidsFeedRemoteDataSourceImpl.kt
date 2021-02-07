package com.udacity.asteroidradar.features.main.data.datasource.remote

import com.udacity.asteroidradar.core.api.ApiService
import com.udacity.asteroidradar.core.api.ErrorHandler
import com.udacity.asteroidradar.core.api.RequestExecutor
import com.udacity.asteroidradar.core.api.ResultEither
import com.udacity.asteroidradar.core.api.flow
import com.udacity.asteroidradar.core.extensions.getCurrentDate
import com.udacity.asteroidradar.database.asteroids.models.AsteroidsFeedResponse
import com.udacity.asteroidradar.features.main.data.datasource.remote.api.AsteroidRadarService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


class AsteroidsFeedRemoteDataSourceImpl(
    private val requestExecutor: RequestExecutor,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : AsteroidsFeedRemoteDataSource {

    private val service : AsteroidRadarService by lazy {
        ApiService.createService(AsteroidRadarService::class.java)
    }

    override suspend fun getRemoteAsteroidsFeed(): ResultEither<AsteroidsFeedResponse?, ErrorHandler> {
        return requestExecutor.safeRequest(dispatcher) {
                service.getRemoteAsteroidFeed(
                    startDate = getCurrentDate(),
                )
            }.flow(
                {
                    ResultEither.Success(it)
                },
                {
                    ResultEither.Failure(it)
                }
            )
    }
}