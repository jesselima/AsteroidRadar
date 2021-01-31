package com.udacity.asteroidradar.core.api

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

class RequestExecutorImpl: RequestExecutor {
    override suspend fun <T> safeRequest(
        dispatcher: CoroutineDispatcher,
        requestExecutor: suspend () -> T
    ): ResultEither<T, ErrorHandler> {
        return withContext(dispatcher) {
            try {
                ResultEither.Success(requestExecutor.invoke())
            } catch (exception: Throwable) {
                when(exception) {
                    is IOException -> {
                        ResultEither.Failure(ErrorHandler.ShowNetworkErrorHandler)
                    }
                    is HttpException -> {
                        ResultEither.Failure(
                            ErrorHandler.ServerError(converterErrorBody(throwable = exception))
                        )
                    }
                    else -> ResultEither.Failure(ErrorHandler.GenericError)
                }
            }
        }
    }

    private fun converterErrorBody(throwable: HttpException): HttpErrorHandler? {
        return try {
            throwable.response()?.errorBody()?.source()?.let {
                Timber.d("ERROR_CONVERTER $it")
                null
            }
        } catch (ex: Exception) {
            null
        }
    }
}