package com.udacity.asteroidradar.features.detail.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.features.detail.domain.usecase.AsteroidDetailsLocalUseCase
import com.udacity.asteroidradar.features.main.domain.entities.AsteroidsFeedItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetailsViewModel(
	private val asteroidDetailsLocalUseCase: AsteroidDetailsLocalUseCase,
) : ViewModel() {

	private val _asteroidsFeedItem = MutableLiveData<AsteroidsFeedItem>()
	val asteroidsFeedItem: LiveData<AsteroidsFeedItem> = _asteroidsFeedItem

	fun getLocalAsteroidById(id: Long) {
		viewModelScope.launch {
			val data = withContext(Dispatchers.IO) {
				asteroidDetailsLocalUseCase.getLocalAsteroidById(id = id)
			}
			_asteroidsFeedItem.value = data
		}
	}
}