package com.udacity.asteroidradar.features.picturedetails.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.core.extensions.getCurrentDate
import com.udacity.asteroidradar.features.mainscreen.domain.entities.PictureOfDay
import com.udacity.asteroidradar.features.mainscreen.domain.usecase.PictureOfTheDayUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val NO_UPDATE_RESULT = 0

class PictureOfTheDayViewModel(
	private val pictureOfTheDayUseCase: PictureOfTheDayUseCase,
) : ViewModel() {

	private val _saveState = MutableLiveData<Boolean?>()
	val saveState: LiveData<Boolean?> = _saveState

	private val _pictureOfTheDay = MutableLiveData<PictureOfDay>()
	val pictureOfTheDay: LiveData<PictureOfDay> = _pictureOfTheDay

	fun toggleFavoritePictureState(pictureOfDay: PictureOfDay) {
		viewModelScope.launch {
			val result = withContext(Dispatchers.IO) {
				pictureOfTheDayUseCase.updateFavoritePictureState(pictureOfDay)
			}

			val data = withContext(Dispatchers.IO) {
				pictureOfTheDayUseCase.getLocalPictureOfTheDayByDate(date = pictureOfDay.date)
			}
			_pictureOfTheDay.value = data
			_saveState.value = result > NO_UPDATE_RESULT

		}
	}

	private fun getTodayPictureOfTheDayByDate(date: String = getCurrentDate()) {
		viewModelScope.launch {
			val data = withContext(Dispatchers.IO) {
				pictureOfTheDayUseCase.getLocalPictureOfTheDayByDate(date = date)
			}
			_pictureOfTheDay.value = data
		}
	}

	fun setPictureOfTheDay(pictureOfDay: PictureOfDay?) {
		_pictureOfTheDay.value = pictureOfDay
	}
}