package com.umutgultekin.satelliteapp.presentation.satellites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.umutgultekin.satelliteapp.base.BaseFragment
import com.umutgultekin.satelliteapp.common.DividerItemDecoration
import com.umutgultekin.satelliteapp.common.State
import com.umutgultekin.satelliteapp.databinding.FragmentSatellitesBinding
import com.umutgultekin.satelliteapp.presentation.satellites.adapter.SatellitesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SatellitesFragment :
    BaseFragment<FragmentSatellitesBinding>(FragmentSatellitesBinding::inflate) {

    private val viewModel: SatellitesViewModel by viewModels()

    private lateinit var satellitesAdapter: SatellitesAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        viewModel.getSatellites()
        collectSatellites()

    }

    private fun collectSatellites() {
        lifecycleScope.launch {
            viewModel.satellitesStateFlow.collect { state ->
                when (state) {
                    is State.Loading -> Unit
                    is State.Success -> satellitesAdapter.submitList(state.data?.satellites)
                    is State.Error -> Unit
                }
            }
        }
    }


    private fun initRecyclerView() {
        satellitesAdapter = SatellitesAdapter { satelliteUiModel ->

        }

        binding.recyclerviewSatellites.apply {
            addItemDecoration(DividerItemDecoration(context))
            adapter = satellitesAdapter
        }
    }
}
