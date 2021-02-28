package com.udacity.asteroidradar.features.copyright

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.R
import kotlinx.android.synthetic.main.layout_item_list_copyright.view.*
import kotlinx.android.synthetic.main.layout_item_list_picture_gallery.view.*

/**
 * Created by jesselima on 28/02/21.
 * This is a part of the project Asteroid Radar.
 */

class AuthorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

	private var view: View = itemView
	private var copyright: Copyright? = null

	init {
		itemView.copyrightButtonActionLink.setOnClickListener(this)
	}

	override fun onClick(v: View?) {
		view.context.startActivity(
			Intent(
				Intent.ACTION_VIEW,
				Uri.parse(copyright?.link)
			)
		)
	}

	fun bindDataToView(copyrightData: Copyright) {
		this.copyright = copyrightData
		view.copyrightSource.text = copyrightData.sourceName
		view.copyrightauthorName.text = copyrightData.authorName

		val isAnimation = copyright?.isAnimation ?: false
		if (isAnimation) {
			view.copyrightImage.setAnimation(copyrightData.imageResId)
		} else {
			view.copyrightImage.setImageResource(copyrightData.imageResId)
		}
	}
}