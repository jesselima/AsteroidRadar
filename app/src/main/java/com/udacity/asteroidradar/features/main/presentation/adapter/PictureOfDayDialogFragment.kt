package com.udacity.asteroidradar.features.main.presentation.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.core.extensions.ToastType
import com.udacity.asteroidradar.core.extensions.formatDate
import com.udacity.asteroidradar.core.extensions.inflateFragment
import com.udacity.asteroidradar.core.extensions.isConnected
import com.udacity.asteroidradar.core.extensions.setIconColorState
import com.udacity.asteroidradar.core.extensions.showAppToast
import com.udacity.asteroidradar.core.extensions.showWithFadeIn
import com.udacity.asteroidradar.core.extensions.showWithLongFadeIn
import com.udacity.asteroidradar.features.main.domain.entities.PictureOfDay
import kotlinx.android.synthetic.main.fragment_picure_of_the_day.*
import kotlinx.android.synthetic.main.fragment_picure_of_the_day.view.*
import timber.log.Timber

/**
 * Created by jesselima on 25/01/21.
 * This is a part of the project Asteroid Radar.
 */
class PictureOfDayDialogFragment : DialogFragment() {

	private var pictureOfTheDay: PictureOfDay? = null

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
			layoutResId = R.layout.fragment_picure_of_the_day,
			shouldLoadWIthAnimation = true
		)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		setupListeners()

		requireDialog().window?.setWindowAnimations(R.style.DialogAnimation)

		pictureOfTheDay = arguments?.getParcelable(ARG_PICTURE_OF_THE_DAY_DATA)

		pictureOfTheDay?.let {

			it.imageUrl.let { url ->
				Picasso.get()
					.load(url)
					.placeholder(R.drawable.backdrop_image_overlay_darker_bottom)
					.error(R.drawable.ic_astronaut_image_not_found)
					.into(view.pictureOfTheDayDetailsImageView, object : Callback {
						override fun onSuccess() {
							pictureOfTheDayDetailsTextBalonImageNotLoaded.isVisible = false
							Timber.d("Image loaded successfully!")
						}

						override fun onError(e: Exception?) {
							view.pictureOfTheDayDetailsTextBalonImageNotLoaded.isVisible = true
						}

					})
			}

			it.date.let { date ->
				with(view.pictureOfTheDayDetailsLabelWithDate) {
					showWithFadeIn()
					text = getString(R.string.label_picture_of_the_day_format_details, formatDate(date))
				}
			}

			it.title.let { title ->
				with(view.pictureOfTheDayDetailsTitle) {
					showWithFadeIn()
					text = title
				}
			}

			it.explanation.let { explanation ->
				with(view.pictureOfTheDayDetailsExplanation) {
					showWithLongFadeIn()
					text = explanation
				}
			}

			pictureOfTheDayDetailsLabelCopyright.isVisible = it.copyright != null

			it.copyright?.let { title ->
				with(view.pictureOfTheDayDetailsCopyrightAuthor) {
					showWithLongFadeIn()
					text = title
				}
			}
		}
	}

	private fun setupListeners() {
		pictureOfTheDayDetailsCloseFullScreenView.setOnClickListener {
			pictureOfTheDayDetailsToggleHighDefinition.setIconColorState(R.color.color_state_off)
			dismiss()
		}

		pictureOfTheDayDetailsToggleHighDefinition.setOnClickListener {

			pictureOfTheDay?.highDefinitionImageUrl.let { url ->
				progressBarHighDefinitionImage.isVisible = true
				Picasso.get()
					.load(url)
					.placeholder(R.drawable.backdrop_image_overlay_darker_bottom)
					.error(R.drawable.ic_astronaut_image_not_found)
					.into(view?.pictureOfTheDayDetailsImageView, object : Callback {
						override fun onSuccess() {
							showAppToast(getString(R.string.hd_image_loaded), ToastType.SUCCESS)
							progressBarHighDefinitionImage.isVisible = false
							pictureOfTheDayDetailsTextBalonImageNotLoaded.isVisible = false
							pictureOfTheDayDetailsToggleHighDefinition.setIconColorState(R.color.color_state_on)
						}

						override fun onError(e: Exception?) {
							val isConnected = context?.isConnected() ?: false
							if(isConnected.not()) {
								context?.let {
									showAppToast(getString(R.string.check_connection_message), ToastType.WARNING)
								}
							}
							view?.pictureOfTheDayDetailsTextBalonImageNotLoaded?.isVisible = true
							progressBarHighDefinitionImage.isVisible = false
						}

					})
			}
		}
	}

	companion object {
		private const val ARG_PICTURE_OF_THE_DAY_DATA = "picture_of_da_day"
		@JvmStatic
		fun newInstance(pictureOfDay: PictureOfDay?): PictureOfDayDialogFragment {
			return PictureOfDayDialogFragment().apply {
				arguments = Bundle().apply {
					putParcelable(ARG_PICTURE_OF_THE_DAY_DATA, pictureOfDay)
				}
			}
		}
	}
}