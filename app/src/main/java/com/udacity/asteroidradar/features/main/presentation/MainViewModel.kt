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

	private fun getPictureOfTheDayFeed() {
		viewModelScope.launch {
			val data = withContext(Dispatchers.IO) {
				pictureOfTheDayUseCase.getLocalPictureOfTheDay()
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
				getPictureOfTheDayFeed()
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
			getPictureOfTheDayFeed()
			sharedPrefStorage.saveValue(key = HAS_LAUNCH_APP_PREVIOUSLY, value = true)
		}
	}


	/**
	 * USE THIS METHOD TO INSERT DUMMY DATA INTO THE LOCAL DATABASE.
	 *
	 */
//	private fun insertDummyData() {
//		viewModelScope.launch {
//			withContext(Dispatchers.IO) {
//
//				for(index in 10..30) {
//					pictureOfTheDayLocalDataSource.savePictureOfTheDayToLocalDatabase(PictureOfDay(
//						mediaType = "image",
//						title = "Hello Space $index",
//						imageUrl = "https://apod.nasa.gov/apod/image/2101/OldMan_Guerra_960_lines.jpg",
//						highDefinitionImageUrl = "https://apod.nasa.gov/apod/image/2101/OldMan_Guerra_6000.jpg",
//						date = "2021-01-${index}",
//						explanation = "The night sky is filled with stories. Cultures throughout history have projected some of their most enduring legends onto the stars above. Generations of people see these stellar constellations, hear the associated stories, and pass them down. Featured here is the perhaps unfamiliar constellation of the Old Man, long recognized by the Tupi peoples native to regions of South America now known as Brazil.",
//						createdAt = getCurrentDate(),
//						modifiedAt = getCurrentDate(),
//					))
//				}
//
//				for(index in 10..30) {
//					asteroidsFeedLocalDataSource.saveFeedToLocalDatabase(
//						listOf(
//							AsteroidsFeedItem(
//								codename = "Space Boy $index",
//								closeApproachDate = "2020-02-$index",
//								absoluteMagnitude = 0.3,
//								estimatedDiameter = 0.1,
//								relativeVelocity = 0.7,
//								distanceFromEarth = 1.2,
//								isPotentiallyHazardous = true,
//								date = "2020-02-$index",
//								createdAt = getCurrentDate(),
//								modifiedAt = getCurrentDate(),
//							)
//						)
//					)
//				}
//			}
//		}
//	}
}