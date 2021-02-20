package com.udacity.asteroidradar.features.mainscreen.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.core.extensions.inflate
import com.udacity.asteroidradar.core.extensions.showListItemWithFadeIn
import com.udacity.asteroidradar.features.mainscreen.domain.entities.AsteroidsFeedItem

/**
 * Created by jesselima on 17/01/21.
 * This is a part of the project Asteroid Radar.
 */
class AsteroidsAdapter(
	private var asteroids: List<AsteroidsFeedItem> = emptyList()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

	fun submitList(asteroidsData: List<AsteroidsFeedItem>) {
		asteroids = emptyList()
		asteroids = asteroidsData
		notifyDataSetChanged()
	}

	override fun onCreateViewHolder(rootViewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

		return rootViewGroup.inflate(R.layout.item_list_asteroids).run {
			showListItemWithFadeIn()
			AsteroidsViewHolder(this)
		}
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		val asteroid = asteroids[position]
		val viewHolder = holder as AsteroidsViewHolder
		viewHolder.bindDataToView(asteroid)
	}

	override fun getItemCount() = asteroids.size

}