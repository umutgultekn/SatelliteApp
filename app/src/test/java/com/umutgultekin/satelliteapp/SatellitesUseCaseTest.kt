package com.umutgultekin.satelliteapp

import com.umutgultekin.satelliteapp.data.remote.model.SatelliteItemApiModel
import com.umutgultekin.satelliteapp.data.remote.model.SatellitesApiModel
import com.umutgultekin.satelliteapp.data.remote.model.toUiModel
import com.umutgultekin.satelliteapp.domain.repository.SatellitesRepository
import com.umutgultekin.satelliteapp.domain.use_case.SatellitesUseCase
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
class SatellitesUseCaseTest {

    private lateinit var satellitesUseCase: SatellitesUseCase
    private val satellitesRepository: SatellitesRepository = mock(SatellitesRepository::class.java)
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var testScope: TestScope


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        testScope = TestScope(testDispatcher)
        satellitesUseCase = SatellitesUseCase(satellitesRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test getSatellites returns data from repository`() = runBlocking {
        val expectedApiModel = SatellitesApiModel(
            listOf(
                SatelliteItemApiModel(
                    id = 1L,
                    active = true,
                    name = "Starlink"
                )
            )
        )

        whenever(satellitesRepository.getSatellites()).thenReturn(expectedApiModel)

        val flow = satellitesUseCase.getSatellites()

        flow.collect { actualSatellites ->
            val expectedSatellites = expectedApiModel.toUiModel()
            assertEquals(expectedSatellites, actualSatellites)
        }
    }

    @Test
    fun `test getSatellites returns null when repository returns null`() = runBlocking {
        whenever(satellitesRepository.getSatellites()).thenReturn(null)

        val flow = satellitesUseCase.getSatellites()
        flow.collect { actualSatellites ->
            assertEquals(null, actualSatellites)
        }
    }
}
