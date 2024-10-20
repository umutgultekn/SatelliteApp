package com.umutgultekin.satelliteapp.domain.repository

import com.umutgultekin.satelliteapp.data.remote.model.SatellitesApiModel

interface SatellitesRepository {

    suspend fun getSatellites(): SatellitesApiModel?
}