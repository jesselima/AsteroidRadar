package com.udacity.asteroidradar.features.main.presentation.picturesviewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.core.extensions.formatDate
import com.udacity.asteroidradar.core.extensions.inflateFragment
import com.udacity.asteroidradar.core.extensions.showWithFadeIn
import com.udacity.asteroidradar.core.extensions.showWithLongFadeIn
import com.udacity.asteroidradar.features.main.domain.entities.PictureOfDay
import com.udacity.asteroidradar.features.main.presentation.MediaType
import com.udacity.asteroidradar.features.main.presentation.picturedetails.PictureOfDayDetailsDialogFragment
import kotlinx.android.synthetic.main.fragment_picture_of_the_day_pager_layout.*
import java.lang.Exception

private const val YOUTUBE_VIDEO_THUMBNAIL_BASE_URL = "https://img.youtube.com/vi/"
private const val YOUTUBE_VIDEO_THUMBNAIL_PREFIX_AND_FORMAT = "/hqdefault.jpg"
private const val NASA_VIDEO_URL_PATH_EMBED = "/embed/"
private const val URL_QUERY_DIVIDER = "?"

class PictureOfTheDayViewPagerFragment : Fragment() {

    private var pictureOfTheDay: PictureOfDay? = PictureOfDay()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return inflateFragment(
            inflater = inflater,
            container = container,
            layoutResId = R.layout.fragment_picture_of_the_day_pager_layout,
            shouldLoadWIthAnimation = true
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()

        loadingPagerImageItemProgressBar.isVisible = true

        pictureOfTheDay = arguments?.getParcelable(ARG_PICTURE_OF_THE_DAY_DATA)

        if (pictureOfTheDay == null) {
            loadingPagerImageItemProgressBar.isVisible = false
            mainViewPagerTextBalonImageNotLoaded.isVisible = false
        } else {

            val url = when(pictureOfTheDay?.mediaType ?: MediaType.IMAGE) {
                MediaType.IMAGE.type -> pictureOfTheDay?.imageUrl
                else -> {
                    getVideoThumbnailUrl(pictureOfTheDay?.imageUrl)
                }
            }

            pictureOfTheDay?.let {
                it.imageUrl.let {
                    loadingPagerImageItemProgressBar.isVisible = true
                    Picasso.get()
                        .load(url)
                        .placeholder(R.drawable.backdrop_image_overlay_darker_bottom)
                        .error(R.drawable.ic_astronaut_image_not_found)
                        .into(mainViewPagerCollapsingToolbarImageView, object : Callback {
                            override fun onSuccess() {
                                loadingPagerImageItemProgressBar.isVisible = false
                                mainViewPagerTextBalonImageNotLoaded.isVisible = false
                            }
                            override fun onError(e: Exception?) {
                                loadingPagerImageItemProgressBar.isVisible = false
                                mainViewPagerTextBalonImageNotLoaded.isVisible = true
                            }
                        })
                }

                it.date.let { date ->
                    with(mainViewPagerLabelWithDate) {
                        showWithFadeIn()
                        text = getString(R.string.label_picture_of_the_day_format, formatDate(date))
                    }
                }

                it.title.let { title ->
                    with(mainViewPagerTitle) {
                        showWithLongFadeIn()
                        text = title
                    }
                    with(mainViewPagerCollapsingToolbarImageView) {
                        showWithLongFadeIn()
                        contentDescription = title
                    }
                }
            }
        }
    }

    private fun getVideoThumbnailUrl(imageUrl: String?): String {
        val videoID = imageUrl?.substringAfterLast(NASA_VIDEO_URL_PATH_EMBED)?.substringBefore(URL_QUERY_DIVIDER)
        return "$YOUTUBE_VIDEO_THUMBNAIL_BASE_URL$videoID$YOUTUBE_VIDEO_THUMBNAIL_PREFIX_AND_FORMAT"
    }

    private fun setupListeners() {
        mainViewPagerToggleFullScreenView.setOnClickListener {
            openPictureDetails()
        }
        mainViewPagerCollapsingToolbarImageView.setOnClickListener {
            openPictureDetails()
        }
    }

    private fun openPictureDetails() {
        PictureOfDayDetailsDialogFragment.newInstance(pictureOfDay = pictureOfTheDay).show(
            childFragmentManager, PictureOfDayDetailsDialogFragment::class.java.simpleName
        )
    }

    companion object {
        /**
         * The fragment argument representing the a PictureOfTheDay for this
         * fragment.
         */
        private const val ARG_PICTURE_OF_THE_DAY_DATA = "picture_of_the_day"
        /**
         * Returns a new instance of this fragment for the given page in the ViewPager.
         */
        @JvmStatic
        fun newInstance(pictureOfDay: PictureOfDay): PictureOfTheDayViewPagerFragment {
            return PictureOfTheDayViewPagerFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PICTURE_OF_THE_DAY_DATA, pictureOfDay)
                }
            }
        }
    }
}