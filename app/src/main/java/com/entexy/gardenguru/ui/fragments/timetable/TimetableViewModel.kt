package com.entexy.gardenguru.ui.fragments.timetable

import android.util.Log
import androidx.lifecycle.ViewModel
import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.plant.event.EventData
import com.entexy.gardenguru.data.plant.event.PlantEventData
import com.entexy.gardenguru.domain.usecases.events.CompleteEventUseCase
import com.entexy.gardenguru.domain.usecases.events.FetchPlantEventsUseCase
import com.entexy.gardenguru.domain.usecases.events.PredictEventsUseCase
import com.entexy.gardenguru.utils.isDaysEquals
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TimetableViewModel @Inject constructor(
    private val fetchPlantEventsUseCase: FetchPlantEventsUseCase,
    private val predictEventsUseCase: PredictEventsUseCase,
    private val completeEventUseCase: CompleteEventUseCase
) : ViewModel() {

    suspend fun fetchPlantAndEvents() =
        fetchPlantEventsUseCase.perform()


    private var plants: List<PlantData>? = null
    private var events: MutableList<EventData>? = null
    fun calculateEvents(plants: List<PlantData>, events: List<EventData>): ArrayList<PlantEventData> {
        val predictedEvents = predictEventsUseCase.predictTimetableEvents(plants, events)
        this.plants = plants
        this.events = events.toMutableList()
        return PlantEventData.fromPlantsAndEvents(plants, predictedEvents)
    }

    fun updateEvent(changedEvent: EventData, insertedEvent: EventData?, deletedEvent: EventData?) {
        Log.d("qqqqq", "updateEvent changedEvent: $changedEvent, insertedEvent: $insertedEvent, deletedEvent: $deletedEvent")

        if (changedEvent.isCompleted && insertedEvent != null)
            events?.add(insertedEvent)
        else {
            events?.removeIf {
                it.eventTime == changedEvent.eventTime && it.eventType == changedEvent.eventType
            }
        }
        if (deletedEvent != null) {
            events?.removeIf {
                it.eventTime == deletedEvent.eventTime && it.eventType == deletedEvent.eventType
            }
        }
    }

    fun recalculateEvents(): ArrayList<PlantEventData> {
        val predictedEvents = predictEventsUseCase.predictTimetableEvents(
            plants ?: return arrayListOf(),
            events ?: return arrayListOf()
        )
        return PlantEventData.fromPlantsAndEvents(plants!!, predictedEvents)
    }


    suspend fun completeEvent(plantEvent: PlantEventData): Flow<CloudResponse<Pair<EventData?, EventData?>>> {
        val event = plantEvent.mapToEventData().apply { isCompleted = !isCompleted }

        var eventToDelete: EventData? = null
        if (event.isCompleted && event.eventTime.isDaysEquals(Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) })) {
            val sameEventToday =
                events?.find { it.eventTime.isDaysEquals(Calendar.getInstance()) && it.eventType == event.eventType && it.plantId == event.plantId }
            if (sameEventToday?.isCompleted == true) {
                eventToDelete = sameEventToday
            }
        }
        return completeEventUseCase.perform(event, eventToDelete)
    }
}
