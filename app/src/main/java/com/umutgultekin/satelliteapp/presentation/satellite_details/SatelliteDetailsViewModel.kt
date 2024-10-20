package com.umutgultekin.satelliteapp.presentation.satellite_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umutgultekin.satelliteapp.common.Constants.REQUEST_TIMEOUT
import com.umutgultekin.satelliteapp.common.State
import com.umutgultekin.satelliteapp.data.local.DataStoreManager
import com.umutgultekin.satelliteapp.domain.model.PositionUiModel
import com.umutgultekin.satelliteapp.domain.model.SatelliteDetailsItemUiModel
import com.umutgultekin.satelliteapp.domain.model.SatellitePositionsItemUiModel
import com.umutgultekin.satelliteapp.domain.use_case.SatelliteDetailsUseCase
import com.umutgultekin.satelliteapp.domain.use_case.SatellitePositionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject

@HiltViewModel
class SatelliteDetailsViewModel @Inject constructor(
    private val satelliteDetailsUseCase: SatelliteDetailsUseCase,
    private val satellitePositionsUseCase: SatellitePositionsUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _satelliteDetailsStateFlow =
        MutableStateFlow<State<SatelliteDetailsItemUiModel>>(State.Idle())
    val satelliteDetailsStateFlow: StateFlow<State<SatelliteDetailsItemUiModel>> =
        _satelliteDetailsStateFlow.asStateFlow()

    fun getSatelliteDetails(id: Long) {
        viewModelScope.launch {
            loadCachedSatelliteDetails(id) ?: fetchSatelliteDetailsFromServer(id)
        }
    }

    private suspend fun loadCachedSatelliteDetails(id: Long): SatelliteDetailsItemUiModel? {
        val cachedDetails = withContext(Dispatchers.IO) {
            runCatching { dataStoreManager.getSatelliteDetails(id).firstOrNull() }.getOrNull()
        }

        cachedDetails?.let { uiModel ->
            val positions = fetchSatellitePositions(id)

            if (positions?.positions.isNullOrEmpty()) {
                _satelliteDetailsStateFlow.emit(State.Success(uiModel))
            } else {
                updateSatellitePositionInRealTime(uiModel, positions?.positions.orEmpty())
            }

            return uiModel
        }

        return null
    }

    private suspend fun fetchSatelliteDetailsFromServer(id: Long) {
        _satelliteDetailsStateFlow.emit(State.Loading())
        delay(1000L)

        val details = fetchSatelliteDetails(id)
        val positions = fetchSatellitePositions(id)?.positions.orEmpty()

        if (details == null) {
            _satelliteDetailsStateFlow.emit(State.Error())
            return
        }

        if (positions.isEmpty()) {
            _satelliteDetailsStateFlow.emit(State.Success(details))
            saveSatelliteDetails(details = details)
        } else {
            updateSatellitePositionInRealTime(
                details = details,
                positions = positions
            )
        }
    }

    private suspend fun fetchSatelliteDetails(id: Long): SatelliteDetailsItemUiModel? {
        return withTimeoutOrNull(REQUEST_TIMEOUT) {
            satelliteDetailsUseCase.getSatelliteDetails(id)
                .flowOn(Dispatchers.IO)
                .firstOrNull()
        }
    }


    private suspend fun fetchSatellitePositions(id: Long): SatellitePositionsItemUiModel? {
        return withTimeoutOrNull(REQUEST_TIMEOUT) {
            satellitePositionsUseCase.getSatellitePositions(id)
                .flowOn(Dispatchers.IO)
                .firstOrNull()
        }
    }

    private suspend fun saveSatelliteDetails(details: SatelliteDetailsItemUiModel) =
        withContext(Dispatchers.IO) {
            dataStoreManager.saveSatelliteDetails(details)
        }

    private suspend fun updateSatellitePositionInRealTime(
        details: SatelliteDetailsItemUiModel,
        positions: List<PositionUiModel>
    ) {
        positions.forEach { currentPosition ->
            val updatedDetails = details.copy(
                position = "(${currentPosition.posX}, ${currentPosition.posY})"
            )

            _satelliteDetailsStateFlow.emit(State.Success(updatedDetails))
            saveSatelliteDetails(updatedDetails)

            delay(POSITIONS_INTERVAL)
        }
    }

    companion object {
        private const val POSITIONS_INTERVAL = 3000L
    }
}