package com.umutgultekin.satelliteapp.data.remote.model


import com.google.gson.annotations.SerializedName
import com.umutgultekin.satelliteapp.domain.model.PositionUiModel
import com.umutgultekin.satelliteapp.domain.model.SatellitePositionsItemUiModel
import com.umutgultekin.satelliteapp.extensions.orZero

data class SatellitePositionsApiModel(
    @SerializedName("list")
    val list: List<SatellitePositionsItemApiModel>? = null
)

data class SatellitePositionsItemApiModel(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("positions")
    val positions: List<PositionApiModel?>? = null
)

data class PositionApiModel(
    @SerializedName("posX")
    val posX: Double? = null,
    @SerializedName("posY")
    val posY: Double? = null
)

fun SatellitePositionsItemApiModel.toUiModel(): SatellitePositionsItemUiModel {
    return SatellitePositionsItemUiModel(
        positions = positions?.map {
            PositionUiModel(
                posX = it?.posX.orZero(),
                posY = it?.posY.orZero()
            )
        }.orEmpty()
    )
}