package com.udacity.asteroidradar.core.extensions

import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

/**
 * Created by jesselima on 21/01/21.
 * This is a part of the project Asteroid Radar.
 */
private const val VERTICAL_OFFSET_FOR_EXPANDED = 0

abstract class AppBarStateChangeListener: AppBarLayout.OnOffsetChangedListener {

	private var currentAppBarState: AppBarState? = AppBarState.IDLE

	override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
		when {
			verticalOffset == VERTICAL_OFFSET_FOR_EXPANDED -> {
				setCurrentStateAndNotify(appBarLayout = appBarLayout, appBarState = AppBarState.EXPANDED)
			}
			abs(verticalOffset) >= appBarLayout.totalScrollRange -> {
				setCurrentStateAndNotify(appBarLayout = appBarLayout, appBarState = AppBarState.COLLAPSED)
			}
			else -> {
				setCurrentStateAndNotify(appBarLayout = appBarLayout, appBarState = AppBarState.IDLE)
			}
		}
	}

	private fun setCurrentStateAndNotify(appBarLayout: AppBarLayout?, appBarState: AppBarState) {
		if (currentAppBarState != null) {
			onStateChanged(appBarLayout = appBarLayout, appBarState = appBarState)
		}
		currentAppBarState = appBarState

	}

	abstract fun onStateChanged(appBarLayout: AppBarLayout?, appBarState: AppBarState)
}


enum class AppBarState {
	EXPANDED,
	COLLAPSED,
	IDLE
}