package com.umutgultekin.satelliteapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class SatellitePositionsItemUiModel(
    val positions: List<PositionUiModel>
) : Parcelable

@Serializable
@Parcelize
data class PositionUiModel(
    val posX: Double,
    val posY: Double
) : Parcelable