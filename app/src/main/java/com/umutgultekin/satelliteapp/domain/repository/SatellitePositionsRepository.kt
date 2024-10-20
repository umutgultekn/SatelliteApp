package com.umutgultekin.satelliteapp.domain.repository

import com.umutgultekin.satelliteapp.data.remote.model.SatellitePositionsItemApiModel

interface SatellitePositionsRepository {

    suspend fun getSatellitePositions(id: Long): SatellitePositionsItemApiModel?
}