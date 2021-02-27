package com.udacity.asteroidradar.features.picturefullscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.core.extensions.hideWithFadeOut
import com.udacity.asteroidradar.core.extensions.inflateFragment
import com.udacity.asteroidradar.core.extensions.showWithFadeIn
import com.udacity.asteroidradar.core.extensions.whenNotNull
import kotlinx.android.synthetic.main.fragment_picture_of_the_day_full_screen.*

/**
 * Created by jesselima on 13/02/21.
 * This is a part of the project Asteroid Radar.
 */

private const val ARG_HIGH_DEFINITION_IMAGE_URL = "highDefinitionImageUrl"
private const val ARG_PICTURE_OF_THE_DAY_TITLE = "pictureOfTheDayTitle"
private const val EMPTY = ""

class PictureOfTheDayFullScreenFragment : Fragment() {

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return inflateFragment(
			inflater = inflater,
			container = container,
			layoutResId = R.layout.fragment_picture_of_the_day_full_screen,
			shouldLoadWIthAnimation = true
		)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		setupListeners()
		showViews()

		val highDefinitionImageUrl = arguments?.getString(ARG_HIGH_DEFINITION_IMAGE_URL)
		val pictureOfTheDayTitle = arguments?.getString(ARG_PICTURE_OF_THE_DAY_TITLE)

		fullScreenPictureOfTheDayName.text = pictureOfTheDayTitle ?: EMPTY
		fullScreenPictureImageView.contentDescription = activity?.getString(
			R.string.message_picture_of,
			pictureOfTheDayTitle ?: EMPTY
		)

		highDefinitionImageUrl.whenNotNull {
			Picasso.get()
				.load(this)
				.placeholder(R.drawable.backdrop_image_overlay_darker_bottom)
				.error(R.drawable.ic_astronaut_image_not_found)
				.into(fullScreenPictureImageView, object : Callback {
					override fun onSuccess() {
						hideViews()
					}
					override fun onError(e: Exception?) {
						hideViews()
					}
				})
		}
	}

	private fun hideViews() {
		progressAnimatedFullScreenImage.hideWithFadeOut(fastAnimation = true)
		fullScreenPictureOfTheDayTextBalonMessage.hideWithFadeOut(fastAnimation = true)
		fullScreenPictureOfTheDayTextLoadingImageOf.hideWithFadeOut(fastAnimation = true)
	}

	private fun showViews() {
		progressAnimatedFullScreenImage.showWithFadeIn()
		fullScreenPictureOfTheDayTextBalonMessage.showWithFadeIn()
		fullScreenPictureOfTheDayTextLoadingImageOf.showWithFadeIn()
	}

	private fun setupListeners() {
		pictureOfTheDayIconExitFullScreen.setOnClickListener {
			activity?.onBackPressed()
		}
	}
}