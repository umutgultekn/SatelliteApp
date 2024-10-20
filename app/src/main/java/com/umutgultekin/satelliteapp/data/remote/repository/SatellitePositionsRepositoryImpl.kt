package com.umutgultekin.satelliteapp.data.remote.repository

import android.content.Context
import com.google.gson.Gson
import com.umutgultekin.satelliteapp.common.Constants.SATELLITE_POSITIONS_JSON
import com.umutgultekin.satelliteapp.data.remote.model.SatellitePositionsApiModel
import com.umutgultekin.satelliteapp.data.remote.model.SatellitePositionsItemApiModel
import com.umutgultekin.satelliteapp.domain.repository.SatellitePositionsRepository
import javax.inject.Inject

class SatellitePositionsRepositoryImpl @Inject constructor(private val context: Context) :
    SatellitePositionsRepository {

    override suspend fun getSatellitePositions(id: Long): SatellitePositionsItemApiModel? {
        return runCatching {
            val json =
                context.assets.open(SATELLITE_POSITIONS_JSON).bufferedReader().use { it.readText() }

            val data = Gson().fromJson(json, SatellitePositionsApiModel::class.java)
            data.list?.find { it.id == id.toString() }

        }.getOrNull()
    }
}