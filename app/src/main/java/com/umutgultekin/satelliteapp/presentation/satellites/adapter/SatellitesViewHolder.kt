package com.umutgultekin.satelliteapp.presentation.satellites.adapter

import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.umutgultekin.satelliteapp.R
import com.umutgultekin.satelliteapp.databinding.ItemSatellitesBinding
import com.umutgultekin.satelliteapp.domain.model.SatelliteItemUiModel


class SatellitesViewHolder(
    private val binding: ItemSatellitesBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        uiModel: SatelliteItemUiModel,
        onItemClicked: ((SatelliteItemUiModel) -> Unit)?
    ) = with(binding) {

        val context = binding.root.context
        val activeColor = context.getColor(R.color.dark_gray)
        val inactiveColor = context.getColor(R.color.gray)

        textViewSatelliteName.apply {
            text = uiModel.name
            setTextColor(if (uiModel.active) activeColor else inactiveColor)
        }

        textViewStatus.apply {
            text = context.getString(if (uiModel.active) R.string.active else R.string.passive)
            setTextColor(if (uiModel.active) activeColor else inactiveColor)
        }

        val drawableId = if (uiModel.active) {
            R.drawable.ic_circle_green_16
        } else {
            R.drawable.ic_circle_red_16
        }

        val drawable = AppCompatResources.getDrawable(context, drawableId)
        imageViewPassive.setImageDrawable(drawable)

        cardView.setOnClickListener {
            if (uiModel.active) onItemClicked?.invoke(uiModel)
        }
    }
}
