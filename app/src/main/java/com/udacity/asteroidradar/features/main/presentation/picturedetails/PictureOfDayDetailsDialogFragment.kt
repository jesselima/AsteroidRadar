package com.udacity.asteroidradar.features.main.presentation.picturedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.google.android.youtube.player.YouTubeStandalonePlayer
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.BuildConfig
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
import com.udacity.asteroidradar.features.main.presentation.MediaType
import kotlinx.android.synthetic.main.fragment_picure_of_the_day_details.*
import kotlinx.android.synthetic.main.fragment_picure_of_the_day_details.view.*
import java.util.*

/**
 * Created by jesselima on 25/01/21.
 * This is a part of the project Asteroid Radar.
 */
class PictureOfDayDetailsDialogFragment : DialogFragment() {

	private var pictureOfTheDay: PictureOfDay? = null
	private var mediaType: String = MediaType.IMAGE.type

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
			layoutResId = R.layout.fragment_picure_of_the_day_details,
			shouldLoadWIthAnimation = true
		)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		setupListeners()

		requireDialog().window?.setWindowAnimations(R.style.DialogAnimation)

		pictureOfTheDay = arguments?.getParcelable(ARG_PICTURE_OF_THE_DAY_DATA)
		mediaType = pictureOfTheDay?.mediaType ?: MediaType.IMAGE.type

		val url = when(mediaType) {
			MediaType.IMAGE.type -> {
				pictureOfTheDayDetailsToggleHighDefinition.setImageResource(R.drawable.ic_high_definition_is_off)
				pictureOfTheDay?.imageUrl
			}
			else -> {
				pictureOfTheDayDetailsToggleHighDefinition.setImageResource(R.drawable.ic_play_circle)
				val videoID = pictureOfTheDay?.imageUrl?.substringAfterLast("/embed/")?.substringBefore("?")
				"https://img.youtube.com/vi/$videoID/hqdefault.jpg"
			}
		}


		pictureOfTheDay?.let {

			it.imageUrl.let {
				progressBarHighDefinitionImage.isVisible = true
				Picasso.get()
					.load(url)
					.placeholder(R.drawable.backdrop_image_overlay_darker_bottom)
					.error(R.drawable.ic_astronaut_image_not_found)
					.into(view.pictureOfTheDayDetailsImageView, object : Callback {
						override fun onSuccess() {
							progressBarHighDefinitionImage.isVisible = false
							pictureOfTheDayDetailsTextBalonImageNotLoaded.isVisible = false
						}

						override fun onError(e: Exception?) {
							progressBarHighDefinitionImage.isVisible = false
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
			pictureOfTheDayDetailsToggleHighDefinition.setIconColorState(R.color.colorStateOff)
			dismiss()
		}

		pictureOfTheDayDetailsToggleHighDefinition.setOnClickListener {

			when(mediaType) {
				MediaType.IMAGE.type -> {
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
									pictureOfTheDayDetailsToggleHighDefinition.setIconColorState(R.color.colorStateOn)
								}

								override fun onError(e: Exception?) {
									showAppToast(getString(R.string.hd_image_not_loaded), ToastType.ERROR)
									val isConnected = context?.isConnected() ?: false
									if (isConnected.not()) {
										context?.let {
											showAppToast(getString(R.string.message_check_connection), ToastType.WARNING)
										}
									}
									view?.pictureOfTheDayDetailsTextBalonImageNotLoaded?.isVisible = true
									progressBarHighDefinitionImage.isVisible = false
								}
							})
					}
				}
				MediaType.VIDEO.type -> {
					val videoID = pictureOfTheDay?.imageUrl?.substringAfterLast("/embed/")?.substringBefore("?")
					videoID?.let {
						playThisVideo(videoId = it)
					}
				}
			}
		}
	}

	private fun playThisVideo(videoId: String) {
		val intent = YouTubeStandalonePlayer.createVideosIntent(
			requireActivity(),			/* Activity/Application  */
			BuildConfig.YOUTUBE_KEY,	/* API KEY */
			listOf(videoId),			/* List of videoIds */
			0,							/* startIndex */
			1,							/* timeMillis */
			true,						/* autoplay */
			true						/* light box Mode */
		)
		context?.startActivity(intent)
	}

	companion object {
		private const val ARG_PICTURE_OF_THE_DAY_DATA = "picture_of_da_day"
		@JvmStatic
		fun newInstance(pictureOfDay: PictureOfDay?): PictureOfDayDetailsDialogFragment {
			return PictureOfDayDetailsDialogFragment().apply {
				arguments = Bundle().apply {
					putParcelable(ARG_PICTURE_OF_THE_DAY_DATA, pictureOfDay)
				}
			}
		}
	}
}