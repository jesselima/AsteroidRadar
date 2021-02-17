package com.udacity.asteroidradar.core.extensions

import java.text.DecimalFormat

/**
 * Created by jesselima on 16/02/21.
 * This is a part of the project Asteroid Radar.
 */
private const val ONE_DECIMAL = 1
private const val TWO_DECIMALS = 2
private const val THREE_DECIMALS = 3

fun formatNumberToString(number: Number, decimals: Int = 0) : String {
	val format = when(decimals) {
		ONE_DECIMAL -> "###,###,###.0"
		TWO_DECIMALS -> "###,###,###.00"
		THREE_DECIMALS -> "###,###,###.000"
		else -> "###,###,##0"
	}
	return DecimalFormat(format).format(number);
}