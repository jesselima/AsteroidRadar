package com.udacity.asteroidradar.features.mainscreen.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.core.extensions.ToastType
import com.udacity.asteroidradar.core.extensions.getPagTransformer
import com.udacity.asteroidradar.core.extensions.hideWithFadeOut
import com.udacity.asteroidradar.core.extensions.showAppToast
import com.udacity.asteroidradar.core.extensions.showWithFadeIn
import com.udacity.asteroidradar.core.extensions.showWithLongFadeIn
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.features.mainscreen.domain.entities.AsteroidsFeedItem
import com.udacity.asteroidradar.features.mainscreen.domain.entities.PictureOfDay
import com.udacity.asteroidradar.features.mainscreen.presentation.adapter.AsteroidsAdapter
import com.udacity.asteroidradar.features.mainscreen.presentation.picturesviewpager.PictureOfTheDayPagerAdapter
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private val viewModel by viewModel<MainViewModel>()

    private val asteroidsAdapter: AsteroidsAdapter by lazy {
        AsteroidsAdapter()
    }

    private lateinit var picturesViewPagerAdapter: PictureOfTheDayPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupListeners()
        setupAsteroidsAdapter()
        setupPictureOfTheDayPagerAdapter()
    }

    private fun setupAsteroidsAdapter() {
        asteroidRecyclerView.adapter = asteroidsAdapter
    }
    private fun setupPictureOfTheDayPagerAdapter() {
        picturesViewPagerAdapter = PictureOfTheDayPagerAdapter(fragmentActivity = requireActivity())
        pictureOfTheDayViewPager.adapter = picturesViewPagerAdapter
        pictureOfTheDayViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        pictureOfTheDayViewPager.setPageTransformer(getPagTransformer())
        TabLayoutMediator(tabLayout, pictureOfTheDayViewPager) { _, _ -> }.attach()
    }

    private fun setupListeners() {
        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_bar_favorites_gallery -> {
                    showAppToast("To be implemented ", ToastType.WARNING) // TODO - To be implemented
                    true
                }
                R.id.bottom_bar_sort_by_distance -> {
                    viewModel.sortAsteroidsByDistance()
                    true
                }
                R.id.bottom_bar_sort_by_speed -> {
                    viewModel.sortAsteroidsBySpeed()
                    true
                }
                R.id.bottom_bar_sort_by_date -> {
                    viewModel.sortAsteroidsByDate()
                    true
                }
                R.id.bottom_bar_show_only_favorites_pictures -> {
                    viewModel.getAllLocalFavoritesPicturesOfTheDay()
                    true
                }
                R.id.bottom_bar_show_all_pictures -> {
                    viewModel.getLocalPictureOfTheLastSevenDays()
                    true
                }
                else -> false
            }
        }
        buttonGoIntoSpaceAndTryAgain.setOnClickListener {
            viewModel.requestRemoteAsteroidsData()
        }
    }

    private fun setupObservers() {
        viewModel.asteroidsState.observe(viewLifecycleOwner, { state ->
            state.renderViewState()
        })
        viewModel.picturesState.observe(viewLifecycleOwner, { state ->
            state?.renderViewState()
        })
    }

    private fun PicturesState.renderViewState() {
            if(isLoadingPictures) {
                mainTextLoadingImages.isVisible = true
                mainTextLoadingImages.text = getString(R.string.loading_pictures_of_10_last_days)
            }
            else if (isLoadingPictures.not() && picturesResult.isEmpty()) {
                mainTextLoadingImages.showWithLongFadeIn()
                mainTextLoadingImages.text = getString(R.string.message_no_pictures_found)
            } else {
                mainTextLoadingImages.hideWithFadeOut()
                mainTextLoadingImages.isVisible = picturesResult.isEmpty()
                handlePicturesSuccess(picturesResult)
            }
    }

    private fun AsteroidsState.renderViewState() {
        if(isLoadingAsteroids) {
            mainAnimateLoadingAsteroids.isVisible = true
            mainAnimateNoAsteroidsFound.isVisible = false
            buttonGoIntoSpaceAndTryAgain.isVisible = false
            mainAnimateLoadingAsteroids.showWithFadeIn()
            mainTextBalonMessage.showWithLongFadeIn()
            mainTextBalonMessage.text = getString(R.string.message_searching_asteroids_for_you)
        }
        if (asteroidsResult.isEmpty() && isLoadingAsteroids.not()) {
            mainAnimateLoadingAsteroids.isVisible = false
            mainAnimateLoadingAsteroids.hideWithFadeOut()
            mainTextBalonMessage.hideWithFadeOut()
            mainTextBalonMessage.showWithLongFadeIn()
            mainTextBalonMessage.text = getString(R.string.message_no_asteroids_found)
            mainAnimateNoAsteroidsFound.isVisible = true
            mainAnimateNoAsteroidsFound.showWithLongFadeIn()
            buttonGoIntoSpaceAndTryAgain.isVisible = true
            buttonGoIntoSpaceAndTryAgain.showWithLongFadeIn()
        } else if (asteroidsResult.isNotEmpty()) {
            mainAnimateLoadingAsteroids.isVisible = false
            mainAnimateNoAsteroidsFound.isVisible = false
            buttonGoIntoSpaceAndTryAgain.isVisible = false
            mainTextBalonMessage.isVisible = false
            handleAsteroidsSuccess(asteroidsResult)
        }
    }

    private fun handlePicturesSuccess(picturesResult: List<PictureOfDay>) {
        picturesViewPagerAdapter.submitList(picturesResult)
    }

    private fun handleAsteroidsSuccess(asteroidsResult: List<AsteroidsFeedItem>) {
        asteroidsAdapter.submitList(asteroidsData = asteroidsResult)
    }
}
