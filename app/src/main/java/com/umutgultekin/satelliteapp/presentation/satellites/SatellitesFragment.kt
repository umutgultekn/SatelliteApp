package com.umutgultekin.satelliteapp.presentation.satellites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.umutgultekin.satelliteapp.base.BaseFragment
import com.umutgultekin.satelliteapp.common.DividerItemDecoration
import com.umutgultekin.satelliteapp.common.NoAnimationItemAnimator
import com.umutgultekin.satelliteapp.common.State
import com.umutgultekin.satelliteapp.databinding.FragmentSatellitesBinding
import com.umutgultekin.satelliteapp.domain.model.SatelliteUiModel
import com.umutgultekin.satelliteapp.extensions.textChanges
import com.umutgultekin.satelliteapp.presentation.satellites.adapter.SatellitesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SatellitesFragment :
    BaseFragment<FragmentSatellitesBinding>(FragmentSatellitesBinding::inflate) {

    private val viewModel: SatellitesViewModel by viewModels()

    private lateinit var satellitesAdapter: SatellitesAdapter
    private val searchDelay = 300L
    private var searchJob: Job? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initSearchView()
        viewModel.getSatellites()
        collectSatellites()
    }

    private fun collectSatellites() {
        lifecycleScope.launch {
            viewModel.satellitesStateFlow.collect { state ->
                when (state) {
                    is State.Loading -> Unit
                    is State.Success -> state.data?.satellites?.let { submitList(satellites = it) }
                    is State.Error -> Unit
                }
            }
        }
    }


    private fun submitList(satellites: List<SatelliteUiModel>) {
        satellitesAdapter.setData(satellites)
    }

    private fun initRecyclerView() {
        satellitesAdapter = SatellitesAdapter { satelliteUiModel ->
            findNavController().navigate(
                SatellitesFragmentDirections.toSatelliteDetailsFragment()
            )
        }

        binding.recyclerviewSatellites.apply {
            addItemDecoration(DividerItemDecoration(context))
            itemAnimator = NoAnimationItemAnimator()
            adapter = satellitesAdapter
        }
    }

    @OptIn(FlowPreview::class)
    private fun initSearchView() {
        searchJob?.cancel()

        searchJob = lifecycleScope.launch {
            binding.editTextSearch.textChanges()
                .debounce(searchDelay)
                .distinctUntilChanged()
                .collect { query ->
                    satellitesAdapter.filter.filter(query.lowercase())
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchJob?.cancel()
    }
}
