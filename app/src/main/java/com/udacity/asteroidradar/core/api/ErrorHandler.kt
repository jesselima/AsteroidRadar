package com.udacity.asteroidradar.core.api

sealed class ErrorHandler() {
    object GenericError : ErrorHandler()
    data class ServerError(val error: HttpErrorHandler?) : ErrorHandler()
    data class ThrowableHandler(val error: Throwable?) : ErrorHandler()
    object ShowNetworkErrorHandler : ErrorHandler()
}