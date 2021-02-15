package com.udacity.asteroidradar.features.mainscreen.presentation

import com.udacity.asteroidradar.features.mainscreen.domain.entities.AsteroidsFeedItem

data class AsteroidsState(
    val isLoadingAsteroids: Boolean = false,
    val asteroidsResult: List<AsteroidsFeedItem> = emptyList(),
    val action: (() -> Unit)? = null
)
