package com.udacity.asteroidradar.core.bindingadapters

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.core.extensions.formatDate

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_emoji_angry)
    } else {
        imageView.setImageResource(R.drawable.ic_emoji_friendly)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
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

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}
@BindingAdapter("formatDate")
fun bindTextFormatDate(textView: TextView, date: String?) {
    date?.let {
        textView.text = formatDate(date = it)
    }
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
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
