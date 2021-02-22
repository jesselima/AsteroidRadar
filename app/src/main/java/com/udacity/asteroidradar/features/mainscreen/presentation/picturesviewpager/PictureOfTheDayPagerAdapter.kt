package com.udacity.asteroidradar.features.mainscreen.presentation.picturesviewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.udacity.asteroidradar.features.mainscreen.domain.entities.PictureOfDay

class PictureOfTheDayPagerAdapter(
    private var picturesOfTheDay: MutableList<PictureOfDay> = arrayListOf(),
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = picturesOfTheDay.size

    override fun createFragment(position: Int): Fragment {
        return PictureOfTheDayViewPagerFragment.newInstance(
            pictureOfDay = picturesOfTheDay[position]
        )
    }

    fun submitList(listOfPicturesOfTheDays: List<PictureOfDay>) {
        picturesOfTheDay = listOfPicturesOfTheDays.toMutableList()
        notifyDataSetChanged()
    }
}