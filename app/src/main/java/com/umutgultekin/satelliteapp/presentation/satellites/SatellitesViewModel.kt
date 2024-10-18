package com.umutgultekin.satelliteapp.presentation.satellites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umutgultekin.satelliteapp.common.State
import com.umutgultekin.satelliteapp.domain.model.SatellitesUiModel
import com.umutgultekin.satelliteapp.domain.use_case.SatellitesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SatellitesViewModel @Inject constructor(
    private val satellitesUseCase: SatellitesUseCase,
) : ViewModel() {

    private val _satellitesStateFlow =
        MutableStateFlow<State<SatellitesUiModel>>(State.Loading())

    val satellitesStateFlow: StateFlow<State<SatellitesUiModel>> =
        _satellitesStateFlow.asStateFlow()


    fun getSatellites() {
        viewModelScope.launch {

            val test = satellitesUseCase.getSatellites()

            _satellitesStateFlow.emit(State.Success(test))
        }
    }


}
