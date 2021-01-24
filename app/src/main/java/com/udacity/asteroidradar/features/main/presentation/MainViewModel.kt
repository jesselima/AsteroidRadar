package com.udacity.asteroidradar.features.main.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.core.extensions.getCurrentDate
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

	private val _pictureOfTheDayBySelectedDate = MutableLiveData<PictureOfDay>()
	val pictureOfTheDayBySelectedDate: LiveData<PictureOfDay> = _pictureOfTheDayBySelectedDate

	private val _listOfPicturesOfTheDays = MutableLiveData<List<PictureOfDay>>()
	val listOfPicturesOfTheDays: LiveData<List<PictureOfDay>> = _listOfPicturesOfTheDays

	private fun hasLaunchTheAppPreviously() {
		viewModelScope.launch {
			val hasLaunchTheAppPreviously = withContext(Dispatchers.IO) {
				sharedPrefStorage.getBooleanValue(key = HAS_LAUNCH_APP_PREVIOUSLY)
			}
			if (hasLaunchTheAppPreviously) {
				getLocalAsteroidsFeed()
				getLocalPictureOfTheLastSevenDays()
			} else {
				requestRemoteData()
			}
		}
	}

	private fun getLocalAsteroidsFeed() {
		viewModelScope.launch {
			val data = withContext(Dispatchers.IO) {
				asteroidsFeedUseCase.getLocalFeed()
			}
			_asteroidsFeed.value = data
		}
	}

	private fun getLocalPictureOfTheLastSevenDays() {
		viewModelScope.launch {
			val data = withContext(Dispatchers.IO) {
				pictureOfTheDayUseCase.getLocalPictureOfTheDayLastSevenDays()
			}
			_listOfPicturesOfTheDays.value = data
		}
	}

	private fun requestRemoteData() {
		viewModelScope.launch {
			withContext(Dispatchers.IO) {
				asteroidsFeedUseCase.getRemoteFeed()
				pictureOfTheDayUseCase.getRemotePictureOfTheLastSevenDays()
			}
			getLocalAsteroidsFeed()
			getLocalPictureOfTheLastSevenDays()
			sharedPrefStorage.saveValue(key = HAS_LAUNCH_APP_PREVIOUSLY, value = true)
		}
	}

	private fun getPictureOfTheDayByDate(date: String = getCurrentDate()) {
		viewModelScope.launch {
			val data = withContext(Dispatchers.IO) {
				pictureOfTheDayUseCase.getLocalPictureOfTheDayByDate(date = date)
			}
			_pictureOfTheDayBySelectedDate.value = data
		}
	}

}