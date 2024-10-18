package com.umutgultekin.satelliteapp.presentation.satellites.adapter

import androidx.recyclerview.widget.DiffUtil
import com.umutgultekin.satelliteapp.domain.model.SatelliteUiModel

class SatellitesDiffCallBack : DiffUtil.ItemCallback<SatelliteUiModel>() {
    override fun areItemsTheSame(
        oldItem: SatelliteUiModel,
        newItem: SatelliteUiModel
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: SatelliteUiModel,
        newItem: SatelliteUiModel
    ): Boolean {
        return oldItem == newItem
    }

}
