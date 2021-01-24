package com.udacity.asteroidradar.features.main.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.udacity.asteroidradar.features.main.domain.entities.PictureOfDay

class PictureOfTheDayPagerAdapter(
    private var picturesOfTheDay: List<PictureOfDay> = emptyList(),
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = picturesOfTheDay.size

    override fun createFragment(position: Int): Fragment {
        return PictureOfTheDayViewPagerFragment.newInstance(
            pictureOfDay = picturesOfTheDay[position]
        )
    }

    fun submitList(listOfPicturesOfTheDays: List<PictureOfDay>) {
        picturesOfTheDay = listOfPicturesOfTheDays
        notifyDataSetChanged()
    }
}