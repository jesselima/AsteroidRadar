package com.udacity.asteroidradar.features.mainscreen.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.core.extensions.ToastType
import com.udacity.asteroidradar.core.extensions.getPageTransformer
import com.udacity.asteroidradar.core.extensions.hideWithFadeOut
import com.udacity.asteroidradar.core.extensions.showAppToast
import com.udacity.asteroidradar.core.extensions.showDialog
import com.udacity.asteroidradar.core.extensions.showWithFadeIn
import com.udacity.asteroidradar.core.extensions.showWithLongFadeIn
import com.udacity.asteroidradar.core.extensions.whenNotNull
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.features.mainscreen.domain.entities.AsteroidsFeedItem
import com.udacity.asteroidradar.features.mainscreen.domain.entities.PictureOfDay
import com.udacity.asteroidradar.features.mainscreen.presentation.adapter.AsteroidsAdapter
import com.udacity.asteroidradar.features.mainscreen.presentation.picturesviewpager.PictureOfTheDayPagerAdapter
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val NO_DELETED_RESULT = 0
private const val FIRST_TAB_POSITION = 0

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
        setupPictureOfTheDayPagerAdapter()
        setupObservers()
        setupListeners()
        setupAsteroidsAdapter()
    }

    private fun setupAsteroidsAdapter() {
        asteroidRecyclerView.adapter = asteroidsAdapter
    }

    private fun setupPictureOfTheDayPagerAdapter() {
        picturesViewPagerAdapter = PictureOfTheDayPagerAdapter(
            fragmentManager = childFragmentManager,
            lifecycle = viewLifecycleOwner.lifecycle
        )
        pictureOfTheDayViewPager.adapter = picturesViewPagerAdapter
        pictureOfTheDayViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        pictureOfTheDayViewPager.setPageTransformer(getPageTransformer())
        TabLayoutMediator(tabLayout, pictureOfTheDayViewPager){ _ , _ -> }.attach()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewModel.setCurrentPageAdapterPosition(tab?.position ?: FIRST_TAB_POSITION)
                val position = tab?.position ?: FIRST_TAB_POSITION
                tab?.contentDescription = picturesViewPagerAdapter.getCurrentPicture(position)?.title
            }
            override fun onTabReselected(tab: TabLayout.Tab?) { }
            override fun onTabUnselected(tab: TabLayout.Tab?) { }
        })
    }

    private fun setupListeners() {
        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_bar_favorites_gallery -> {
                    findNavController().navigate(R.id.navigateToPicturesGalleryFragment)
                    true
                }
                // Filter Actions
                R.id.app_bar_show_today_asteroids -> {
                    viewModel.getTodayAsteroids()
                    true
                }
                R.id.app_bar_show_week_asteroids -> {
                    viewModel.getLocalAsteroidsFeed()
                    true
                }
                R.id.app_bar_sort_by_distance -> {
                    viewModel.sortAsteroidsByDistance()
                    true
                }
                R.id.app_bar_sort_by_speed -> {
                    viewModel.sortAsteroidsBySpeed()
                    true
                }
                R.id.app_bar_sort_by_date -> {
                    viewModel.sortAsteroidsByDate()
                    true
                }
                // Sort Pictures
                R.id.app_bar_show_only_favorites_pictures -> {
                    viewModel.getAllLocalFavoritesPicturesOfTheDay()
                    true
                }
                R.id.app_bar_show_all_pictures -> {
                    viewModel.getLocalPictureOfTheLastSevenDays()
                    true
                }
                // Reset Actions
                R.id.app_bar_reset_favorites_pictures -> {
                    showDeleteWarning(R.string.message_warning_reset_favorites_pictures)
                    { viewModel.resetFavorites() }
                    true
                }
                // Delete Actions
                R.id.app_bar_delete_favorite_pictures -> {
                    showDeleteWarning(R.string.message_warning_delete_favorite_pictures)
                    { viewModel.deleteFavoritesOnly() }
                    true
                }
                R.id.app_bar_delete_not_favorites_pictures -> {
                    showDeleteWarning(R.string.message_warning_delete_not_favorite_pictures)
                    { viewModel.deleteNotFavoritesOnly() }
                    true
                }
                R.id.app_bar_delete_all_pictures -> {
                    showDeleteWarning(R.string.message_warning_delete_all_pictures)
                    { viewModel.deleteAllPictures() }
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
            state?.renderViewState()
        })
        viewModel.picturesState.observe(viewLifecycleOwner, { state ->
            state?.renderViewState()
        })
        viewModel.affectedDataItems.observe(viewLifecycleOwner, { deletedItems ->
            deletedItems.whenNotNull {
                if (this > NO_DELETED_RESULT) showAppToast(
                    resources.getQuantityString(
                        R.plurals.number_of_items_delete,
                        this,
                        this),
                    ToastType.INFO
                )
            }
        })
        viewModel.pictureOfTheDayViewPagerCurrentItem.observe(viewLifecycleOwner, {
            pictureOfTheDayViewPager.currentItem = it
        })
        viewModel.filterHasResultState.observe(viewLifecycleOwner, { filterHasResult ->
            if (filterHasResult.not()) {
                mainTextLoadingImages.isVisible = false
                context?.let {
                    showAppToast(
                        it.getString(R.string.message_no_favorite_pictures_found_try_to_add_some),
                        ToastType.INFO
                    )
                }
            }
        })
    }

    private fun PicturesState.renderViewState() {
        if(isLoadingPictures) {
            mainTextLoadingImages.isVisible = true
            mainTextLoadingImages.text = getString(R.string.loading_pictures)
        } else if (isLoadingPictures.not() && picturesResult.isEmpty()) {
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

    private fun showDeleteWarning(@StringRes stringResMessage: Int, action: () -> Unit) {
        context?.let {
            showDialog(
                context = it,
                title = getString(R.string.message_attention),
                message = getString(stringResMessage),
                positiveButtonAction = action
            )
        }
    }
}
