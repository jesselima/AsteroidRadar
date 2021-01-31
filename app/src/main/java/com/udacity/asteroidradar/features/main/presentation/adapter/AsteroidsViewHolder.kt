package com.udacity.asteroidradar.features.main.presentation.adapter

import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.features.main.domain.entities.AsteroidsFeedItem
import kotlinx.android.synthetic.main.item_list_asteroids.view.*

/**
 * Created by jesselima on 17/01/21.
 * This is a part of the project Asteroid Radar.
 */

private const val ASTEROID_ID = "ASTEROID_ID"


class AsteroidsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

	private var view: View = itemView
	private var asteroidId: Long? = null

	init {
		itemView.setOnClickListener(this)
	}

	override fun onClick(v: View?) {
		val asteroidId: Long? = asteroidId
		val bundle = bundleOf(ASTEROID_ID to asteroidId)
		view.findNavController().navigate(R.id.navigateToAsteroidDetails, bundle)
	}

	fun bindDataToView(asteroid: AsteroidsFeedItem) {
		this.asteroidId = asteroid.id

		view.asteroidName.text = asteroid.codename

		view.textAsteroidSpeed.text = view.context.getString(
			R.string.units_format_kilometers,
			String.format("%.2f", asteroid.relativeVelocityKilometersPerHour)
		)

		view.textDistanceFromEarthAu.text = view.context.getString(
			R.string.units_format_astronomical,
			String.format("%.2f", asteroid.distanceFromEarthAu)
		)

		if (asteroid.isPotentiallyHazardous) {
			view.textDistanceFromEarthAu.setTextColor(
				ContextCompat.getColor(view.context, R.color.secondaryColor)
			)
			view.textAsteroidSpeed.setTextColor(
				ContextCompat.getColor(view.context, R.color.secondaryColor)
			)
			view.imageIsPotentiallyHazardous.setImageResource(R.drawable.ic_emoji_angry)
			view.imageIsPotentiallyHazardous.contentDescription = view.context.getString(
				R.string.image_is_potentially_hazardous
			)
		} else {
			view.textDistanceFromEarthAu.setTextColor(
				ContextCompat.getColor(view.context, R.color.primaryTextColor)
			)
			view.textAsteroidSpeed.setTextColor(
				ContextCompat.getColor(view.context, R.color.primaryTextColor)
			)
			view.imageIsPotentiallyHazardous.setImageResource(R.drawable.ic_emoji_friendly)
			view.imageIsPotentiallyHazardous.contentDescription = view.context.getString(
				R.string.image_not_potentially_hazardous
			)
		}
	}
}