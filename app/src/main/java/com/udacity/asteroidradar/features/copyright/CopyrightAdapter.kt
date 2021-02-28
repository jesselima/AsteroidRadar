package com.udacity.asteroidradar.features.copyright

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.core.extensions.inflate
import com.udacity.asteroidradar.core.extensions.showListItemWithFadeIn

/**
 * Created by jesselima on 28/02/21.
 * This is a part of the project Asteroid Radar.
 */
class CopyrightAdapter(
	private var copyrights: List<Copyright> = emptyList()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

	fun submitList(copyrightsData: List<Copyright>) {
		copyrights = emptyList()
		copyrights = copyrightsData
		notifyDataSetChanged()
	}

	override fun onCreateViewHolder(rootViewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		return rootViewGroup.inflate(R.layout.layout_item_list_copyright).run {
			showListItemWithFadeIn()
			AuthorViewHolder(this)
		}
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		val author = copyrights[position]
		val viewHolder = holder as AuthorViewHolder
		viewHolder.bindDataToView(author)
	}

	override fun getItemCount() = copyrights.size
}