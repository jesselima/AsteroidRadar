package com.udacity.asteroidradar.features.mainscreen.domain.reposirory

import com.udacity.asteroidradar.features.mainscreen.domain.entities.AsteroidsFeedItem

/**
 * Created by jesselima on 9/01/21.
 * This is a part of the project Asteroid Radar.
 */
interface AsteroidsFeedRepository {
	suspend fun getRemoteFeed()
	suspend fun getLocalFeed() : List<AsteroidsFeedItem>
}