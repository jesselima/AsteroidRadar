package com.udacity.asteroidradar.core.connectionchecker

import android.content.Context
import com.udacity.asteroidradar.core.extensions.isConnected

/**
 * Created by jesselima on 31/01/21.
 * This is a part of the project Asteroid Radar.
 */
class ConnectionCheckerImpl(private val context: Context?) : ConnectionChecker {
	override fun isConnected(): Boolean {
		return context?.isConnected() ?: false
	}
}