package com.udacity.asteroidradar.core.extensions

/**
 * Created by jesselima on 9/02/21.
 * This is a part of the project Asteroid Radar.
 */
inline fun <T> T?.whenNull(block: T?.() -> Unit): T? {
	if (this == null) block()
	return this@whenNull
}

inline fun <T> T?.whenNotNull(block: T.() -> Unit): T? {
	this?.block()
	return this@whenNotNull
}

private fun howToUseExample() {
	val text: String? = null
	text.whenNotNull {
		// Do something
	}.whenNull {
		// Do something
	}
}
