package com.udacity.asteroidradar.features.picturesfavoritesgallery

import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.features.mainscreen.domain.entities.PictureOfDay
import kotlinx.android.synthetic.main.layout_item_list_picture_gallery.view.*

/**
 * Created by jesselima on 21/02/21.
 * This is a part of the project Asteroid Radar.
 */

private const val ARG_HIGH_DEFINITION_IMAGE_URL = "highDefinitionImageUrl"
private const val ARG_PICTURE_OF_THE_DAY_TITLE = "pictureOfTheDayTitle"

class PicturesGalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

	private var view: View = itemView
	private var pictureOfDay: PictureOfDay? = null

	init {
		itemView.setOnClickListener(this)
	}

	override fun onClick(v: View?) {
		val pictureOfTheDayTitle: String? = pictureOfDay?.title
		val highDefinitionImageUrl: String? =  pictureOfDay?.highDefinitionImageUrl
		val bundle = bundleOf(
			ARG_PICTURE_OF_THE_DAY_TITLE to pictureOfTheDayTitle,
			ARG_HIGH_DEFINITION_IMAGE_URL to highDefinitionImageUrl
		)
		view.findNavController().navigate(R.id.pictureOfTheDayFullScreenFragment, bundle)
	}

	fun bindDataToView(pictureOfDay: PictureOfDay) {
		this.pictureOfDay = pictureOfDay
		view.galleryPictureOfDayTile.text = pictureOfDay.title
		view.galleryFavoritePictureImageView.contentDescription = pictureOfDay.title
		Picasso.get()
			.load(pictureOfDay.imageUrl)
			.placeholder(R.drawable.backdrop_image_overlay_darker_bottom)
			.error(R.drawable.ic_astronaut_image_not_found)
			.into(view.galleryFavoritePictureImageView, object : Callback {
				override fun onSuccess() {
					view.loadingImageGalleryItem.isVisible = false
				}
				override fun onError(e: Exception?) {
					view.loadingImageGalleryItem.isVisible = false
					view.galleryFavoritePictureImageView.contentDescription =
						view.context.getString(R.string.message_this_image_could_not_be_loaded)
				}
			})
	}
}