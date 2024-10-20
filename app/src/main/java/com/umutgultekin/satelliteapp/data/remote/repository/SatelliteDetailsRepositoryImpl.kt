package com.umutgultekin.satelliteapp.data.remote.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.umutgultekin.satelliteapp.common.Constants.SATELLITE_DETAILS_JSON
import com.umutgultekin.satelliteapp.data.remote.model.SatelliteDetailsItemApiModel
import com.umutgultekin.satelliteapp.domain.repository.SatelliteDetailsRepository
import javax.inject.Inject

class SatelliteDetailsRepositoryImpl @Inject constructor(private val context: Context) :
    SatelliteDetailsRepository {

    override suspend fun getSatelliteDetails(id: Long): SatelliteDetailsItemApiModel? {
        return runCatching {
            val json =
                context.assets.open(SATELLITE_DETAILS_JSON).bufferedReader().use { it.readText() }

            val data = Gson().fromJson<List<SatelliteDetailsItemApiModel>>(
                json,
                object : TypeToken<List<SatelliteDetailsItemApiModel>>() {}.type
            )
            data.find { it.id == id }

        }.getOrNull()
    }
}