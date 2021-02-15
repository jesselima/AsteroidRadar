package com.udacity.asteroidradar.features.asteroiddetail.domain.reposirory

import com.udacity.asteroidradar.features.mainscreen.domain.entities.AsteroidsFeedItem

/**
 * Created by jesselima on 27/01/21.
 * This is a part of the project Asteroid Radar.
 */
interface AsteroidDetailsLocalRepository {
	suspend fun getLocalAsteroidById(id: Long) : AsteroidsFeedItem
}