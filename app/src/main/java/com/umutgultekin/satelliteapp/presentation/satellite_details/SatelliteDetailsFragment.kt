package com.umutgultekin.satelliteapp.presentation.satellite_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.umutgultekin.satelliteapp.base.BaseFragment
import com.umutgultekin.satelliteapp.databinding.FragmentSatelliteDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SatelliteDetailsFragment :
    BaseFragment<FragmentSatelliteDetailsBinding>(FragmentSatelliteDetailsBinding::inflate) {

    private val viewModel: SatelliteDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        binding.imageViewBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}
