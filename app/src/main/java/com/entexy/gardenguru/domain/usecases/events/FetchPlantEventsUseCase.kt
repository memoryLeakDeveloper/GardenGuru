package com.entexy.gardenguru.domain.usecases.events

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.plant.event.PlantEventData
import com.entexy.gardenguru.domain.repository.EventRepository
import com.entexy.gardenguru.domain.repository.PlantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchPlantEventsUseCase @Inject constructor(
    private val plantRepository: PlantRepository,
    private val eventRepository: EventRepository,
    private val predictEventsUseCase: PredictEventsUseCase
) {
    suspend fun perform(): Flow<CloudResponse<ArrayList<PlantEventData>>> = flow {
        emit(CloudResponse.Loading())
        val plants = plantRepository.fetchPlainUserPlants()
        if (plants is CloudResponse.Success) {
            val events = eventRepository.fetchUserEvents(plants.result.map { it.id })

            if (events is CloudResponse.Success) {
                val predictedEvents = predictEventsUseCase.predictTimetableEvents(plants.result, events.result)

                emit(CloudResponse.Success(PlantEventData.fromPlantsAndEvents(plants.result, predictedEvents)))
            } else emit(CloudResponse.Error((events as? CloudResponse.Error)?.exception))

        } else emit(CloudResponse.Error((plants as? CloudResponse.Error)?.exception))
    }.catch {
        emit(CloudResponse.Error(it))
    }
}