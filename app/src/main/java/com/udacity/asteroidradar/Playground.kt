package com.udacity.asteroidradar

import com.udacity.asteroidradar.core.extensions.padValue
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by jesselima on 15/02/21.
 * This is a part of the project Asteroid Radar.
 */
fun main() {
	val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
	//println(date)


//	val diff: Long = date1.getTime() - date2.getTime()
//	val seconds = diff / 1000
//	val minutes = seconds / 60
//	val hours = minutes / 60
//	val days = hours / 24

	val calendar = Calendar.getInstance(Locale.getDefault())
	val year = calendar.get(Calendar.YEAR)
	val month = calendar.get(Calendar.MONTH).plus(1)

	val day = calendar.get(Calendar.DAY_OF_MONTH)
	//println(day)

	val dayMinus = calendar.get(Calendar.DAY_OF_MONTH).minus(17)
	//println(dayMinus)


	val today = calendar.get(Calendar.DATE)
	println(today)
	calendar.set(Calendar.DATE, 16)
	val yesterday = calendar.get(Calendar.DATE)
	println(yesterday)


}


fun getDateDaysBehind(daysBehind: Int) : List<String> {
	val calendar = Calendar.getInstance(Locale.getDefault())
	val year = calendar.get(Calendar.YEAR)
	val month = calendar.get(Calendar.MONTH).plus(1)
	val day = calendar.get(Calendar.DAY_OF_MONTH)

	val listOdDates: MutableList<String> = mutableListOf("$year-${padValue(month)}-${padValue(day)}")

	for (number in 1..daysBehind) {
		listOdDates.add("$year-${padValue(month)}-${padValue(day.minus(number))}")
	}
	return listOdDates.toList()
}
