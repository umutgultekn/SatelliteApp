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
import com.umutgultekin.satelliteapp.domain.model.SatelliteItemUiModel
import com.umutgultekin.satelliteapp.extensions.textChanges
import com.umutgultekin.satelliteapp.presentation.satellites.adapter.SatellitesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SatellitesFragment :
    BaseFragment<FragmentSatellitesBinding>(FragmentSatellitesBinding::inflate) {

    private val viewModel: SatellitesViewModel by viewModels()
    private lateinit var satellitesAdapter: SatellitesAdapter
    private var searchJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        collectSatellites()
    }

    private fun setupUi() {
        setupRecyclerView()
        setupSearchView()
    }

    private fun setupRecyclerView() {
        satellitesAdapter = SatellitesAdapter { satelliteUiModel ->
            navigateToDetails(satelliteUiModel = satelliteUiModel)
        }

        binding.recyclerviewSatellites.apply {
            addItemDecoration(DividerItemDecoration(context))
            itemAnimator = NoAnimationItemAnimator()
            adapter = satellitesAdapter
        }
    }

    @OptIn(FlowPreview::class)
    private fun setupSearchView() {
        binding.editTextSearch.setText(viewModel.searchQuery.value)
        searchJob?.cancel()

        searchJob = viewLifecycleOwner.lifecycleScope.launch {
            binding.editTextSearch.textChanges()
                .debounce(SEARCH_DELAY)
                .collect { query ->
                    viewModel.updateSearchQuery(query)
                }
        }
    }

    private fun collectSatellites() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.satellitesStateFlow.collect { state ->
                when (state) {
                    is State.Idle -> Unit
                    is State.Loading -> showLoading()
                    is State.Success -> {
                        hideLoading()
                        updateSatellitesList(satellites = state.data?.satellites)
                    }

                    is State.Error -> {
                        hideLoading()
                        showErrorToast()
                    }

                }
            }
        }
    }

    private fun updateSatellitesList(satellites: List<SatelliteItemUiModel>?) {
        satellites?.let { satellitesAdapter.submitList(satellites) }
    }

    private fun navigateToDetails(satelliteUiModel: SatelliteItemUiModel) {
        findNavController().navigate(
            SatellitesFragmentDirections.toSatelliteDetailsFragment(satelliteUiModel)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchJob?.cancel()
    }

    companion object {
        private const val SEARCH_DELAY = 300L
    }
}
