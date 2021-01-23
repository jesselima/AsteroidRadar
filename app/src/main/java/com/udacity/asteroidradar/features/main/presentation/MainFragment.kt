package com.udacity.asteroidradar.features.main.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.core.extensions.AppBarState
import com.udacity.asteroidradar.core.extensions.AppBarStateChangeListener
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.features.main.presentation.adapter.AsteroidsAdapter
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainFragment : Fragment() {

    private val viewModel by viewModel<MainViewModel>()

    private var appBarDefaultTitle = "Asteroids"

    private val adapter: AsteroidsAdapter by lazy {
        AsteroidsAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        asteroidRecyclerView.adapter = adapter

        viewModel.pictureOfTheDay.observe(viewLifecycleOwner, {

            it?.highDefinitionImageUrl?.let { url ->
                appBarDefaultTitle = it.title
                Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.backdrop_image_overlay_darker_bottom)
                    .error(R.drawable.ic_astronaut_image_not_found)
                    .into(mainCollapsingToolbarImageView);
            }
        })
        viewModel.asteroidFeed.observe(viewLifecycleOwner, {
            it?.let { asteroids ->
                adapter.submitList(asteroidsData = asteroids)
            }

        })

        mainAppBarLayout.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout?, appBarState: AppBarState) {
                if(appBarState == AppBarState.EXPANDED || appBarState == AppBarState.IDLE) {
                    mainCollapsingToolbarLayout.title = appBarDefaultTitle
                } else {
                    mainCollapsingToolbarLayout.title = "Asteroids"
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
