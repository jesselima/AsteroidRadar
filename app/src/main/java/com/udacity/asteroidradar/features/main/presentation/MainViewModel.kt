package com.udacity.asteroidradar.features.main.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.core.extensions.getCurrentDate
import com.udacity.asteroidradar.core.sharedprefs.SharedPrefStorage
import com.udacity.asteroidradar.features.main.domain.usecase.AsteroidsFeedUseCase
import com.udacity.asteroidradar.features.main.domain.usecase.PictureOfTheDayUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val HAS_LAUNCH_APP_PREVIOUSLY = "HAS_LAUNCH_APP_PREVIOUSLY"
private const val ON_LAUNCH_APP_REQUEST_REMOTE_DATA = "ON_LAUNCH_APP_REQUEST_REMOTE_DATA"
private const val SHOULD_SHOW_METRICS_INFO = "HAS_SEEN_METRICS_INFO"

class MainViewModel(
	private val asteroidsFeedUseCase: AsteroidsFeedUseCase,
	private val pictureOfTheDayUseCase: PictureOfTheDayUseCase,
	private val sharedPrefStorage: SharedPrefStorage
) : ViewModel() {

	private val _resultsState = MutableLiveData<MainState>()
	val resultsState: LiveData<MainState> = _resultsState

	fun validateShouldRequestRemoteData(isConnected: Boolean) {
		_resultsState.value = MainState.LoadingAsteroids(isLoadingAsteroids = true)
		viewModelScope.launch {
			withContext(Dispatchers.IO) {
				val hasLaunchTheAppPreviously = sharedPrefStorage.getBooleanValue(key = HAS_LAUNCH_APP_PREVIOUSLY)
				if (hasLaunchTheAppPreviously.not()) {
					if (isConnected) {
						requestRemoteData()
					} else {
						getLocalAsteroidsFeed()
						getLocalPictureOfTheLastSevenDays()
					}
				} else {
					getLocalAsteroidsFeed()
					getLocalPictureOfTheLastSevenDays()
				}
			}
		}
	}

	private fun getLocalAsteroidsFeed() {
		viewModelScope.launch {
			val data = withContext(Dispatchers.IO) {
				asteroidsFeedUseCase.getLocalFeed()
			}
			delay(5000)
			_resultsState.run {
				value = MainState.LoadingAsteroids(isLoadingAsteroids = false)
				postValue(MainState.ResultAsteroidsSuccess(data))
			}
		}
	}

	private fun getLocalPictureOfTheLastSevenDays() {
		viewModelScope.launch {
			val data = withContext(Dispatchers.IO) {
				pictureOfTheDayUseCase.getLocalPictureOfTheDayLastSevenDays()
			}
			delay(10000)
			_resultsState.run {
				value = MainState.LoadingPictures(isLoadingPictures = false)
				postValue(MainState.ResultPicturesOfTheDay(data))
			}
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

	private fun getTodayPictureOfTheDayByDate(date: String = getCurrentDate()) {
		viewModelScope.launch {
			val data = withContext(Dispatchers.IO) {
				pictureOfTheDayUseCase.getLocalPictureOfTheDayByDate(date = date)
			}
			_resultsState.value = MainState.ResultPicturesOfTheDayByDate(data)
		}
	}

	private fun saveOnLaunchAppShouldRequestRemoteData(shouldRequestRemoteData: Boolean) {
		sharedPrefStorage.saveValue(
			key = ON_LAUNCH_APP_REQUEST_REMOTE_DATA,
			value = shouldRequestRemoteData
		)
	}

	private fun getOnLaunchAppShouldRequestRemoteData() : Boolean {
		return sharedPrefStorage.getBooleanValue(key = ON_LAUNCH_APP_REQUEST_REMOTE_DATA)
	}

	fun getShouldShowMetricsInfoDialog() : Boolean {
		return sharedPrefStorage.getBooleanValue(key = SHOULD_SHOW_METRICS_INFO)
	}

	fun saveShouldShowMetricsInfoDialog(shouldShowAgain: Boolean) {
		sharedPrefStorage.saveValue(key = SHOULD_SHOW_METRICS_INFO, value = shouldShowAgain)
	}
}