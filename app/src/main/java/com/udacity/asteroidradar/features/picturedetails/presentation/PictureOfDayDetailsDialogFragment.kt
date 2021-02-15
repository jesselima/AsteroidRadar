package com.udacity.asteroidradar.features.picturedetails.presentation

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
import com.udacity.asteroidradar.core.connectionchecker.ConnectionChecker
import com.udacity.asteroidradar.core.extensions.ToastType
import com.udacity.asteroidradar.core.extensions.formatDate
import com.udacity.asteroidradar.core.extensions.inflateFragment
import com.udacity.asteroidradar.core.extensions.isConnected
import com.udacity.asteroidradar.core.extensions.setIconColorState
import com.udacity.asteroidradar.core.extensions.showAppToast
import com.udacity.asteroidradar.core.extensions.showWithFadeIn
import com.udacity.asteroidradar.core.extensions.showWithLongFadeIn
import com.udacity.asteroidradar.core.extensions.whenNotNull
import com.udacity.asteroidradar.core.extensions.whenNull
import com.udacity.asteroidradar.features.mainscreen.domain.entities.PictureOfDay
import com.udacity.asteroidradar.features.mainscreen.presentation.MediaType
import kotlinx.android.synthetic.main.fragment_picure_of_the_day_details.*
import kotlinx.android.synthetic.main.fragment_picure_of_the_day_details.view.*
import org.koin.android.ext.android.inject

/**
 * Created by jesselima on 25/01/21.
 * This is a part of the project Asteroid Radar.
 */

private const val YOUTUBE_VIDEO_THUMBNAIL_BASE_URL = "https://img.youtube.com/vi/"
private const val YOUTUBE_VIDEO_THUMBNAIL_PREFIX_AND_FORMAT = "/hqdefault.jpg"
private const val NASA_VIDEO_URL_PATH_EMBED = "/embed/"
private const val URL_QUERY_DIVIDER = "?"
private const val EMPTY = ""

class PictureOfDayDetailsDialogFragment : DialogFragment() {

	private var pictureOfTheDay: PictureOfDay? = null
	private var mediaType: String = MediaType.IMAGE.type
	private var videoId: String? = ""

	private val connectionChecker: ConnectionChecker by inject()

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

		pictureDetailsCollapsingToolbar.apply {
			setExpandedTitleTextAppearance(R.style.ExpandedAppBar)
			setCollapsedTitleTextAppearance(R.style.CollapsedAppBar)
		}

		pictureOfTheDay = arguments?.getParcelable(ARG_PICTURE_OF_THE_DAY_DATA)
		mediaType = pictureOfTheDay?.mediaType ?: MediaType.IMAGE.type
		videoId = pictureOfTheDay?.imageUrl?.substringAfterLast(NASA_VIDEO_URL_PATH_EMBED)?.substringBefore(URL_QUERY_DIVIDER)
		pictureOfTheDayDetailsOpenFullScreenDialog.isVisible = mediaType == MediaType.IMAGE.type
		pictureOfTheDayDetailsOpenFullScreenDialog.isVisible = mediaType == MediaType.IMAGE.type
		pictureOfTheDayDetailsPlayVideo.isVisible = mediaType == MediaType.VIDEO.type

		val url = when (mediaType) {
			MediaType.IMAGE.type -> {
				pictureOfTheDay?.imageUrl
			}
			else -> {
				"$YOUTUBE_VIDEO_THUMBNAIL_BASE_URL$videoId$YOUTUBE_VIDEO_THUMBNAIL_PREFIX_AND_FORMAT"
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
		pictureOfTheDayDetailsOpenFullScreenDialog.setOnClickListener {
			pictureOfTheDay?.highDefinitionImageUrl?.let { highDefinitionImageUrl ->
				val pictureOfTheDayName = pictureOfTheDay?.title
				PictureOfTheDayFullScreenFragmentDialog.newInstance(pictureOfDayName = pictureOfTheDayName, pictureOfDayUrl = highDefinitionImageUrl).show(
					childFragmentManager, PictureOfDayDetailsDialogFragment::class.java.simpleName
				)
			}
		}

		pictureOfTheDayDetailsToggleFavorite.setOnClickListener {
			// TODO update the picture as favorite.
			showAppToast("To be implemented!", ToastType.INFO)
			pictureOfTheDayDetailsToggleFavorite.setIconColorState(R.color.colorStateOn)
		}

		backIconAsteroidDetailsTopAppBar.setNavigationOnClickListener {
			pictureOfTheDayDetailsToggleHighDefinition.setIconColorState(R.color.colorStateOff)
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

		pictureOfTheDayDetailsPlayVideo.setOnClickListener {
			if(connectionChecker.isConnected().not()) {
				context?.let {
					showAppToast(
						it.getString(R.string.message_check_connection_before_to_play_video),
						ToastType.ERROR
					)
				}
			} else {
				videoId.whenNotNull {
					playThisVideo(this)
				}.whenNull {
					context?.let {
						showAppToast(
							it.getString(R.string.message_not_valid_video_id_or_url),
							ToastType.ERROR
						)
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
		private const val ARG_PICTURE_OF_THE_DAY_DATA = "picture_of_the_day"
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