package com.udacity.asteroidradar.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        ontainer: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        arguments?.let {
            binding.asteroidsFeedItem = DetailFragmentArgs.fromBundle(it).selectedAsteroidsFeedItem
        }

        binding.helpButton.setOnClickListener {
            displayAstronomicalUnitExplanationDialog()
        }

        return binding.root
    }

    private fun displayAstronomicalUnitExplanationDialog() {
        activity?.let {
            val builder = AlertDialog.Builder(it)
                .setMessage(getString(R.string.astronomica_unit_explanation))
                .setPositiveButton(android.R.string.ok, null)
            builder.create().show()
        }
    }
}
