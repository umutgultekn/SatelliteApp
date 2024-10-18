package com.umutgultekin.satelliteapp.domain.use_case


import com.umutgultekin.satelliteapp.data.remote.model.toUiModel
import com.umutgultekin.satelliteapp.domain.model.SatellitesUiModel
import com.umutgultekin.satelliteapp.domain.repository.SatellitesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SatellitesUseCase @Inject constructor(
    private val satellitesRepository: SatellitesRepository
) {

    suspend fun getSatellites(): SatellitesUiModel {
        return satellitesRepository.getSatellites().toUiModel()
    }

}