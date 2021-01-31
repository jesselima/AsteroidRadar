package com.udacity.asteroidradar.features.main.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.udacity.asteroidradar.features.main.domain.entities.PictureOfDay

private const val FIRST_POSITION = 0

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

    fun addToList(pictureByDate: PictureOfDay) {
        picturesOfTheDay.add(FIRST_POSITION, pictureByDate)
        notifyDataSetChanged()
    }
}