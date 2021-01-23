package com.udacity.asteroidradar.features.main.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.core.sharedprefs.SharedPrefStorage
import com.udacity.asteroidradar.features.main.domain.entities.AsteroidsFeedItem
import com.udacity.asteroidradar.features.main.domain.entities.PictureOfDay
import com.udacity.asteroidradar.features.main.domain.usecase.AsteroidsFeedUseCase
import com.udacity.asteroidradar.features.main.domain.usecase.PictureOfTheDayUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val HAS_LAUNCH_APP_PREVIOUSLY = "HAS_LAUNCH_APP_PREVIOUSLY"

class MainViewModel(
	private val asteroidsFeedUseCase: AsteroidsFeedUseCase,
	private val pictureOfTheDayUseCase: PictureOfTheDayUseCase,
	private val sharedPrefStorage: SharedPrefStorage
) : ViewModel() {

	init {
		hasLaunchTheAppPreviously()
	}

	private val _asteroidsFeed = MutableLiveData<List<AsteroidsFeedItem>>()
	val asteroidFeed: LiveData<List<AsteroidsFeedItem>> = _asteroidsFeed

	private val _pictureOfTheDay = MutableLiveData<PictureOfDay>()
	val pictureOfTheDay: LiveData<PictureOfDay> = _pictureOfTheDay

	private fun getAsteroidsFeed() {
		viewModelScope.launch {
			val data = withContext(Dispatchers.IO) {
				asteroidsFeedUseCase.getLocalFeed()
			}
			_asteroidsFeed.value = data
		}
	}

	private fun getPictureOfTheDay() {
		viewModelScope.launch {
			val data = withContext(Dispatchers.IO) {
				pictureOfTheDayUseCase.getLocalPictureOfTheDay()
			}
			_pictureOfTheDay.value = data
		}
	}

	private fun getRemotePictureOfTheDayByDate(date: String) {
		viewModelScope.launch {
			val data = withContext(Dispatchers.IO) {
				pictureOfTheDayUseCase.getRemotePictureOfTheDayByDate(date = date)
			}
			_pictureOfTheDay.value = data
		}
	}

	private fun hasLaunchTheAppPreviously() {
		viewModelScope.launch {
			val hasLaunchTheAppPreviously = withContext(Dispatchers.IO) {
				sharedPrefStorage.getBooleanValue(key = HAS_LAUNCH_APP_PREVIOUSLY)
			}
			if (hasLaunchTheAppPreviously) {
				getAsteroidsFeed()
				getPictureOfTheDay()
			} else {
				requestRemoteData()
			}
		}
	}

	private fun requestRemoteData() {
		viewModelScope.launch {
			withContext(Dispatchers.IO) {
				asteroidsFeedUseCase.getRemoteFeed()
				pictureOfTheDayUseCase.getRemotePictureOfTheDay()
			}
			getAsteroidsFeed()
			getPictureOfTheDay()
			sharedPrefStorage.saveValue(key = HAS_LAUNCH_APP_PREVIOUSLY, value = true)
		}
	}
}