package com.umutgultekin.satelliteapp.presentation.satellites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umutgultekin.satelliteapp.common.Constants.REQUEST_TIMEOUT
import com.umutgultekin.satelliteapp.common.State
import com.umutgultekin.satelliteapp.domain.model.SatellitesUiModel
import com.umutgultekin.satelliteapp.domain.use_case.SatellitesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

@HiltViewModel
class SatellitesViewModel @Inject constructor(
    private val satellitesUseCase: SatellitesUseCase,
) : ViewModel() {

    private val _satellitesStateFlow =
        MutableStateFlow<State<SatellitesUiModel>>(State.Loading())
    val satellitesStateFlow: StateFlow<State<SatellitesUiModel>> =
        _satellitesStateFlow.asStateFlow()

    init {
        getSatellites()
    }

    private fun getSatellites() {
        viewModelScope.launch {
            runCatching {
                withTimeout(REQUEST_TIMEOUT) {
                    delay(1000L)

                    satellitesUseCase.getSatellites()
                        .flowOn(Dispatchers.IO)
                        .firstOrNull()?.let { satellites ->
                            _satellitesStateFlow.emit(State.Success(satellites))
                        } ?: run {
                        _satellitesStateFlow.emit(State.Error())
                    }
                }
            }.getOrElse { _satellitesStateFlow.emit(State.Error()) }
        }
    }
}
