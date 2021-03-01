package com.udacity.asteroidradar.core.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

/**
 * Created by jesselima on 24/01/21.
 * This is a part of the project Asteroid Radar.
 */

@Suppress("DEPRECATION")
fun Context.isConnected(): Boolean {

	val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

	return when {
		Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
			val network = connectivityManager.activeNetwork ?: return false
			val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
			val isConnectingOrConnected = connectivityManager.activeNetworkInfo?.isConnectedOrConnecting ?: return false
			when {
				networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
				networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
				isConnectingOrConnected -> true
				else -> false
			}
		}
		else -> {
			val networkInfo = connectivityManager.activeNetworkInfo ?: return false
			networkInfo.isConnected
		}
	}
}
