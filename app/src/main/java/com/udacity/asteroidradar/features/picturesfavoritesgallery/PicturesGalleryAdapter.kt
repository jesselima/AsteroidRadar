package com.udacity.asteroidradar.features.picturesfavoritesgallery

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.core.extensions.inflate
import com.udacity.asteroidradar.core.extensions.showListItemWithFadeIn
import com.udacity.asteroidradar.features.mainscreen.domain.entities.PictureOfDay

/**
 * Created by jesselima on 21/02/21.
 * This is a part of the project Asteroid Radar.
 */
class PicturesGalleryAdapter(
	private var favoritesPictures: List<PictureOfDay> = emptyList()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

	fun submitList(favoritesPicturesData: List<PictureOfDay>) {
		favoritesPictures = emptyList()
		favoritesPictures = favoritesPicturesData
		notifyDataSetChanged()
	}

	override fun onCreateViewHolder(rootViewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		return rootViewGroup.inflate(R.layout.layout_item_list_picture_gallery).run {
			showListItemWithFadeIn()
			PicturesGalleryViewHolder(this)
		}
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		val pictureOfDay = favoritesPictures[position]
		val viewHolder = holder as PicturesGalleryViewHolder
		viewHolder.bindDataToView(pictureOfDay)
	}

	override fun getItemCount() = favoritesPictures.size
}