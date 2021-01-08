package com.udacity.asteroidradar.database.asteroids.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "asteroids")
data class AsteroidsFeedItem(
	@PrimaryKey
	val id: Long? = null,
	@ColumnInfo(name = "codename")
	val codename: String,
	@ColumnInfo(name = "close_approach_date")
	val closeApproachDate: String,
	@ColumnInfo(name = "absolute_magnitude")
	val absoluteMagnitude: Double,
	@ColumnInfo(name = "estimated_diameter")
	val estimatedDiameter: Double,
	@ColumnInfo(name = "relative_velocity")
	val relativeVelocity: Double,
	@ColumnInfo(name = "distance_fromEarth")
	val distanceFromEarth: Double,
	@ColumnInfo(name = "is_potentially_hazardous")
	val isPotentiallyHazardous: Boolean
) : Parcelable