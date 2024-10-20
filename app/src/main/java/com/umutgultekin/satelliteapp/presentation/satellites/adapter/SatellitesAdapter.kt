package com.umutgultekin.satelliteapp.presentation.satellites.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.ListAdapter
import com.umutgultekin.satelliteapp.databinding.ItemSatellitesBinding
import com.umutgultekin.satelliteapp.domain.model.SatelliteItemUiModel

class SatellitesAdapter(private val onItemClicked: (SatelliteItemUiModel) -> Unit) :
    ListAdapter<SatelliteItemUiModel, SatellitesViewHolder>(
        SatellitesDiffCallBack()
    ), Filterable {

    private var originalList = emptyList<SatelliteItemUiModel>()

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
        holder.bind(getItem(position), onItemClicked)
    }

    override fun getFilter(): Filter {
        return searchFilter
    }

    private val searchFilter: Filter = object : Filter() {
        override fun performFiltering(input: CharSequence): FilterResults {
            val filteredList = if (input.isEmpty()) {
                originalList
            } else {
                originalList.filter { it.name.lowercase().contains(input) }
            }

            return FilterResults().apply { values = filteredList }
        }

        override fun publishResults(input: CharSequence, results: FilterResults) {
            val filteredList = results.values as? List<SatelliteItemUiModel> ?: emptyList()
            submitList(filteredList)
        }
    }

    fun setData(list: List<SatelliteItemUiModel>) {
        this.originalList = list
        submitList(list)
    }
}
