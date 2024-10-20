package com.umutgultekin.satelliteapp.di

import android.content.Context
import com.umutgultekin.satelliteapp.data.remote.repository.SatelliteDetailsRepositoryImpl
import com.umutgultekin.satelliteapp.data.remote.repository.SatellitePositionsRepositoryImpl
import com.umutgultekin.satelliteapp.data.remote.repository.SatellitesRepositoryImpl
import com.umutgultekin.satelliteapp.domain.repository.SatelliteDetailsRepository
import com.umutgultekin.satelliteapp.domain.repository.SatellitePositionsRepository
import com.umutgultekin.satelliteapp.domain.repository.SatellitesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSatellitesRepository(@ApplicationContext context: Context): SatellitesRepository {
        return SatellitesRepositoryImpl(context = context)
    }

    @Provides
    @Singleton
    fun provideSatelliteDetailsRepository(@ApplicationContext context: Context): SatelliteDetailsRepository {
        return SatelliteDetailsRepositoryImpl(context = context)
    }

    @Provides
    @Singleton
    fun provideSatellitePositionsRepository(@ApplicationContext context: Context): SatellitePositionsRepository {
        return SatellitePositionsRepositoryImpl(context = context)
    }
}