package com.umutgultekin.satelliteapp.presentation.satellite_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.umutgultekin.satelliteapp.base.BaseFragment
import com.umutgultekin.satelliteapp.common.State
import com.umutgultekin.satelliteapp.databinding.FragmentSatelliteDetailsBinding
import com.umutgultekin.satelliteapp.domain.model.SatelliteDetailsItemUiModel
import com.umutgultekin.satelliteapp.extensions.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SatelliteDetailsFragment :
    BaseFragment<FragmentSatelliteDetailsBinding>(FragmentSatelliteDetailsBinding::inflate) {

    private val viewModel: SatelliteDetailsViewModel by viewModels()
    private val args: SatelliteDetailsFragmentArgs by navArgs()
    private val satelliteUiModel by lazy { args.uiModel }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        getSatelliteDetails()
        collectSatelliteDetails()
    }

    private fun setupUi() {
        binding.imageViewBack.setOnClickListener { navigateBack() }
    }

    private fun getSatelliteDetails() {
        viewModel.getSatelliteDetails(satelliteUiModel.id)
    }

    private fun collectSatelliteDetails() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.satelliteDetailsStateFlow.collect { state ->
                when (state) {
                    is State.Idle -> hideLoading()
                    is State.Loading -> showLoading()
                    is State.Success -> {
                        hideLoading()
                        state.data?.let {
                            updateSatelliteDetails(it)
                        }
                    }
                    is State.Error -> {
                        hideLoading()
                        navigateBack()
                        showErrorToast()
                    }
                }
            }
        }
    }

    private fun updateSatelliteDetails(uiModel: SatelliteDetailsItemUiModel) = with(binding) {
        textViewName.text = satelliteUiModel.name
        textViewDate.text = uiModel.firstFlight
        textViewCost.text = uiModel.costPerLaunch
        textViewHeightMass.text = uiModel.heightMass
        textViewLastPosition.text = uiModel.position

        linearLayout.visible()
    }

    private fun navigateBack() {
        findNavController().navigateUp()
    }
}
