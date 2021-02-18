package com.udacity.asteroidradar.features.asteroiddetail.presentation


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.core.extensions.showDialog
import com.udacity.asteroidradar.core.extensions.showWithLongFadeIn
import com.udacity.asteroidradar.databinding.FragmentAsteroidDetailBinding
import kotlinx.android.synthetic.main.fragment_asteroid_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AsteroidDetailFragment : Fragment() {

    private val viewModel by viewModel<AsteroidDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        ontainer: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentAsteroidDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.iconInfoAboutLunarUnits.setOnClickListener {
            displayLunarUnitExplanationDialog()
        }

        binding.iconInfoAboutAstronomicalUnits.setOnClickListener {
            displayAstronomicalUnitExplanationDialog()
        }

        binding.backIconAsteroidDetailsTopAppBar.setOnClickListener {
            findNavController().navigate(R.id.mainFragment)
        }

        binding.backIconAsteroidDetailsTopAppBar.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.buttonGoToNASAJplWebsite.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(viewModel.asteroidsFeedItem.value?.nasaJplUrl)
                )
            )
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        asteroidDetailsContent.showWithLongFadeIn()
        arguments?.let {
            val asteroidId = it.getLong("asteroidId")
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

    private fun displayLunarUnitExplanationDialog() {
        context?.let {
            showDialog(
                context = it,
                title = getString(R.string.tell_more_lunar_units_title),
                message = getString(R.string.tell_more_lunar_units),
            )
        }
    }
}
