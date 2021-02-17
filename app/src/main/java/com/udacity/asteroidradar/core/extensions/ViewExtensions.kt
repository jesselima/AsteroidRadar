package com.udacity.asteroidradar.core.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import com.udacity.asteroidradar.R


fun View.showWithFadeIn() {
    this.visibility = View.VISIBLE
    this.startAnimation(
        AnimationUtils.loadAnimation(
                context,
                R.anim.fade_in_animation
        )
    )
}

fun View.showWithLongFadeIn() {
    this.visibility = View.VISIBLE
    this.startAnimation(
        AnimationUtils.loadAnimation(
            context,
            R.anim.fade_in_animation
        )
    )
}

fun View.hideWithFadeOut(fastAnimation: Boolean = false) {
    val animation = if(fastAnimation) {
        R.anim.fade_out_animation_short
    } else {
        R.anim.fade_out_animation
    }
    this.startAnimation(AnimationUtils.loadAnimation(context, animation))
    this.visibility = View.GONE
}

fun View.showListItemWithFadeIn() {
    this.startAnimation(
        AnimationUtils.loadAnimation(
            context,
            R.anim.fade_in
        )
    )
}

fun View.toggleVisibility() {
    if(this.isVisible) {
        this.visibility = View.GONE
    } else {
        this.visibility = View.VISIBLE
    }
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false) : View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}
