package com.udacity.asteroidradar.features.main.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.core.extensions.AppBarState
import com.udacity.asteroidradar.core.extensions.AppBarStateChangeListener
import com.udacity.asteroidradar.core.extensions.getPagTransformer
import com.udacity.asteroidradar.core.extensions.showDialogWithActions
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.features.main.presentation.adapter.AsteroidsAdapter
import com.udacity.asteroidradar.features.main.presentation.adapter.PictureOfTheDayPagerAdapter
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private val viewModel by viewModel<MainViewModel>()

    private val adapter: AsteroidsAdapter by lazy {
        AsteroidsAdapter()
    }

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
        validateMetricsInfoPreferences()
        asteroidRecyclerView.adapter = adapter

        mainAppBarLayout.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout?, appBarState: AppBarState) {
                if(appBarState == AppBarState.COLLAPSED) {
                    mainCollapsingToolbarLayout.title = getString(R.string.label_asteroids)
                } else {
                    mainCollapsingToolbarLayout.title = ""
                }
            }
        })
    }

    private fun setupObservers() {
        viewModel.asteroidFeed.observe(viewLifecycleOwner, {
            it?.let { asteroids ->
                adapter.submitList(asteroidsData = asteroids)
            }

        })

        val viewPagerAdapter = PictureOfTheDayPagerAdapter(emptyList(), requireActivity())
        pictureOfTheDayViewPager.adapter = viewPagerAdapter
        pictureOfTheDayViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        pictureOfTheDayViewPager.setPageTransformer(getPagTransformer())
        TabLayoutMediator(tabLayout, pictureOfTheDayViewPager) { _, _ ->
            /* Use "tab" and "position" to set tabs texts */
        }.attach()

        viewModel.listOfPicturesOfTheDays.observe(viewLifecycleOwner, { listOfPicturesOfTheDays ->
            viewPagerAdapter.submitList(listOfPicturesOfTheDays)
        })
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

    private fun validateMetricsInfoPreferences() {

        val hasLaunchAppPreviously = viewModel.hasLaunchAppPreviously()
        if (hasLaunchAppPreviously.not()) {
            saveMetricsInfoPreferences(shouldShowAgain = true)
        }

        val shouldShowAgain = viewModel.shouldShowAgainMetricsInfo()

        if (shouldShowAgain) {
            context?.let {
                showDialogWithActions(
                    context = it,
                    title = getString(R.string.tell_more_astronomical_units_title),
                    message = getString(R.string.tell_more_astronomical_units),
                    positiveButtonText = getString(R.string.remember_me_later),
                    positiveButtonAction = { saveMetricsInfoPreferences(shouldShowAgain = true) },
                    negativeButtonText = getString(R.string.ok_got_it),
                    negativeButtonAction =  { saveMetricsInfoPreferences(shouldShowAgain = false) },
                )
            }
        }
    }
}
