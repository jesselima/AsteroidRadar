package com.udacity.asteroidradar.core.extensions

/**
 * Created by jesselima on 31/01/21.
 * This is a part of the project Asteroid Radar.
 */

fun String.mapDateToUniqueId() : Long {
	return this.replace("-", "").toLong()
}