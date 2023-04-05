package com.entexy.gardenguru.domain.repository

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.plant.event.EventData

interface PredictionRepository {

    suspend fun fetchPlainUserPlants(): CloudResponse<List<PlantData>>

    suspend fun fetchUserEvents(plantIds: List<String>): CloudResponse<List<EventData>>

}