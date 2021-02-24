package com.udacity.asteroidradar.features.mainscreen.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.core.connectionchecker.ConnectionChecker
import com.udacity.asteroidradar.core.extensions.getCurrentDate
import com.udacity.asteroidradar.core.sharedprefs.SharedPrefStorage
import com.udacity.asteroidradar.features.mainscreen.domain.usecase.AsteroidsFeedUseCase
import com.udacity.asteroidradar.features.mainscreen.domain.usecase.PictureOfTheDayUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val HAS_RETRIEVED_REMOTE_ASTEROIDS_DATA_PREVIOUSLY =
	"HAS_RETRIEVED_REMOTE_ASTEROIDS_DATA_PREVIOUSLY"
private const val HAS_RETRIEVED_REMOTE_PICTURES_DATA_PREVIOUSLY =
	"HAS_RETRIEVED_REMOTE_PICTURES_DATA_PREVIOUSLY"

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

	private val _affectedDataItems = MutableLiveData<Int?>()
	val affectedDataItems: LiveData<Int?> = _affectedDataItems

	init {
		val isConnected = connectionChecker.isConnected()
		validateShouldRequestRemoteData(isConnected = isConnected)
	}

	private fun validateShouldRequestRemoteData(isConnected: Boolean) {

		_picturesState.value = PicturesState(isLoadingPictures = true)
		_asteroidsState.value = AsteroidsState(isLoadingAsteroids = true)

		viewModelScope.launch {
			withContext(Dispatchers.IO) {
				val hasRetrievedAsteroidsDataPreviously = sharedPrefStorage.getBooleanValue(
					key = HAS_RETRIEVED_REMOTE_ASTEROIDS_DATA_PREVIOUSLY
				)

				val hasRetrievedPicturesDataPreviously = sharedPrefStorage.getBooleanValue(
					key = HAS_RETRIEVED_REMOTE_PICTURES_DATA_PREVIOUSLY
				)

				if (hasRetrievedAsteroidsDataPreviously.not()) {
					if (isConnected) {
						requestRemoteAsteroidsData()
					} else {
						/**
						 * This delay function is here to improve visual experience with lottie
						 * loading animation. This avoid the loading animation to blink when query
						 * for local data.
						 */
						delay(1000)
						getLocalAsteroidsFeed()
					}
				} else {
					/**
					 * This delay function is here to improve visual experience with lottie
					 * loading animation. This avoid the loading animation to blink when query
					 * for local data.
					 */
					delay(1000)
					getLocalAsteroidsFeed()
				}

				if (hasRetrievedPicturesDataPreviously.not()) {
					if (isConnected) {
						requestRemotePicturesData()
					} else {
						/**
						 * This delay function is here to improve visual experience with lottie
						 * loading animation. This avoid the loading animation to blink when query
						 * for local data.
						 */
						delay(1000)
						getLocalPictureOfTheLastSevenDays()
					}
				} else {
					/**
					 * This delay function is here to improve visual experience with lottie
					 * loading animation. This avoid the loading animation to blink when query
					 * for local data.
					 */
					delay(1000)
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
			sharedPrefStorage.saveValue(
				key = HAS_RETRIEVED_REMOTE_PICTURES_DATA_PREVIOUSLY,
				value = data.isNotEmpty()
			)
			if (data.isNotEmpty()) _picturesState.value = null
			_picturesState.value = PicturesState(
				picturesResult = data,
				isLoadingPictures = false
			)
		}
	}

	fun getLocalAsteroidsFeed() {
		viewModelScope.launch {
			val data = withContext(Dispatchers.IO) {
				asteroidsFeedUseCase.getLocalFeed()
			}
			sharedPrefStorage.saveValue(
				key = HAS_RETRIEVED_REMOTE_ASTEROIDS_DATA_PREVIOUSLY,
				value = data.isNotEmpty()
			)
			_asteroidsState.value = asteroidsState.value?.copy(
				asteroidsResult = data,
				isLoadingAsteroids = false
			)
		}
	}

	fun requestRemoteAsteroidsData() {
		viewModelScope.launch {
			_asteroidsState.value = asteroidsState.value?.copy(isLoadingAsteroids = true)
			withContext(Dispatchers.IO) {
				asteroidsFeedUseCase.getRemoteFeed()
			}
			getLocalAsteroidsFeed()
		}
	}

	private fun requestRemotePicturesData() {
		viewModelScope.launch {
			withContext(Dispatchers.IO) {
				pictureOfTheDayUseCase.getRemotePictureOfTheLastSevenDays()
			}
			getLocalPictureOfTheLastSevenDays()
		}
	}

	/**
	 * App Bar action menu filters methods
 	 */
	fun getAllLocalFavoritesPicturesOfTheDay() {
		viewModelScope.launch {
			val data = withContext(Dispatchers.IO) {
				pictureOfTheDayUseCase.getAllLocalFavoritesPicturesOfTheDay()
			}
			if (data.isNotEmpty()) _picturesState.value = null
			_picturesState.value = PicturesState(picturesResult = data)
		}
	}

	fun getTodayAsteroids() {
		viewModelScope.launch {
			_asteroidsState.value = asteroidsState.value?.copy(isLoadingAsteroids = true)
			val data = withContext(Dispatchers.IO) {
				asteroidsFeedUseCase.getTodayAsteroids()
			}
			if (data.isNotEmpty()) _asteroidsState.value = null
			_asteroidsState.value = AsteroidsState(asteroidsResult = data)
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

	fun deleteAllPictures() {
		viewModelScope.launch {
			val deleteRows = withContext(Dispatchers.IO) {
				pictureOfTheDayUseCase.deleteAllPictures()
			}
			_affectedDataItems.value = deleteRows
			_affectedDataItems.value = null
			updateLocalTodayPictureOfTheDayWithRemoteData()
		}
	}

	fun deleteFavoritesOnly() {
		viewModelScope.launch {
			val deletedFavorites = withContext(Dispatchers.IO) {
				pictureOfTheDayUseCase.deleteFavoritesOnly()
			}
			_affectedDataItems.value = deletedFavorites
			getLocalPictureOfTheLastSevenDays()
		}
	}

	fun deleteNotFavoritesOnly() {
		viewModelScope.launch {
			val deletedRows = withContext(Dispatchers.IO) {
				pictureOfTheDayUseCase.deleteNotFavoritesOnly()
			}
			_affectedDataItems.value = deletedRows
			_affectedDataItems.value = null
			getLocalPictureOfTheLastSevenDays()
		}
	}

	private fun updateLocalTodayPictureOfTheDayWithRemoteData(date: String = getCurrentDate()) {
		viewModelScope.launch {
			withContext(Dispatchers.IO) {
				pictureOfTheDayUseCase.getRemotePictureOfTheDayByDate(date)

			}
			_picturesState.value = null
			getLocalPictureOfTheLastSevenDays()
		}
	}

	fun resetFavorites() {
		viewModelScope.launch {
			val updatedRows = withContext(Dispatchers.IO) {
				pictureOfTheDayUseCase.resetFavorites()
			}
			_affectedDataItems.value = updatedRows
			_affectedDataItems.value = null
		}
	}

}