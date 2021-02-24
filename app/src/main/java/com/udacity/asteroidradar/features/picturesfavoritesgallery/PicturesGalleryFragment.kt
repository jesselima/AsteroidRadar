package com.udacity.asteroidradar.features.picturesfavoritesgallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.core.extensions.ToastType
import com.udacity.asteroidradar.core.extensions.inflateFragment
import com.udacity.asteroidradar.core.extensions.showAppToast
import com.udacity.asteroidradar.core.extensions.whenNotNull
import kotlinx.android.synthetic.main.fragment_picture_gallery.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by jesselima on 21/02/21.
 * This is a part of the project Asteroid Radar.
 */

private const val RECYCLER_VIEW_GRID_LAYOUT_SPAN_COUNT = 1

class PicturesGalleryFragment : Fragment() {

	private val viewModel by viewModel<PicturesGalleryViewModel>()

	private val picturesGalleryAdapter: PicturesGalleryAdapter by lazy {
		PicturesGalleryAdapter()
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return inflateFragment(
			inflater = inflater,
			container = container,
			layoutResId = R.layout.fragment_picture_gallery,
			shouldLoadWIthAnimation = true
		)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		picturesGalleryRecyclerView.layoutManager = GridLayoutManager(
			context, RECYCLER_VIEW_GRID_LAYOUT_SPAN_COUNT
		)

		picturesGalleryRecyclerView.adapter = picturesGalleryAdapter
		setupObservers()

		favoritePicturesTopAppBar.setOnClickListener {
			activity?.onBackPressed()
		}
	}

	private fun setupObservers() {
		viewModel.picturesOfDay.observe(viewLifecycleOwner, { favoritePictures ->
			favoritePictures.whenNotNull {
				picturesGalleryAdapter.submitList(this)
				animatedNoFavoritesPicturesFound.isVisible = favoritePictures?.isEmpty() == true
				animatedNoFavoritesTextBalonMessage.isVisible = favoritePictures?.isEmpty() == true
				noFavoritesFoundEmptySpaceBackground.isVisible = favoritePictures?.isEmpty() == true
			}
		})
	}

}