package com.udacity.asteroidradar.features.main.data.models

import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.udacity.asteroidradar.features.main.domain.entities.PictureOfDay

data class PictureOfDayResponse(
	@PrimaryKey
	val id: Long? = null,
	@Json(name = "media_type")
	val mediaType: String? = null,
	@Json(name = "title")
	val title: String? = null,
	@Json(name = "url")
	val imageUrl: String? = null,
	@Json(name = "hdurl")
	val highDefinitionImageUrl: String? = null,
	@Json(name = "date")
	val date: String? = null,
	@Json(name = "explanation")
	val explanation: String? = null,
)


fun PictureOfDayResponse.mapToLocalDatabaseModel() : PictureOfDay {
	return PictureOfDay(
		mediaType = this.mediaType ?: "",
		title = this.title ?: "",
		imageUrl = this.imageUrl ?: "",
		highDefinitionImageUrl = this.highDefinitionImageUrl ?: "",
		date = this.date ?: "",
		explanation = this.explanation ?: ""
	)
}