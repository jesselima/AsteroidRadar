package com.udacity.asteroidradar.features.main.domain.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "asteroids")
data class AsteroidsFeedItem(
	@PrimaryKey(autoGenerate = false)
	val id: Long? = null,
	@ColumnInfo(name = "codename")
	val codename: String,
	@ColumnInfo(name = "close_approach_date")
	val closeApproachDate: String,
	@ColumnInfo(name = "absolute_magnitude")
	val absoluteMagnitude: Double,
	@ColumnInfo(name = "estimated_diameter")
	val estimatedDiameter: Double,
	@ColumnInfo(name = "relative_velocity_miles_per_hour")
	val relativeVelocityMilesPerHour: Double,
	@ColumnInfo(name = "relative_velocity_km_per_hour")
	val relativeVelocityKilometersPerHour: Double,
	@ColumnInfo(name = "relative_velocity_km_per_second")
	val relativeVelocityKilometersPerSecond: Double,
	@ColumnInfo(name = "distance_from_earth_au")
	val distanceFromEarthAu: Double,
	@ColumnInfo(name = "distance_from_earth_miles")
	val distanceFromEarthMiles: Double,
	@ColumnInfo(name = "distance_from_earth_km")
	val distanceFromEarthKm: Double,
	@ColumnInfo(name = "distance_from_earth_lunar")
	val distanceFromEarthLunar: Double,
	@ColumnInfo(name = "is_potentially_hazardous")
	val isPotentiallyHazardous: Boolean,
	@ColumnInfo(name = "date")
	val date: String,
	@ColumnInfo(name = "created_at")
	var createdAt: String? = null,
	@ColumnInfo(name = "modified_at")
	var modifiedAt: String? = null
) : Parcelable