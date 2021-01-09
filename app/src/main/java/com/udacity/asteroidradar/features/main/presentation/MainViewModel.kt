package com.udacity.asteroidradar.features.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.core.api.AsteroidRadarApi
import com.udacity.asteroidradar.database.asteroids.models.mapDataToDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class MainViewModel : ViewModel() {

	init {
		getFeed()
	}

	private fun getFeed() {
		viewModelScope.launch {
			withContext(Dispatchers.IO) {
				val data = AsteroidRadarApi.service.getAsteroidFeed(
					startDate = "2015-09-12",
					endDate = "2015-09-08",
					apiKey = "DEMO_KEY"
				)
				Timber.d(data.toString())
				Timber.d(data.mapDataToDomain().toString())
			}
		}
	}
}