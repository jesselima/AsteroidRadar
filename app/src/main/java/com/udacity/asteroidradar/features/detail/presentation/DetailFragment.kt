package com.udacity.asteroidradar.features.detail.presentation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.core.extensions.showDialog
import com.udacity.asteroidradar.core.extensions.showWithLongFadeIn
import com.udacity.asteroidradar.databinding.FragmentDetailBinding
import kotlinx.android.synthetic.main.fragment_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class DetailFragment : Fragment() {

    private val viewModel by viewModel<DetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        ontainer: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.helpButton.setOnClickListener {
            displayAstronomicalUnitExplanationDialog()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        asteroidDetailsContent.showWithLongFadeIn()
        arguments?.let {
            val asteroidId = it.getLong("ASTEROID_ID")
            Timber.d("ASTEROID_ID: $asteroidId")
            viewModel.getLocalAsteroidById(id = asteroidId)
        }
    }

    private fun displayAstronomicalUnitExplanationDialog() {
        context?.let {
            showDialog(
                context = it,
                title = getString(R.string.tell_more_astronomical_units_title),
                message = getString(R.string.tell_more_astronomical_units),
            )
        }
    }
}
