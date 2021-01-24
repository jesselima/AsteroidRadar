package com.udacity.asteroidradar.core.extensions

import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

fun getPagTransformer() : ViewPager2.PageTransformer {
    return ViewPager2.PageTransformer { page, position ->
        val absPos = abs(position)
        page.apply {
            translationX =  -absPos * width
            translationY = 0f
            val scale = if (absPos > 1) 0.3F else 1 - absPos * 1.0F
            scaleX = scale
            scaleY = scale
            alpha = (1 - absPos)
        }
    }
}