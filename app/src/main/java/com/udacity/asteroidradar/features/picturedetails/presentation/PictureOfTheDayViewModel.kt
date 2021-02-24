package com.udacity.asteroidradar.features.picturedetails.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.features.mainscreen.domain.entities.PictureOfDay
import com.udacity.asteroidradar.features.mainscreen.domain.usecase.PictureOfTheDayUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val NO_UPDATE_RESULT = 0

class PictureOfTheDayViewModel(
	private val pictureOfTheDayUseCase: PictureOfTheDayUseCase,
) : ViewModel() {

	private val _saveState = MutableLiveData<SaveState>()
	val saveState: LiveData<SaveState?> = _saveState

	private val _pictureOfTheDay = MutableLiveData<PictureOfDay>()
	val pictureOfTheDay: LiveData<PictureOfDay> = _pictureOfTheDay

	private val _pictureFavoriteState = MutableLiveData<Boolean>()
	val pictureFavoriteState: LiveData<Boolean> = _pictureFavoriteState

	fun toggleFavoritePictureState(pictureOfDay: PictureOfDay) {
		viewModelScope.launch {
			val updateResult = withContext(Dispatchers.IO) {
				pictureOfTheDayUseCase.updateFavoritePictureState(
					pictureOfDay.copy(isFavorite = pictureOfDay.isFavorite.not())
				)
			}
			if (updateResult > NO_UPDATE_RESULT) {
				pictureOfDay.id?.let {
					getUpdatedFavoriteStateFromDataBase(pictureId = it)
				}
				_saveState.value = if (pictureOfDay.isFavorite) {
					SaveState.REMOVED
				} else {
					SaveState.SAVED
				}
				_saveState.value = null
			}
		}
	}

	fun getUpdatedFavoriteStateFromDataBase(pictureId: Long) {
		viewModelScope.launch {
			val favoriteState = withContext(Dispatchers.IO) {
				pictureOfTheDayUseCase.getLocalPictureOfTheDayById(id = pictureId)
			}
			_pictureFavoriteState.value = favoriteState?.isFavorite
		}
	}
}

enum class SaveState {
	SAVED,
	REMOVED
}