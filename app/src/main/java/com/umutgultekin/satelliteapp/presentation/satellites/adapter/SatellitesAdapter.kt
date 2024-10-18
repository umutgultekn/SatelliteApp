package com.umutgultekin.satelliteapp.presentation.satellites.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.umutgultekin.satelliteapp.databinding.ItemSatellitesBinding
import com.umutgultekin.satelliteapp.domain.model.SatelliteUiModel

class SatellitesAdapter(private val onItemClicked: (SatelliteUiModel) -> Unit) :
    ListAdapter<SatelliteUiModel, SatellitesViewHolder>(
        SatellitesDiffCallBack()
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = SatellitesViewHolder(
        binding = ItemSatellitesBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
    )

    override fun onBindViewHolder(holder: SatellitesViewHolder, position: Int) {
        holder.bind(uiModel = getItem(position), onItemClicked)
    }
}
