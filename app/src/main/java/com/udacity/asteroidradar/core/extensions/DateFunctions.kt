package com.udacity.asteroidradar.core.extensions

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date
import java.util.Calendar

/**
 * Created by jesselima on 10/01/21.
 * This is a part of the project Asteroid Radar.
 */

private const val DATE_DATABASE_FORMAT = "yyyy-MM-dd"
private const val NUMBER_INCREMENT_MONTH_CORRECTION = 1
private const val NUMBER_OF_DAY_BEHIND = 10
private const val MAX_NUMBER_TO_PAD_WITH_ZERO = 10

fun getCurrentDate() : String {
	val formatter = SimpleDateFormat(DATE_DATABASE_FORMAT, Locale.getDefault())
	return formatter.format(Date())
}

fun getDateForDaysBehind() : String {
	val calendar = Calendar.getInstance(Locale.getDefault())
	val year = calendar.get(Calendar.YEAR)
	val month = calendar.get(Calendar.MONTH).plus(NUMBER_INCREMENT_MONTH_CORRECTION)
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

fun formatDate(date: String) : String {
	val dateInstance = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date)
	val simpleDateFormat = SimpleDateFormat("E dd, MMM yyyy", Locale.getDefault())
	return if(dateInstance != null) {
		simpleDateFormat.format(dateInstance)
	}else {
		date
	}
}
