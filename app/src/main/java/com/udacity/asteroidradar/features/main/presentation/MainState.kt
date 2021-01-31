package com.udacity.asteroidradar.features.main.presentation

import com.udacity.asteroidradar.features.main.domain.entities.AsteroidsFeedItem
import com.udacity.asteroidradar.features.main.domain.entities.PictureOfDay

sealed class MainState {
    data class ResultAsteroidsSuccess(
        val asteroidsResult: List<AsteroidsFeedItem>
    ) : MainState()
    data class ResultPicturesOfTheDay(
        val picturesResult: List<PictureOfDay>
    ) : MainState()
    data class ResultPicturesOfTheDayByDate(
        val pictureByDate: PictureOfDay
    ) : MainState()
    data class LoadingAsteroids(
        val isLoadingAsteroids: Boolean = true
    ) : MainState()
    data class LoadingPictures(
        val isLoadingPictures: Boolean = true
    ) : MainState()
    data class ShowError(
        val message: String,
        val action: (() -> Unit)?
    ) : MainState()
}