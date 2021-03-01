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
private const val API_URL_QUERY_FORMAT = "yyyy-dd-MM"

fun getCurrentDate() : String {
	val formatter = SimpleDateFormat(DATE_DATABASE_FORMAT, Locale.getDefault())
	return formatter.format(Date())
}

fun getCurrentDateApiQueryParamFormat() : String {
	val formatter = SimpleDateFormat(API_URL_QUERY_FORMAT, Locale.getDefault())
	return formatter.format(Date())
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
