package com.umutgultekin.satelliteapp.domain.repository

import com.umutgultekin.satelliteapp.data.remote.model.SatelliteDetailsItemApiModel

interface SatelliteDetailsRepository {

    suspend fun getSatelliteDetails(id: Long): SatelliteDetailsItemApiModel?
}