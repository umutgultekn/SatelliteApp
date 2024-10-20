package com.umutgultekin.satelliteapp

import com.umutgultekin.satelliteapp.data.remote.model.PositionApiModel
import com.umutgultekin.satelliteapp.data.remote.model.SatellitePositionsItemApiModel
import com.umutgultekin.satelliteapp.data.remote.model.toUiModel
import com.umutgultekin.satelliteapp.domain.repository.SatellitePositionsRepository
import com.umutgultekin.satelliteapp.domain.use_case.SatellitePositionsUseCase
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
class SatellitePositionsUseCaseTest {

    private lateinit var satellitePositionsUseCase: SatellitePositionsUseCase
    private val satellitePositionsRepository: SatellitePositionsRepository =
        mock(SatellitePositionsRepository::class.java)
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var testScope: TestScope


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        testScope = TestScope(testDispatcher)
        satellitePositionsUseCase = SatellitePositionsUseCase(satellitePositionsRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test getSatellitePositions returns data from repository`() = runBlocking {
        val expectedApiModel = SatellitePositionsItemApiModel(
            id = "1",
            positions = listOf(PositionApiModel(posX = 1.0, posY = 2.0))
        )

        whenever(satellitePositionsRepository.getSatellitePositions(1)).thenReturn(expectedApiModel)

        val flow = satellitePositionsUseCase.getSatellitePositions(1)

        flow.collect { actualSatellites ->
            val expectedSatellites = expectedApiModel.toUiModel()
            assertEquals(expectedSatellites, actualSatellites)
        }
    }

    @Test
    fun `test getSatellitePositions returns null when repository returns null`() = runBlocking {
        whenever(satellitePositionsRepository.getSatellitePositions(1)).thenReturn(null)

        val flow = satellitePositionsUseCase.getSatellitePositions(1)
        flow.collect { actualSatellites ->
            assertEquals(null, actualSatellites)
        }
    }
}
