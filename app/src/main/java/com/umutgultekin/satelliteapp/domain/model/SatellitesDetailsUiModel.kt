package com.umutgultekin.satelliteapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class SatellitesDetailsUiModel(
    val satellitesDetails: List<SatelliteDetailsItemUiModel>
) : Parcelable

@Serializable
@Parcelize
data class SatelliteDetailsItemUiModel(
    val id: Long,
    val costPerLaunch: String,
    val firstFlight: String,
    val heightMass: String,
    val position: String
) : Parcelable