package com.udacity.asteroidradar.features.picturesfavoritesgallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.features.mainscreen.domain.entities.PictureOfDay
import com.udacity.asteroidradar.features.mainscreen.domain.usecase.PictureOfTheDayUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by jesselima on 21/02/21.
 * This is a part of the project Asteroid Radar.
 */

class PicturesGalleryViewModel(
	private val pictureOfTheDayUseCase: PictureOfTheDayUseCase,
) : ViewModel() {

	private val _picturesOfDay = MutableLiveData<List<PictureOfDay>>()
	val picturesOfDay: LiveData<List<PictureOfDay>?> = _picturesOfDay

	init {
		getAllLocalFavoritesPicturesOfTheDay()
	}

	fun getAllLocalFavoritesPicturesOfTheDay() {
		viewModelScope.launch {
			val data = withContext(Dispatchers.IO) {
				pictureOfTheDayUseCase.getAllLocalFavoritesPicturesOfTheDay()
			}
			_picturesOfDay.value = data
		}
	}
}