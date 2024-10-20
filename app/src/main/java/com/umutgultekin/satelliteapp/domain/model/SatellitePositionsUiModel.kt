package com.umutgultekin.satelliteapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class SatellitePositionsItemUiModel(
    val positions: List<PositionUiModel>
) : Parcelable


@Parcelize
data class PositionUiModel(
    val posX: Double,
    val posY: Double
) : Parcelable