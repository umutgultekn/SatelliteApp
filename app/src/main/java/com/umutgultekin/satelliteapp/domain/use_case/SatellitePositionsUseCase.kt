package com.umutgultekin.satelliteapp.domain.use_case


import com.umutgultekin.satelliteapp.data.remote.model.toUiModel
import com.umutgultekin.satelliteapp.domain.model.SatellitePositionsItemUiModel
import com.umutgultekin.satelliteapp.domain.repository.SatellitePositionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SatellitePositionsUseCase @Inject constructor(
    private val satellitePositionsRepository: SatellitePositionsRepository
) {
    fun getSatellitePositions(id: Long): Flow<SatellitePositionsItemUiModel?> = flow {
        emit(satellitePositionsRepository.getSatellitePositions(id)?.toUiModel())
    }.flowOn(Dispatchers.IO)
}