package com.umutgultekin.satelliteapp.data.remote.model

import com.google.gson.annotations.SerializedName
import com.umutgultekin.satelliteapp.domain.model.SatelliteItemUiModel
import com.umutgultekin.satelliteapp.domain.model.SatellitesUiModel
import com.umutgultekin.satelliteapp.extensions.orFalse
import com.umutgultekin.satelliteapp.extensions.orZero

data class SatellitesApiModel(
    val satellites: List<SatelliteItemApiModel>? = null
)

data class SatelliteItemApiModel(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("active")
    val active: Boolean? = null
)

fun SatellitesApiModel.toUiModel(): SatellitesUiModel {
    return SatellitesUiModel(satellites = satellites?.map { satellite ->
        SatelliteItemUiModel(
            id = satellite.id.orZero(),
            name = satellite.name.orEmpty(),
            active = satellite.active.orFalse()
        )
    }.orEmpty())
}