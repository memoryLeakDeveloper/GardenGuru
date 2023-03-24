package com.entexy.gardenguru.ui.fragments.timetable

import androidx.lifecycle.ViewModel
import com.entexy.gardenguru.data.plant.event.EventData
import com.entexy.gardenguru.domain.usecases.events.CompleteEventUseCase
import com.entexy.gardenguru.domain.usecases.events.FetchEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TimetableViewModel @Inject constructor(
    private val fetchEventsUseCase: FetchEventsUseCase,
    private val completeEventUseCase: CompleteEventUseCase
) : ViewModel() {

    suspend fun fetchEvents() = fetchEventsUseCase.perform()

    suspend fun completeEvent(event: EventData) = completeEventUseCase.perform(event)

}
