package com.entexy.gardenguru.data.prediction

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.plant.cloud.PlantCloudDataSource
import com.entexy.gardenguru.data.plant.event.EventData
import com.entexy.gardenguru.data.plant.event.cloud.FetchUserEventsDataSource
import com.entexy.gardenguru.data.plant.mapToData
import com.entexy.gardenguru.domain.repository.PredictionRepository
import javax.inject.Inject

class PredictionRepositoryImpl @Inject constructor(
    private val plantSource: PlantCloudDataSource,
    private val fetchUserEventsDataSource: FetchUserEventsDataSource
) : PredictionRepository {

    //returns a list of plants with empty benefits, pests
    override suspend fun fetchPlainUserPlants(): CloudResponse<List<PlantData>> {
        val plantDataCloud = plantSource.fetchPlants()

        return if (plantDataCloud is CloudResponse.Success) {
            val result = mutableListOf<PlantData>()
            plantDataCloud.result.forEach {
                val plantData = it.mapToData()
                if (plantData != null)
                    result.add(plantData)
            }
            CloudResponse.Success(result)
        } else CloudResponse.Error(null)
    }

    override suspend fun fetchUserEvents(plantIds: List<String>): CloudResponse<List<EventData>> {
        return fetchUserEventsDataSource.fetchEvents(plantIds)
    }

}