package com.udacity.asteroidradar.core.api

/**
 * Thanks the Jeziel Lago code sharing this on GitHub:
 * https://github.com/jeziellago/ideas-kotlin-problems/blob/master/ResultEither.kt
 */
sealed class ResultEither<out S, out F> {
    data class Success<out S>(val value: S) : ResultEither<S, Nothing>()
    data class Failure<out F>(val value: F) : ResultEither<Nothing, F>()
}

inline fun <S, F, T> ResultEither<S, F>.flow(
    success: (S) -> T,
    failure: (F) -> T
): T = when (this) {
    is ResultEither.Success -> success(value)
    is ResultEither.Failure -> failure(value)
}

inline fun <S, F, T> ResultEither<S, F>.mapSuccess(transform: (S) -> T): ResultEither<T, F> {
    return when (this) {
        is ResultEither.Success -> ResultEither.Success(
            transform(value)
        )
        is ResultEither.Failure -> this
    }
}

inline fun <S> ResultEither.Success<S>.filter(condition: (S) -> Boolean) = if (condition(value)) {
    ResultEither.Success(value)
} else {
    null
}

inline fun <E, S : Iterable<E>> ResultEither.Success<S>.filterItems(condition: (E) -> Boolean) =
    ResultEither.Success(value.filter {
        condition(
            it
        )
    })

inline fun <S, T> ResultEither.Success<S>.map(transform: (S) -> T) =
    ResultEither.Success(transform(value))

inline fun <E, S : Iterable<E>, T> ResultEither.Success<S>.mapItems(transform: (E) -> T) =
    ResultEither.Success(value.map {
        transform(
            it
        )
    })