package com.entexy.gardenguru.domain.usecases.events

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.plant.event.EventData
import com.entexy.gardenguru.domain.repository.PredictionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchPlantEventsUseCase @Inject constructor(
    private val repository: PredictionRepository,
) {
    suspend fun perform(): Flow<CloudResponse<Pair<List<PlantData>, List<EventData>>>> = flow {
        emit(CloudResponse.Loading())
        val plants = repository.fetchPlainUserPlants()
        if (plants is CloudResponse.Success) {
            if (plants.result.isNotEmpty()) {
                val events = repository.fetchUserEvents(plants.result.map { it.id })

                if (events is CloudResponse.Success) {
                    emit(CloudResponse.Success(plants.result to events.result))
                } else emit(CloudResponse.Error((events as? CloudResponse.Error)?.exception))
            } else emit(CloudResponse.Success(emptyList<PlantData>() to listOf()))
        } else emit(CloudResponse.Error((plants as? CloudResponse.Error)?.exception))
    }.catch {
        emit(CloudResponse.Error(it))
    }
}