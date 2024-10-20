package com.umutgultekin.satelliteapp

import com.umutgultekin.satelliteapp.data.remote.model.SatelliteDetailsItemApiModel
import com.umutgultekin.satelliteapp.data.remote.model.toUiModel
import com.umutgultekin.satelliteapp.domain.repository.SatelliteDetailsRepository
import com.umutgultekin.satelliteapp.domain.use_case.SatelliteDetailsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class SatelliteDetailsUseCaseTest {

    private lateinit var satelliteDetailsUseCase: SatelliteDetailsUseCase
    private val satelliteDetailsRepository: SatelliteDetailsRepository =
        mock(SatelliteDetailsRepository::class.java)
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var testScope: TestScope


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        testScope = TestScope(testDispatcher)
        satelliteDetailsUseCase = SatelliteDetailsUseCase(satelliteDetailsRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test getSatelliteDetails returns data from repository`() = runBlocking {
        val expectedApiModel = SatelliteDetailsItemApiModel(
            id = 1L,
            height = 100,
            mass = 200,
            firstFlight = "2024-10-20",
            costPerLaunch = 1000
        )

        whenever(satelliteDetailsRepository.getSatelliteDetails(1)).thenReturn(expectedApiModel)

        val flow = satelliteDetailsUseCase.getSatelliteDetails(1)

        flow.collect { actualSatellites ->
            val expectedSatellites = expectedApiModel.toUiModel()
            assertEquals(expectedSatellites, actualSatellites)
        }
    }

    @Test
    fun `test getSatelliteDetails returns null when repository returns null`() = runBlocking {
        whenever(satelliteDetailsRepository.getSatelliteDetails(1)).thenReturn(null)

        val flow = satelliteDetailsUseCase.getSatelliteDetails(1)
        flow.collect { actualSatellites ->
            assertEquals(null, actualSatellites)
        }
    }
}
