package com.umutgultekin.satelliteapp.common

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.umutgultekin.satelliteapp.data.remote.model.SatelliteApiModel
import com.umutgultekin.satelliteapp.data.remote.model.SatellitesApiModel
import com.umutgultekin.satelliteapp.data.remote.model.toUiModel
import com.umutgultekin.satelliteapp.domain.model.SatellitesUiModel

object ReadDataFromAsset {

    fun readJsonFromAssets(context: Context, fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }

    fun parseJsonToModel(jsonString: String): SatellitesUiModel {
        val gson = Gson()
        return SatellitesApiModel(
            satellites = gson.fromJson(
                jsonString,
                object : TypeToken<List<SatelliteApiModel>>() {}.type
            )
        ).toUiModel()

    }
}