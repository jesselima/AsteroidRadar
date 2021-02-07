package com.udacity.asteroidradar.features.main.presentation

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
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.core.extensions.AppBarState
import com.udacity.asteroidradar.core.extensions.AppBarStateChangeListener
import com.udacity.asteroidradar.core.extensions.getPagTransformer
import com.udacity.asteroidradar.core.extensions.hideWithFadeOut
import com.udacity.asteroidradar.core.extensions.showDialog
import com.udacity.asteroidradar.core.extensions.showDialogWithActions
import com.udacity.asteroidradar.core.extensions.showWithFadeIn
import com.udacity.asteroidradar.core.extensions.showWithLongFadeIn
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.features.main.domain.entities.AsteroidsFeedItem
import com.udacity.asteroidradar.features.main.domain.entities.PictureOfDay
import com.udacity.asteroidradar.features.main.presentation.adapter.AsteroidsAdapter
import com.udacity.asteroidradar.features.main.presentation.picturesviewpager.PictureOfTheDayPagerAdapter
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

private const val EMPTY = ""

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
        picturesViewPagerAdapter = PictureOfTheDayPagerAdapter(fragmentActivity = requireActivity())
        setupPictureOfTheDayPagerAdapter()

        asteroidRecyclerView.adapter = asteroidsAdapter

        mainAppBarLayout.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout?, appBarState: AppBarState) {
                if(appBarState == AppBarState.COLLAPSED) {
                    mainCollapsingToolbarLayout.title = getString(R.string.label_asteroids)
                } else {
                    mainCollapsingToolbarLayout.title = EMPTY
                }
            }
        })
    }

    private fun setupObservers() {
        viewModel.asteroidsState.observe(viewLifecycleOwner, { state ->
            state.renderViewState()
        })
        viewModel.picturesState.observe(viewLifecycleOwner, { state ->
            state.renderViewState()
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
        } else if (asteroidsResult.isNotEmpty()) {
            mainAnimateLoadingAsteroids.isVisible = false
            mainAnimateNoAsteroidsFound.isVisible = false
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

    private fun handleError(message: String, action: (() -> Unit)? = null) {
        mainAnimateNoAsteroidsFound.isVisible = true
        action?.invoke()
        context?.let {
            showDialog(
                context= it,
                title = it.getString(R.string.message_oops),
                message = message,
                positiveButtonAction = { Timber.d("-->> Error button clicked!") }
            )
        }
    }

    private fun setupPictureOfTheDayPagerAdapter() {
        pictureOfTheDayViewPager.adapter = picturesViewPagerAdapter
        pictureOfTheDayViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        pictureOfTheDayViewPager.setPageTransformer(getPagTransformer())
        TabLayoutMediator(tabLayout, pictureOfTheDayViewPager) { _, _ -> }.attach()
    }

    private fun saveMetricsInfoPreferences(shouldShowAgain: Boolean) {
        viewModel.saveShouldShowMetricsInfoDialog(shouldShowAgain = shouldShowAgain)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

    private fun validateInfoDialogMetricsPreferences() {
        val shouldShowAgain = viewModel.getShouldShowMetricsInfoDialog()
        if (shouldShowAgain) {
            context?.let {
                showDialogWithActions(
                    context = it,
                    title = getString(R.string.tell_more_astronomical_units_title),
                    message = getString(R.string.tell_more_astronomical_units),
                    positiveButtonText = getString(R.string.remember_me_later),
                    positiveButtonAction = { saveMetricsInfoPreferences(shouldShowAgain = true) },
                    negativeButtonText = getString(R.string.label_ok_got_it),
                    negativeButtonAction =  { saveMetricsInfoPreferences(shouldShowAgain = false) },
                )
            }
        }
    }
}
