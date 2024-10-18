package com.umutgultekin.satelliteapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SatellitesUiModel(
    val satellites: List<SatelliteUiModel>
) : Parcelable

@Parcelize
data class SatelliteUiModel(
    val id: Int,
    val active: Boolean,
    val name: String
) : Parcelable