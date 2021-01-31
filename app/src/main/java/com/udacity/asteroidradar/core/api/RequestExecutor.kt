package com.udacity.asteroidradar.core.api

import kotlinx.coroutines.CoroutineDispatcher

interface RequestExecutor {
    suspend fun <T> safeRequest(
        dispatcher: CoroutineDispatcher,
        requestExecutor: suspend () -> T
    ): ResultEither<T, ErrorHandler>
}