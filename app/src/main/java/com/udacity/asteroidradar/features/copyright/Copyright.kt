package com.udacity.asteroidradar.features.copyright

/**
 * Created by jesselima on 28/02/21.
 * This is a part of the project Asteroid Radar.
 */
data class Copyright(
	val imageResId: Int,
	val sourceName: String,
	val authorName: String? = null,
	val link: String? = null,
	val isAnimation: Boolean = false
)
