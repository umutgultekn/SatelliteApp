package com.umutgultekin.satelliteapp.presentation.satellites.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.ListAdapter
import com.umutgultekin.satelliteapp.databinding.ItemSatellitesBinding
import com.umutgultekin.satelliteapp.domain.model.SatelliteUiModel

class SatellitesAdapter(private val onItemClicked: (SatelliteUiModel) -> Unit) :
    ListAdapter<SatelliteUiModel, SatellitesViewHolder>(
        SatellitesDiffCallBack()
    ), Filterable {

    private var originList = listOf<SatelliteUiModel>()

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

    override fun getFilter(): Filter {
        return searchFilter
    }

    private val searchFilter : Filter = object : Filter() {
        override fun performFiltering(input: CharSequence): FilterResults {
            val filteredList = if (input.isEmpty()) {
                originList
            } else {
                originList.filter { it.name.lowercase().contains(input) }
            }
            return FilterResults().apply { values = filteredList }
        }

        override fun publishResults(input: CharSequence, results: FilterResults) {
            submitList(results.values as ArrayList<SatelliteUiModel>)
        }
    }

    fun setData(list: List<SatelliteUiModel>){
        this.originList = list
        submitList(list)
    }
}
