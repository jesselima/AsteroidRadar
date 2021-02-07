package com.udacity.asteroidradar.features.main.presentation

import com.udacity.asteroidradar.features.main.domain.entities.AsteroidsFeedItem
import com.udacity.asteroidradar.features.main.domain.entities.PictureOfDay

data class AsteroidsState(
    val isLoadingAsteroids: Boolean = false,
    val asteroidsResult: List<AsteroidsFeedItem> = emptyList(),
    val action: (() -> Unit)? = null
)
