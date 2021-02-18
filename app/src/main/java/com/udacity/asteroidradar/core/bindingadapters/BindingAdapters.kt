package com.udacity.asteroidradar.core.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.core.extensions.formatDate
import com.udacity.asteroidradar.core.extensions.formatNumberToString
import com.udacity.asteroidradar.core.extensions.hideWithFadeOut
import com.udacity.asteroidradar.core.extensions.showWithLongFadeIn

private const val ONE_DECIMALS = "%.1f"
private const val TWO_DECIMALS = "%.2f"
private const val THREE_DECIMALS = "%.3f"

@BindingAdapter("statusHazardousStatusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_emoji_angry)
    } else {
        imageView.setImageResource(R.drawable.ic_emoji_friendly)
    }
}

@BindingAdapter("toggleVisibility")
fun bindIsVisible(view: View, shouldDisplayView: Boolean) {
    if(shouldDisplayView) {
        view.showWithLongFadeIn()
    } else {
        view.hideWithFadeOut()
    }
}

@BindingAdapter("showWhenListIsEmpty")
fun bindShowWhenEmpty(view: View, isEmpty: Boolean) {
    if(isEmpty) {
        view.showWithLongFadeIn()
    } else {
        view.hideWithFadeOut()
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindAsteroidStatusStatusImage(lottieAnimationView: LottieAnimationView, isHazardous: Boolean) {
    if (isHazardous) {
        lottieAnimationView.setAnimation(R.raw.angry_emoji)
        lottieAnimationView.playAnimation()
        lottieAnimationView.contentDescription = lottieAnimationView.context.getString(
            R.string.content_description_asteroid_status_hazardous
        )
    } else {
        lottieAnimationView.setAnimation(R.raw.smilie_emoji)
        lottieAnimationView.playAnimation()
        lottieAnimationView.contentDescription = lottieAnimationView.context.getString(
            R.string.content_description_asteroid_status_not_hazardous
        )
    }
}

@BindingAdapter("bindDetailsStatusTitle")
fun bindDetailsStatusTitle(textView: TextView, isHazardous: Boolean) {
    if (isHazardous) {
        textView.text = textView.context.getString(R.string.title_is_potentially_hazardous)
        textView.setTextColor(ContextCompat.getColor(textView.context, R.color.secondaryColor))
    } else {
        textView.text = textView.context.getString(R.string.title_not_potentially_hazardous)
        textView.setTextColor(ContextCompat.getColor(textView.context, R.color.primaryTextColor))
    }
}

@BindingAdapter("formatDate")
fun bindTextFormatDate(textView: TextView, date: String?) {
    date?.let {
        textView.text = formatDate(date = it)
    }
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    textView.text = String.format(
        textView.context.getString(R.string.unit_km_format),
        number
    )
}

@BindingAdapter("formatRelativeVelocityMilesPerHour")
fun bindFormatRelativeVelocityMilesPerHour(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(
        R.string.unit_miles_hour_format),
        formatNumberToString(number)
    )
}

@BindingAdapter("formatRelativeVelocityKilometersPerHour")
fun bindFormatRelativeVelocityKilometersPerHour(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(
        R.string.unit_kilometers_per_hour_format),
        formatNumberToString(number)
    )
}

@BindingAdapter("formatRelativeVelocityKilometersPerSecond")
fun bindFormatRelativeVelocityKilometersPerSecond(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(
        R.string.unit_kilometers_per_second_format),
        formatNumberToString(number)
    )
}

@BindingAdapter("astronomicalUnitText")
fun bindAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = context.getString(
        R.string.unit_astronomical_format,
        String.format(TWO_DECIMALS, number)
    )
}

@BindingAdapter("absoluteMagnitudeUnitText")
fun bindAbsoluteMagnitude(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = context.getString(
        R.string.unit_astronomical_format,
        String.format(ONE_DECIMALS, number)
    )
}

@BindingAdapter("lunarUnitText")
fun bindLunarUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = context.getString(
        R.string.unit_lunar_format,
        String.format(TWO_DECIMALS, number)
    )
}

@BindingAdapter("kilometersUnitText")
fun bindKilometersUnitTextUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(
        R.string.unit_km_format),
        String.format(THREE_DECIMALS, number)
    )
}

@BindingAdapter("milesUnitText")
fun bindMilesTextUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(
        R.string.unit_miles_format),
        formatNumberToString(number = number)
    )
}

@BindingAdapter("imageUrl")
fun imageUrl(imageView: ImageView, url: String?) {
    url?.let {
        Picasso.get()
            .load(it)
            .placeholder(R.drawable.place_holder_no_image)
            .error(R.drawable.ic_astronaut_image_not_found)
            .into(imageView);
    }
}
