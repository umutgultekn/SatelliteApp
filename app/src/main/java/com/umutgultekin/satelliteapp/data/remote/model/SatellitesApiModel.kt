package com.umutgultekin.satelliteapp.data.remote.model

import com.google.gson.annotations.SerializedName
import com.umutgultekin.satelliteapp.domain.model.SatelliteUiModel
import com.umutgultekin.satelliteapp.domain.model.SatellitesUiModel

data class SatellitesApiModel(
    val satellites: List<SatelliteApiModel>
)

data class SatelliteApiModel(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("active")
    val active: Boolean?
)

fun SatellitesApiModel.toUiModel(): SatellitesUiModel {
    return SatellitesUiModel(satellites = satellites.map { satellite ->
        SatelliteUiModel(
            id = satellite.id ?: 0,
            name = satellite.name ?: "",
            active = satellite.active ?: false
        )
    })
}