package com.udacity.asteroidradar.database.asteroids.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.features.main.domain.entities.AsteroidFeed
import com.udacity.asteroidradar.features.main.domain.entities.AsteroidsFeedItem

/**
 * Created by jesselima on 4/01/21.
 * This is a part of the project AsteroidsFeedItem Radar.
 */
@JsonClass(generateAdapter = true)
data class AsteroidsFeedResponse(
    @Json(name = "element_count")
   val elementCount: Int? = null,
    @Json(name = "links")
   val paginationLinks: Links? = null,
    @Json(name = "near_earth_objects")
    val nearEarthObjects: Map<String, List<NearEarthObject>>? = null
)

data class Links(
    val next: String? = null,
    val prev: String? = null,
    val self: String? = null
)

data class NearEarthObject(
    @Json(name = "absolute_magnitude_h")
    val absoluteMagnitudeH: Double? = null,
    @Json(name = "close_approach_data")
    val closeApproachData: List<CloseApproachData>? = null,
    @Json(name = "estimated_diameter")
    val estimatedDiameter: EstimatedDiameter? = null,
    @Json(name = "id")
    val id: String? = null,
    @Json(name = "is_potentially_hazardous_asteroid")
    val isPotentiallyHazardousAsteroid: Boolean? = null,
    @Json(name = "is_sentry_object")
    val isSentryObject: Boolean? = null,
    @Json(name = "links")
    val objectLink: Links? = null,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "nasa_jpl_url")
    val nasaJplUrl: String? = null,
    @Json(name = "neo_reference_id")
    val neoReferenceId: String? = null
)

data class CloseApproachData(
    @Json(name = "close_approach_date")
    val closeApproachDate: String? = null,
    @Json(name = "close_approach_date_full")
    val closeApproachDateFull: String? = null,
    @Json(name = "epoch_date_close_approach")
    val epochDateCloseApproach: Long? = null,
    @Json(name = "miss_distance")
    val missDistance: MissDistance? = null,
    @Json(name = "orbiting_body")
    val orbitingBody: String? = null,
    @Json(name = "relative_velocity")
    val relativeVelocity: RelativeVelocity? = null
)

data class EstimatedDiameter(
    val feet: Feet? = null,
    val kilometers: Kilometers? = null,
    val meters: Meters? = null,
    val miles: Miles? = null
)

data class MissDistance(
    val astronomical: String? = null,
    val kilometers: String? = null,
    val lunar: String? = null,
    val miles: String? = null
)

data class RelativeVelocity(
    @Json(name = "kilometers_per_hour")
    val kilometersPerHour: String? = null,
    @Json(name = "kilometers_per_second")
    val kilometersPerSecond: String? = null,
    @Json(name = "miles_per_hour")
    val milesPerHour: String? = null
)

data class Feet(
    @Json(name = "estimated_diameter_max")
    val estimatedDiameterMax: Double? = null,
    @Json(name = "estimated_diameter_min")
    val estimatedDiameterMin: Double? = null
)

data class Kilometers(
    @Json(name = "estimated_diameter_max")
    val estimatedDiameterMax: Double? = null,
    @Json(name = "estimated_diameter_min")
    val estimatedDiameterMin: Double? = null
)

data class Meters(
    @Json(name = "estimated_diameter_max")
    val estimatedDiameterMax: Double? = null,
    @Json(name = "estimated_diameter_min")
    val estimatedDiameterMin: Double? = null
)

data class Miles(
    @Json(name = "estimated_diameter_max")
    val estimatedDiameterMax: Double? = null,
    @Json(name = "estimated_diameter_min")
    val estimatedDiameterMin: Double? = null
)


fun AsteroidsFeedResponse.mapDataToDomain() : List<AsteroidFeed> {

    val list:List<AsteroidFeed> = emptyList()
    val mutableList = list.toMutableList()

    this.nearEarthObjects?.map {


        mutableList.add(AsteroidFeed(header = it.key))

        it.value.forEach { nearEarthObject ->
            mutableList.add(AsteroidFeed(
                feedItem = AsteroidsFeedItem(
                    codename = nearEarthObject.name.orEmpty(),
                    closeApproachDate = nearEarthObject.closeApproachData?.first()?.closeApproachDate ?: "",
                    absoluteMagnitude = nearEarthObject.absoluteMagnitudeH ?: 0.0,
                    estimatedDiameter = nearEarthObject.estimatedDiameter?.kilometers?.estimatedDiameterMax ?: 0.0,
                    relativeVelocity = nearEarthObject.closeApproachData?.first()?.relativeVelocity?.kilometersPerSecond?.toDouble() ?: 0.0,
                    distanceFromEarth = nearEarthObject.closeApproachData?.first()?.missDistance?.astronomical?.toDouble() ?: 0.0,
                    isPotentiallyHazardous = nearEarthObject.isPotentiallyHazardousAsteroid ?: false
                )
            ))
        }
    }
    return mutableList.toList()
}

