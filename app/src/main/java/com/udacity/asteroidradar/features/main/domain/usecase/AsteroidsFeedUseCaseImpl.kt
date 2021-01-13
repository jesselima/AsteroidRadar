package com.udacity.asteroidradar.features.main.domain.usecase

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.features.main.domain.entities.AsteroidsFeedItem
import com.udacity.asteroidradar.features.main.domain.reposirory.AsteroidsFeedRepository

/**
 * Created by jesselima on 9/01/21.
 * This is a part of the project Asteroid Radar.
 */
class AsteroidsFeedUseCaseImpl(
	private val repository: AsteroidsFeedRepository
) : AsteroidsFeedUseCase {

	override suspend fun getLocalFeed() : LiveData<List<AsteroidsFeedItem>> {
		return repository.getLocalFeed()
	}
}