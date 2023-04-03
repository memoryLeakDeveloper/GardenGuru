package com.entexy.gardenguru.ui.fragments.timetable

import androidx.lifecycle.ViewModel
import com.entexy.gardenguru.data.plant.event.PlantEventData
import com.entexy.gardenguru.domain.usecases.events.CompleteEventUseCase
import com.entexy.gardenguru.domain.usecases.events.FetchPlantEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TimetableViewModel @Inject constructor(
    private val fetchPlantEventsUseCase: FetchPlantEventsUseCase,
    private val completeEventUseCase: CompleteEventUseCase
) : ViewModel() {

    suspend fun fetchEvents() = fetchPlantEventsUseCase.perform()

    suspend fun completeEvent(event: PlantEventData) = completeEventUseCase.perform(event)

}