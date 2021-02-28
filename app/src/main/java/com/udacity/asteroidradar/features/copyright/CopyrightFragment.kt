package com.udacity.asteroidradar.features.copyright

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.core.extensions.inflateFragment
import kotlinx.android.synthetic.main.fragment_copyrights.*

/**
 * Created by jesselima on 28/02/21.
 * This is a part of the project Asteroid Radar.
 */

class CopyrightFragment : Fragment() {

	private val copyrightAdapter: CopyrightAdapter by lazy {
		CopyrightAdapter()
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return inflateFragment(
			inflater = inflater,
			container = container,
			layoutResId = R.layout.fragment_copyrights,
			shouldLoadWIthAnimation = true
		)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		copyrightTopAppBar.setOnClickListener {
			activity?.onBackPressed()
		}
		copyrightRecyclerView.adapter = copyrightAdapter
		val copyrights = getCopyrightList()
		copyrightAdapter.submitList(copyrights)
	}

	private fun getCopyrightList() : List<Copyright> {
		return listOf(
			Copyright(
				imageResId = R.drawable.ic_nasa_apis,
				sourceName = "Nasa APIs",
				authorName = "Open Innovation Team",
				link = "https://api.nasa.gov/"
			),
			Copyright(
				imageResId = R.raw.astronaut_loading,
				isAnimation = true,
				sourceName = "Lottie Files",
				authorName = "Samy Menai",
				link = "https://lottiefiles.com/703-navis-loader"
			),
			Copyright(
				imageResId = R.raw.astronaut_loading_in_space,
				isAnimation = true,
				sourceName = "Lottie Files",
				authorName = "Chris Poje",
				link = "https://lottiefiles.com/50236-astronaut-edited"
			),
			Copyright(
				imageResId = R.raw.astronaut_not_found,
				isAnimation = true,
				sourceName = "Lottie Files",
				authorName = "Test Account",
				link = "https://lottiefiles.com/28134-astronaut"
			),
			Copyright(
				imageResId = R.raw.angry_emoji,
				isAnimation = true,
				sourceName = "Lottie Files",
				authorName = "Neel Litoriya",
				link = "https://lottiefiles.com/28759-angry-emoji"
			),
			Copyright(
				imageResId = R.raw.smilie_emoji,
				isAnimation = true,
				sourceName = "Lottie Files",
				authorName = "Ronak Laungani",
				link = "https://lottiefiles.com/46054-animated-smilie-emoji"
			),
			Copyright(
				imageResId = R.drawable.ic_emoji_angry,
				sourceName = "Freepik",
				authorName = "freepik",
				link = "https://www.freepik.com/free-vector/flat-emoticon-reaction-collectio_4362938.htm"
			),
			Copyright(
				imageResId = R.drawable.ic_emoji_friendly,
				sourceName = "Freepik",
				authorName = "freepik",
				link = "https://www.freepik.com/free-vector/flat-emoticon-reaction-collectio_4362938.htm"
			),
			Copyright(
				imageResId = R.drawable.ic_text_ballon_left,
				sourceName = "Freepik",
				authorName = "pikisuperstar",
				link = "https://www.freepik.com/free-vector/short-messages-with-emojis-social-interactions_5481392.htm"
			),
			Copyright(
				imageResId = R.drawable.ic_globe,
				sourceName = "Freepik",
				authorName = "macrovector",
				link = "https://www.freepik.com/free-vector/globe-earth-world-icons-vector-white-black_10601425.htm"
			),
			Copyright(
				imageResId = R.drawable.ic_meteor,
				sourceName = "Freepik",
				authorName = "macrovector",
				link = "https://www.freepik.com/free-vector/vintage-meteors-collection-with-falling-comets-asteroids-meteorites-different-shapes-isolated_10056098.htm"
			),
			Copyright(
				imageResId = R.drawable.ic_astronaut_image_not_found_with_balon,
				sourceName = "Freepik",
				authorName = "catalyststuff",
				link = "https://www.freepik.com/free-vector/cute-astronaut-flying-with-planet-balloons-space-cartoon_11766657.htm"
			),
			Copyright(
				imageResId = R.drawable.ic_antenna,
				sourceName = "Freepik",
				authorName = "rawpixel-com",
				link = "https://www.freepik.com/free-vector/illustration-satellite-antenna_2632419.htm"
			),
			Copyright(
				imageResId = R.drawable.ic_asteroid_falling,
				sourceName = "Freepik",
				authorName = "brgfx",
				link = "https://www.freepik.com/free-vector/comet_1551510.htm"
			),
		)
	}
}