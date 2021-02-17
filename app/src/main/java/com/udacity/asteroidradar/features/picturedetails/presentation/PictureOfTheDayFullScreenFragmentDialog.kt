package com.udacity.asteroidradar.features.picturedetails.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.core.extensions.hideWithFadeOut
import com.udacity.asteroidradar.core.extensions.inflateFragment
import com.udacity.asteroidradar.core.extensions.showWithFadeIn
import com.udacity.asteroidradar.core.extensions.whenNotNull
import kotlinx.android.synthetic.main.fragment_picture_of_the_day_full_screen.*
import java.lang.Exception

/**
 * Created by jesselima on 13/02/21.
 * This is a part of the project Asteroid Radar.
 */

private const val EMPTY = ""

class PictureOfTheDayFullScreenFragmentDialog : DialogFragment() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setStyle(STYLE_NORMAL, R.style.Theme_App_Dialog_FullScreen)
	}

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
		requireDialog().window?.setWindowAnimations(R.style.DialogAnimation)
		setupListeners()
		showViews()

		val pictureOfTheDayName= arguments?.getString(ARG_PICTURE_OF_THE_DAY_NAME) ?: EMPTY
		val pictureOfTheDayUrl = arguments?.getString(ARG_PICTURE_OF_THE_DAY_URL)

		fullScreenPictureOfTheDayName.text = pictureOfTheDayName
		fullScreenPictureImageView.contentDescription = activity?.getString(R.string.message_picture_of, pictureOfTheDayName)

		pictureOfTheDayUrl.whenNotNull {
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
			dismiss()
		}
	}

	companion object {
		private const val ARG_PICTURE_OF_THE_DAY_NAME = "picture_of_the_day_name"
		private const val ARG_PICTURE_OF_THE_DAY_URL = "picture_of_the_day_url"
		@JvmStatic
		fun newInstance(pictureOfDayName: String?, pictureOfDayUrl: String): PictureOfTheDayFullScreenFragmentDialog {
			return PictureOfTheDayFullScreenFragmentDialog().apply {
				arguments = Bundle().apply {
					putString(ARG_PICTURE_OF_THE_DAY_NAME, pictureOfDayName)
					putString(ARG_PICTURE_OF_THE_DAY_URL, pictureOfDayUrl)
				}
			}
		}
	}
}