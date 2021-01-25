package com.udacity.asteroidradar.features.main.presentation.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.core.extensions.formatDate
import com.udacity.asteroidradar.core.extensions.inflateFragment
import com.udacity.asteroidradar.core.extensions.showWithFadeIn
import com.udacity.asteroidradar.core.extensions.showWithLongFadeIn
import com.udacity.asteroidradar.features.main.domain.entities.PictureOfDay
import kotlinx.android.synthetic.main.fragment_picture_of_the_day_pager_layout.*
import timber.log.Timber
import java.lang.Exception

class PictureOfTheDayViewPagerFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return inflateFragment(
            inflater = inflater,
            container = container,
            layoutResId = R.layout.fragment_picture_of_the_day_pager_layout
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pictureOfTheDay = arguments?.getParcelable<PictureOfDay>(ARG_PICTURE_OF_THE_DAY_DATA)

        pictureOfTheDay?.let {

            it.imageUrl.let { url ->
                Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.backdrop_image_overlay_darker_bottom)
                    .error(R.drawable.ic_astronaut_image_not_found)
                    .into(mainViewPagerCollapsingToolbarImageView, object : Callback {
                        override fun onSuccess() {
                            mainViewPagerTextBalonImageNotLoaded.isVisible = false
                            Timber.d("Image loaded successfully!")
                        }

                        override fun onError(e: Exception?) {
                            mainViewPagerTextBalonImageNotLoaded.isVisible = true
                        }

                    })
            }

            it.date.let { date ->
                with(mainViewPagerLabelWithDate) {
                    showWithFadeIn()
                    text = getString(R.string.picture_of_the_day_format, formatDate(date))
                }
            }

            it.title.let { title ->
                with(mainViewPagerTitle) {
                    showWithLongFadeIn()
                    text = title
                }
            }
        }
    }

    companion object {
        /**
         * The fragment argument representing the a PictureOfTheDay for this
         * fragment.
         */
        private const val ARG_PICTURE_OF_THE_DAY_DATA = "picture_of_da_day"
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