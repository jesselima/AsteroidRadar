package com.udacity.asteroidradar.features.mainscreen.presentation

import com.udacity.asteroidradar.features.mainscreen.domain.entities.PictureOfDay

/**
 * Created by jesselima on 7/02/21.
 * This is a part of the project Asteroid Radar.
 */
data class PicturesState(
	val isLoadingPictures: Boolean = false,
	val picturesResult: List<PictureOfDay> = emptyList(),
	val pictureByDate: PictureOfDay? = null,
	val action: (() -> Unit)? = null
)