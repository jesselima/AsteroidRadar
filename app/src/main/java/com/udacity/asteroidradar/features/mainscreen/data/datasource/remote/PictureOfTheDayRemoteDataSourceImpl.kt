package com.udacity.asteroidradar.features.mainscreen.data.datasource.remote

import com.udacity.asteroidradar.core.api.ApiService
import com.udacity.asteroidradar.core.api.ErrorHandler
import com.udacity.asteroidradar.core.api.RequestExecutor
import com.udacity.asteroidradar.core.api.ResultEither
import com.udacity.asteroidradar.core.api.flow
import com.udacity.asteroidradar.features.mainscreen.data.datasource.remote.api.AsteroidRadarService
import com.udacity.asteroidradar.features.mainscreen.data.models.PictureOfDayResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


class PictureOfTheDayRemoteDataSourceImpl(
    private val requestExecutor: RequestExecutor,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : PictureOfTheDayRemoteDataSource {

    private val service : AsteroidRadarService by lazy { ApiService.createService(AsteroidRadarService::class.java) }

    override suspend fun getRemotePictureOfTheDay() : ResultEither<PictureOfDayResponse?, ErrorHandler> {
        return requestExecutor.safeRequest(dispatcher) {
            service.getRemotePictureOfTheDay()
        }.flow(
            {
                ResultEither.Success(it)
            },
            {
                ResultEither.Failure(it)
            }
        )
    }

    override suspend fun getRemotePictureOfTheDayByDate(
        date: String
    ) : ResultEither<PictureOfDayResponse?, ErrorHandler> {
        return requestExecutor.safeRequest(dispatcher) {
            service.getRemotePictureOfTheDayByDate(
                date = date
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

    override suspend fun getRemotePictureOfTheLastSevenDays(
        startDate: String,
        endDate: String
    ) : ResultEither<List<PictureOfDayResponse>?, ErrorHandler> {
        return requestExecutor.safeRequest(dispatcher) {
            service.getRemotePictureOfTheLastSevenDays(
                startDate = startDate,
                endDate = endDate
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