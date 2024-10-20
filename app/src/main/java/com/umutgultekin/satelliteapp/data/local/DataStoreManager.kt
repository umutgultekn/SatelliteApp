package com.umutgultekin.satelliteapp.data.local


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.umutgultekin.satelliteapp.domain.model.SatelliteDetailsItemUiModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreManager @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    suspend fun saveSatelliteDetails(uiModel: SatelliteDetailsItemUiModel) {
        val json = Json.encodeToString(uiModel)
        context.appLocalDataStore.edit { prefs ->
            val key = stringPreferencesKey(uiModel.id.toString())
            prefs[key] = json
        }
    }

    fun getSatelliteDetails(id: Long): Flow<SatelliteDetailsItemUiModel?> {
        val key = stringPreferencesKey(id.toString())
        return context.appLocalDataStore.data
            .map { prefs ->
                prefs[key]?.let { json ->
                    Json.decodeFromString<SatelliteDetailsItemUiModel>(json)
                }
            }
    }


    companion object {
        private const val DATA_STORE_NAME = "data.store.name"

        private val Context.appLocalDataStore: DataStore<Preferences> by preferencesDataStore(
            name = DATA_STORE_NAME
        )
    }
}
