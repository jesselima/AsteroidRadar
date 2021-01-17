package com.udacity.asteroidradar.core.extensions

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

/**
 * Created by jesselima on 10/01/21.
 * This is a part of the project Asteroid Radar.
 */

private const val DATE_DATABASE_FORMAT = "yyyy-MM-dd"

fun getCurrentDate() : String {
	val formatter = SimpleDateFormat(DATE_DATABASE_FORMAT, Locale.getDefault())
	return formatter.format(Date())
}