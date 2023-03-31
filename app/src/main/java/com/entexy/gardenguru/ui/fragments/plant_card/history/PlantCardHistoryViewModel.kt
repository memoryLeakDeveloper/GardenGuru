package com.entexy.gardenguru.ui.fragments.plant_card.history

import androidx.lifecycle.ViewModel
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.plant.event.EventData
import com.entexy.gardenguru.domain.usecases.events.PredictEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlantCardHistoryViewModel @Inject constructor(
    private val predictEventsUseCase: PredictEventsUseCase
) : ViewModel() {
    fun predictEvents(plantData: PlantData, events: ArrayList<EventData>) =
        predictEventsUseCase.predictPastEvents(plantData, events)
}
