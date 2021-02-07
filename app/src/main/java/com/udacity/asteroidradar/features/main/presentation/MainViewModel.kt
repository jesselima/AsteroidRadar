package com.udacity.asteroidradar.features.main.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.core.connectionchecker.ConnectionChecker
import com.udacity.asteroidradar.core.extensions.getCurrentDate
import com.udacity.asteroidradar.core.sharedprefs.SharedPrefStorage
import com.udacity.asteroidradar.features.main.domain.usecase.AsteroidsFeedUseCase
import com.udacity.asteroidradar.features.main.domain.usecase.PictureOfTheDayUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val HAS_LAUNCH_APP_PREVIOUSLY = "HAS_LAUNCH_APP_PREVIOUSLY"
private const val SHOULD_SHOW_METRICS_INFO = "HAS_SEEN_METRICS_INFO"

class MainViewModel(
	private val asteroidsFeedUseCase: AsteroidsFeedUseCase,
	private val pictureOfTheDayUseCase: PictureOfTheDayUseCase,
	private val sharedPrefStorage: SharedPrefStorage,
	connectionChecker: ConnectionChecker
) : ViewModel() {

	private val _asteroidsState = MutableLiveData<AsteroidsState>()
	val asteroidsState: LiveData<AsteroidsState> = _asteroidsState

	private val _picturesState = MutableLiveData<PicturesState>()
	val picturesState: LiveData<PicturesState> = _picturesState

	init {
		val isConnected = connectionChecker.isConnected()
		validateShouldRequestRemoteData(isConnected = isConnected)
	}

	private fun validateShouldRequestRemoteData(isConnected: Boolean) {

		_picturesState.value = PicturesState(isLoadingPictures = true)
		_asteroidsState.value = AsteroidsState(isLoadingAsteroids = true)

		viewModelScope.launch {

			withContext(Dispatchers.IO) {
				val hasLaunchTheAppPreviously = sharedPrefStorage.getBooleanValue(key = HAS_LAUNCH_APP_PREVIOUSLY)
				if (hasLaunchTheAppPreviously.not()) {
					if (isConnected) {
						requestRemoteData()
					} else {
						/**
						 * This delay function is here to improve visual experience with lottie
						 * loading animation. This avoid the blink loading whe query local data
						 */
						delay(1000)
						getLocalAsteroidsFeed()
						getLocalPictureOfTheLastSevenDays()
					}
				} else {
					/**
					 * This delay function is here to improve visual experience with lottie
					 * loading animation. This avoid the blink loading whe query local data
					 */
					delay(1000)
					getLocalAsteroidsFeed()
					getLocalPictureOfTheLastSevenDays()
				}
			}
		}
	}

	private fun getLocalPictureOfTheLastSevenDays() {
		viewModelScope.launch {
			val data = withContext(Dispatchers.IO) {
				pictureOfTheDayUseCase.getLocalPictureOfTheDayLastSevenDays()
			}
			_picturesState.value = PicturesState(picturesResult = data, isLoadingPictures = false)
		}
	}

	private fun getLocalAsteroidsFeed() {
		viewModelScope.launch {
			val data = withContext(Dispatchers.IO) {
				asteroidsFeedUseCase.getLocalFeed()
			}
			_asteroidsState.value = AsteroidsState(asteroidsResult = data, isLoadingAsteroids = false)
		}
	}

	private suspend fun requestRemoteData() {
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
			_picturesState.value = PicturesState(pictureByDate = data)
		}
	}

	fun getShouldShowMetricsInfoDialog() : Boolean {
		return sharedPrefStorage.getBooleanValue(key = SHOULD_SHOW_METRICS_INFO)
	}

	fun saveShouldShowMetricsInfoDialog(shouldShowAgain: Boolean) {
		sharedPrefStorage.saveValue(key = SHOULD_SHOW_METRICS_INFO, value = shouldShowAgain)
	}
}