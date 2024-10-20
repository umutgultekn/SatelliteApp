package com.umutgultekin.satelliteapp.presentation.satellites.adapter

import androidx.recyclerview.widget.DiffUtil
import com.umutgultekin.satelliteapp.domain.model.SatelliteItemUiModel

class SatellitesDiffCallBack : DiffUtil.ItemCallback<SatelliteItemUiModel>() {
    override fun areItemsTheSame(
        oldItem: SatelliteItemUiModel,
        newItem: SatelliteItemUiModel
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: SatelliteItemUiModel,
        newItem: SatelliteItemUiModel
    ): Boolean {
        return oldItem == newItem
    }

}
