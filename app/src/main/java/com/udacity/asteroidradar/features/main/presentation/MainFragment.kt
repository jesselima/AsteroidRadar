package com.udacity.asteroidradar.features.main.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.features.main.presentation.adapter.AsteroidsAdapter
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainFragment : Fragment() {

    private val viewModel by viewModel<MainViewModel>()

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
            it?.let { result ->
                Timber.d("PictureOfTheDay -->>> $result")
                textView.text = result.title
            }
        })
        viewModel.asteroidFeed.observe(viewLifecycleOwner, {
            it?.let { result ->
                Timber.d("SIZE -->>> ${result.size}")
                Timber.d("Data -->>> $result")
                adapter.submitList(asteroidsData = it)
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
