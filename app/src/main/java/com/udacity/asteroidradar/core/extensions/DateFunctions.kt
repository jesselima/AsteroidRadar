package com.udacity.asteroidradar.core.extensions

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Calendar
import java.util.Date

/**
 * Created by jesselima on 10/01/21.
 * This is a part of the project Asteroid Radar.
 */

private const val DATE_DATABASE_FORMAT = "yyyy-MM-dd"
private const val NUMBER_OF_DAY_BEHIND = 6
private const val MAX_NUMBER_TO_PAD_WITH_ZERO = 10

fun getCurrentDate() : String {
	val formatter = SimpleDateFormat(DATE_DATABASE_FORMAT, Locale.getDefault())
	return formatter.format(Date())
}

fun getLastSevenDays() : List<String> {
	val calendar = Calendar.getInstance(Locale.getDefault())
	val year = calendar.get(Calendar.YEAR)
	val month = calendar.get(Calendar.MONTH).plus(1)
	val day = calendar.get(Calendar.DAY_OF_MONTH)

	val listOdDates: MutableList<String> = mutableListOf("$year-${padValue(month)}-${padValue(day)}")

	for (number in 1..NUMBER_OF_DAY_BEHIND) {
		listOdDates.add("$year-${padValue(month)}-${padValue(day.minus(number))}")
	}
	return listOdDates.toList()
}

fun getDateSixDaysBehind() : String {
	val calendar = Calendar.getInstance(Locale.getDefault())
	val year = calendar.get(Calendar.YEAR)
	val month = calendar.get(Calendar.MONTH).plus(1)
	val day = calendar.get(Calendar.DAY_OF_MONTH)
	return "$year-${padValue(month)}-${padValue(day.minus(NUMBER_OF_DAY_BEHIND))}"
}

fun padValue(number: Int) : String {
	return if (number < MAX_NUMBER_TO_PAD_WITH_ZERO) {
		"0$number"
	} else {
		"$number"
	}
}