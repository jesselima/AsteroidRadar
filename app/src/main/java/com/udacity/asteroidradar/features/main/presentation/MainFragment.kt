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
import com.udacity.asteroidradar.core.extensions.isConnected
import com.udacity.asteroidradar.core.extensions.showDialog
import com.udacity.asteroidradar.core.extensions.showDialogWithActions
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.features.main.presentation.adapter.AsteroidsAdapter
import com.udacity.asteroidradar.features.main.presentation.adapter.PictureOfTheDayPagerAdapter
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
        validateOnLaunchShouldRequestRemoteData()
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

    private fun validateOnLaunchShouldRequestRemoteData() {
        val isConnected = context?.isConnected() ?: false
        viewModel.validateShouldRequestRemoteData(isConnected = isConnected)
    }

    private fun setupObservers() {
        viewModel.resultsState.observe(viewLifecycleOwner, { state ->
            when(state) {
                is MainState.LoadingAsteroids -> handleLoadingAsteroids(state)
                is MainState.ResultAsteroidsSuccess -> handleAsteroidsSuccess(state)
                is MainState.LoadingPictures -> handleLoadingPictures(state)
                is MainState.ResultPicturesOfTheDay -> handlePicturesSuccess(state)
                is MainState.ShowError -> handleError(state)
                is MainState.ResultPicturesOfTheDayByDate -> handlePictureOfTheDayByDate(state)
            }
        })
    }

    private fun handlePicturesSuccess(state: MainState.ResultPicturesOfTheDay) {
        if (state.picturesResult.isEmpty()) {
            mainTextLoadingImages.isVisible = true
            mainTextLoadingImages.text = context?.getString(R.string.message_no_pictures_found)
        }
        picturesViewPagerAdapter.submitList(state.picturesResult)
    }

    private fun handleAsteroidsSuccess(state: MainState.ResultAsteroidsSuccess) {
        if (state.asteroidsResult.isEmpty()) {
            mainAnimateLoadingAsteroids.isVisible = false
            mainAnimateNoAsteroidsFound.isVisible = true
            mainTextBalonMessage.isVisible = true
            mainTextBalonMessage.text = context?.getString(R.string.message_no_asteroids_found)
        } else {
            asteroidsAdapter.submitList(asteroidsData = state.asteroidsResult)
        }
    }

    private fun handleLoadingPictures(state: MainState.LoadingPictures) {
        mainTextLoadingImages.isVisible = state.isLoadingPictures
    }

    private fun handleLoadingAsteroids(state: MainState.LoadingAsteroids) {
        mainAppBarLayout.isVisible = state.isLoadingAsteroids.not()
        mainAnimateLoadingAsteroids.isVisible = state.isLoadingAsteroids
        mainTextBalonMessage.isVisible = state.isLoadingAsteroids
        mainTextBalonMessage.text = context?.getString(R.string.message_searching_asteroids_for_you)
    }

    private fun handlePictureOfTheDayByDate(state: MainState.ResultPicturesOfTheDayByDate) {
        picturesViewPagerAdapter.addToList(state.pictureByDate)
    }

    private fun handleError(state: MainState.ShowError) {
        mainAnimateNoAsteroidsFound.isVisible = true
        state.action?.invoke()
        context?.let {
            showDialog(
                context= it,
                title = it.getString(R.string.message_oops),
                message = it.getString(R.string.message_something_went_wrong),
                positiveButtonAction = { Timber.d("-->> Error button clicked!") }
            )
        }
    }

    private fun setupPictureOfTheDayPagerAdapter() {
        pictureOfTheDayViewPager.adapter = picturesViewPagerAdapter
        pictureOfTheDayViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        pictureOfTheDayViewPager.setPageTransformer(getPagTransformer())
        TabLayoutMediator(tabLayout, pictureOfTheDayViewPager) { _, _ ->
            /* Use "tab" and "position" to set tabs texts */
        }.attach()
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
