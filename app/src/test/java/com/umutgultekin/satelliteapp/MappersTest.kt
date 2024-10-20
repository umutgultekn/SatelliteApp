package com.umutgultekin.satelliteapp


import com.umutgultekin.satelliteapp.data.remote.model.PositionApiModel
import com.umutgultekin.satelliteapp.data.remote.model.SatelliteDetailsItemApiModel
import com.umutgultekin.satelliteapp.data.remote.model.SatelliteItemApiModel
import com.umutgultekin.satelliteapp.data.remote.model.SatellitePositionsItemApiModel
import com.umutgultekin.satelliteapp.data.remote.model.SatellitesApiModel
import com.umutgultekin.satelliteapp.data.remote.model.toUiModel
import kotlin.test.Test
import kotlin.test.assertEquals


class MappersTest {

    @Test
    fun `test SatellitesApiModel toUiModel conversion`() {
        val apiModel = SatellitesApiModel(
            satellites = listOf(
                SatelliteItemApiModel(
                    id = 1L,
                    name = "Starlink",
                    active = true
                ),
                SatelliteItemApiModel(
                    id = 2L,
                    name = "Starlink-2",
                    active = false
                )
            )
        )

        val uiModel = apiModel.toUiModel()

        assertEquals(2, uiModel.satellites.size)
        assertEquals(1L, uiModel.satellites[0].id)
        assertEquals("Starlink", uiModel.satellites[0].name)
        assertEquals(true, uiModel.satellites[0].active)
        assertEquals(2L, uiModel.satellites[1].id)
        assertEquals("Starlink-2", uiModel.satellites[1].name)
        assertEquals(false, uiModel.satellites[1].active)
    }

    @Test
    fun `test SatellitePositionsItemApiModel toUiModel conversion`() {
        val apiModel = SatellitePositionsItemApiModel(
            positions = listOf(
                PositionApiModel(posX = 10.0, posY = 20.0),
                PositionApiModel(posX = 30.0, posY = 40.0)
            )
        )

        val uiModel = apiModel.toUiModel()

        assertEquals(2, uiModel.positions.size)
        assertEquals(10.0, uiModel.positions[0].posX)
        assertEquals(20.0, uiModel.positions[0].posY)
        assertEquals(30.0, uiModel.positions[1].posX)
        assertEquals(40.0, uiModel.positions[1].posY)
    }

    @Test
    fun `test SatelliteDetailsItemApiModel toUiModel conversion`() {
        val apiModel = SatelliteDetailsItemApiModel(
            id = 3L,
            costPerLaunch = 15000000,
            firstFlight = "1994-11-04",
            height = 10,
            mass = 5000
        )

        val uiModel = apiModel.toUiModel()

        assertEquals(3L, uiModel.id)
        assertEquals("15.000.000", uiModel.costPerLaunch)
        assertEquals("04.11.1994", uiModel.firstFlight)
        assertEquals("10/5000", uiModel.heightMass)
    }
}
