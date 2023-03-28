package com.entexy.gardenguru.ui.fragments.plant_card.history

import androidx.lifecycle.ViewModel
import com.entexy.gardenguru.domain.usecases.events.FetchEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlantCardViewModel @Inject constructor(
    private val fetchEventsUseCase: FetchEventsUseCase
) : ViewModel() {
    suspend fun fetchEvents(plantId: String) = fetchEventsUseCase.fetchPlant(plantId)
}
