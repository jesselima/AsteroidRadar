package com.udacity.asteroidradar.features.mainscreen.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.core.connectionchecker.ConnectionChecker
import com.udacity.asteroidradar.core.sharedprefs.SharedPrefStorage
import com.udacity.asteroidradar.features.mainscreen.domain.usecase.AsteroidsFeedUseCase
import com.udacity.asteroidradar.features.mainscreen.domain.usecase.PictureOfTheDayUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val HAS_RETRIEVED_DATA_PREVIOUSLY = "HAS_RETRIEVED_DATA_PREVIOUSLY"

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
				val hasRetrievedDataPreviously = sharedPrefStorage.getBooleanValue(
					key = HAS_RETRIEVED_DATA_PREVIOUSLY
				)
				if (hasRetrievedDataPreviously.not()) {
					if (isConnected) {
						requestRemoteAsteroidsData()
						requestRemotePicturesData()
					} else {
						/**
						 * This delay function is here to improve visual experience with lottie
						 * loading animation. This avoid the loading animation to blink when query
						 * for local data.
						 */
						delay(1000)
						getLocalAsteroidsFeed()
						getLocalPictureOfTheLastSevenDays()
					}
				} else {
					/**
					 * This delay function is here to improve visual experience with lottie
					 * loading animation. This avoid the loading animation to blink when query
					 * for local data.
					 */
					delay(1000)
					getLocalAsteroidsFeed()
					getLocalPictureOfTheLastSevenDays()
				}
			}
		}
	}

	fun getLocalPictureOfTheLastSevenDays() {
		viewModelScope.launch {
			val data = withContext(Dispatchers.IO) {
				pictureOfTheDayUseCase.getLocalPictureOfTheDayLastSevenDays()
			}
			if (data.isNotEmpty()) {
				_picturesState.value = null
				_picturesState.value = PicturesState(picturesResult = data)
			}
		}
	}

	fun getAllLocalFavoritesPicturesOfTheDay() {
		viewModelScope.launch {
			val data = withContext(Dispatchers.IO) {
				pictureOfTheDayUseCase.getAllLocalFavoritesPicturesOfTheDay()
			}
			if (data.isNotEmpty()) {
				_picturesState.value = null
				_picturesState.value = PicturesState(picturesResult = data)
			}
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

	fun getTodayAsteroids() {
		viewModelScope.launch {
			_asteroidsState.value = AsteroidsState(isLoadingAsteroids = true)
			val data = withContext(Dispatchers.IO) {
				asteroidsFeedUseCase.getLocalFeed()
			}
			_asteroidsState.value = AsteroidsState(asteroidsResult = data, isLoadingAsteroids = false)
		}
	}

	fun requestRemoteAsteroidsData() {
		viewModelScope.launch {
			_asteroidsState.value = AsteroidsState(isLoadingAsteroids = true)
			withContext(Dispatchers.IO) {
				asteroidsFeedUseCase.getRemoteFeed()
			}
			getLocalAsteroidsFeed()
			sharedPrefStorage.saveValue(key = HAS_RETRIEVED_DATA_PREVIOUSLY, value = true)
		}
	}

	private suspend fun requestRemotePicturesData() {
		viewModelScope.launch {
			withContext(Dispatchers.IO) {
				pictureOfTheDayUseCase.getRemotePictureOfTheLastSevenDays()
			}
			getLocalPictureOfTheLastSevenDays()
			sharedPrefStorage.saveValue(key = HAS_RETRIEVED_DATA_PREVIOUSLY, value = true)
		}
	}

	fun sortAsteroidsBySpeed() {
		asteroidsState.value?.let { state ->
			_asteroidsState.value = asteroidsState.value?.copy(
				asteroidsResult = state.asteroidsResult.sortedByDescending {
					it.relativeVelocityKilometersPerHour
				}
			)
		}
	}

	fun sortAsteroidsByDistance() {
		asteroidsState.value?.let { state ->
			_asteroidsState.value = asteroidsState.value?.copy(
				asteroidsResult = state.asteroidsResult.sortedByDescending {
					it.distanceFromEarthAu
				}
			)
		}
	}

	fun sortAsteroidsByDate() {
		asteroidsState.value?.let { state ->
			_asteroidsState.value = asteroidsState.value?.copy(
				asteroidsResult = state.asteroidsResult.sortedByDescending {
					it.date
				}
			)
		}
	}
}