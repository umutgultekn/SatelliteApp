package com.umutgultekin.satelliteapp.data.remote.model


import com.google.gson.annotations.SerializedName
import com.umutgultekin.satelliteapp.common.Constants.EMPTY_STRING
import com.umutgultekin.satelliteapp.common.Constants.SLASH
import com.umutgultekin.satelliteapp.domain.model.SatelliteDetailsItemUiModel
import com.umutgultekin.satelliteapp.extensions.orZero
import com.umutgultekin.satelliteapp.extensions.toDateFormat
import com.umutgultekin.satelliteapp.extensions.toFormattedString

data class SatelliteDetailsItemApiModel(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("cost_per_launch")
    val costPerLaunch: Long? = null,
    @SerializedName("first_flight")
    val firstFlight: String? = null,
    @SerializedName("height")
    val height: Int? = null,
    @SerializedName("mass")
    val mass: Int? = null
)

fun SatelliteDetailsItemApiModel.toUiModel(): SatelliteDetailsItemUiModel {
    return SatelliteDetailsItemUiModel(
        id = id.orZero(),
        costPerLaunch = costPerLaunch.toFormattedString(),
        firstFlight = firstFlight.toDateFormat().orEmpty(),
        heightMass = height.toString().plus(SLASH).plus(mass.toString()),
        position = EMPTY_STRING
    )
}