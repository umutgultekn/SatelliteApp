package com.umutgultekin.satelliteapp.domain.use_case


import com.umutgultekin.satelliteapp.data.remote.model.toUiModel
import com.umutgultekin.satelliteapp.domain.model.SatelliteDetailsItemUiModel
import com.umutgultekin.satelliteapp.domain.repository.SatelliteDetailsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SatelliteDetailsUseCase @Inject constructor(
    private val satelliteDetailsRepository: SatelliteDetailsRepository
) {
    fun getSatelliteDetails(id: Long): Flow<SatelliteDetailsItemUiModel?> = flow {
        emit(satelliteDetailsRepository.getSatelliteDetails(id)?.toUiModel())
    }.flowOn(Dispatchers.IO)
}