package com.umutgultekin.satelliteapp.data.remote.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.umutgultekin.satelliteapp.common.Constants.SATELLITE_LIST_JSON
import com.umutgultekin.satelliteapp.data.remote.model.SatelliteItemApiModel
import com.umutgultekin.satelliteapp.data.remote.model.SatellitesApiModel
import com.umutgultekin.satelliteapp.domain.repository.SatellitesRepository
import javax.inject.Inject

class SatellitesRepositoryImpl @Inject constructor(private val context: Context) :
    SatellitesRepository {

    override suspend fun getSatellites(): SatellitesApiModel? {
        return runCatching {
            val json =
                context.assets.open(SATELLITE_LIST_JSON).bufferedReader().use { it.readText() }

            SatellitesApiModel(
                satellites = Gson().fromJson(
                    json,
                    object : TypeToken<List<SatelliteItemApiModel>>() {}.type
                )
            )
        }.getOrNull()

    }
}