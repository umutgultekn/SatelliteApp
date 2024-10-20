package com.umutgultekin.satelliteapp.domain.use_case


import com.umutgultekin.satelliteapp.data.remote.model.toUiModel
import com.umutgultekin.satelliteapp.domain.model.SatellitesUiModel
import com.umutgultekin.satelliteapp.domain.repository.SatellitesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SatellitesUseCase @Inject constructor(
    private val satellitesRepository: SatellitesRepository
) {

    fun getSatellites(): Flow<SatellitesUiModel?> = flow {
        emit(satellitesRepository.getSatellites()?.toUiModel())
    }.flowOn(Dispatchers.IO)
}