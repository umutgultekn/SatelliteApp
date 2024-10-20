package com.umutgultekin.satelliteapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SatellitesUiModel(
    val satellites: List<SatelliteItemUiModel>
) : Parcelable

@Parcelize
data class SatelliteItemUiModel(
    val id: Long,
    val active: Boolean,
    val name: String
) : Parcelable