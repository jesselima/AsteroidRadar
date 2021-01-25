package com.udacity.asteroidradar.core.extensions

import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat

/**
 * Created by jesselima on 25/01/21.
 * This is a part of the project Asteroid Radar.
 */
fun ImageView.setIconColorState(@ColorRes color: Int) {
	this.context?.let {
		DrawableCompat.setTint(
			DrawableCompat.wrap(this.drawable),
			ContextCompat.getColor(it, color)
		)
	}
}
