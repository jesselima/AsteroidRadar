package com.udacity.asteroidradar.features.main.presentation.adapter

import android.view.View
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
	private var asteroidsFeedItem: AsteroidsFeedItem? = null

	init {
		itemView.setOnClickListener(this)
	}

	override fun onClick(v: View?) {
		val bundle = bundleOf(ASTEROID_ID to asteroidsFeedItem?.id)
		view.findNavController().navigate(R.id.navigateToAsteroidDetails, bundle)
	}

	fun bindDataToView(asteroid: AsteroidsFeedItem) {
		this.asteroidsFeedItem = asteroid
		view.asteroidName.text = asteroidsFeedItem?.codename

		view.textAsteroidSpeed.text = view.context.getString(
			R.string.units_format_kilometers,
			String.format("%.2f", asteroidsFeedItem?.relativeVelocityKilometersPerHour)
		)

		view.textDistanceFromEarthAu.text = view.context.getString(
			R.string.units_format_astronomical,
			String.format("%.2f", asteroidsFeedItem?.distanceFromEarthAu)
		)

		if (asteroid.isPotentiallyHazardous) {
			view.imageIsPotentiallyHazardous.setImageResource(R.drawable.ic_emoji_angry)
		} else {
			view.imageIsPotentiallyHazardous.setImageResource(R.drawable.ic_emoji_friendly)
		}
	}
}