package com.udacity.asteroidradar.features.mainscreen.presentation.picturesviewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.udacity.asteroidradar.features.mainscreen.domain.entities.PictureOfDay

class PictureOfTheDayPagerAdapter(
    private var picturesOfTheDay: MutableList<PictureOfDay> = arrayListOf(),
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

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