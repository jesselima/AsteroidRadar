package com.udacity.asteroidradar.features.mainscreen.domain.usecase

import com.udacity.asteroidradar.features.mainscreen.domain.entities.AsteroidsFeedItem

/**
 * Created by jesselima on 9/01/21.
 * This is a part of the project Asteroid Radar.
 */
interface AsteroidsFeedUseCase {
	suspend fun getRemoteFeed()
	suspend fun getLocalFeed() : List<AsteroidsFeedItem>
}