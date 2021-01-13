package com.udacity.asteroidradar.core.extensions

import java.util.Calendar
import java.util.Locale

/**
 * Created by jesselima on 10/01/21.
 * This is a part of the project Asteroid Radar.
 */

fun getCurrentDate() : String {
	val calendar = Calendar.getInstance(Locale.getDefault())
	val currentYear = calendar.get(Calendar.YEAR)
	val currentMonth = calendar.get(Calendar.MONTH)
	val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
	return "$currentYear-$currentMonth-$currentDay"
}