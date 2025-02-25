package com.udacity.asteroidradar.features.mainscreen.domain.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "picture_of_the_day")
data class PictureOfDay(
	@PrimaryKey(autoGenerate = true)
	val id: Long? = null,
	@ColumnInfo(name = "media_type")
	val mediaType: String = "",
	@ColumnInfo(name = "title")
	val title: String = "",
	@ColumnInfo(name = "url")
	val imageUrl: String = "",
	@ColumnInfo(name = "hdurl")
	val highDefinitionImageUrl: String = "",
	@ColumnInfo(name = "date")
	val date: String = "",
	@ColumnInfo(name = "explanation")
	val explanation: String = "",
	@ColumnInfo(name = "copyright")
	val copyright: String? = null,
	@ColumnInfo(name = "is_favorite")
	var isFavorite: Boolean = false,
	@ColumnInfo(name = "created_at")
	var createdAt: String? = null,
	@ColumnInfo(name = "modified_at")
	var modifiedAt: String? = null,
) : Parcelable