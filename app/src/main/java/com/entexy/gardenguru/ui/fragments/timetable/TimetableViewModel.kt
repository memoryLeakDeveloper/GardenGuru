package com.entexy.gardenguru.ui.fragments.timetable

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.entexy.gardenguru.data.plant.event.PlantEventsData
import com.entexy.gardenguru.ui.PlantEventMockData
import com.entexy.gardenguru.utils.toDmyString
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TimetableViewModel @Inject constructor() : ViewModel() {
    private val _events = MutableLiveData<ArrayList<PlantEventsData>>().apply {
        value = arrayListOf(
            PlantEventMockData.plant
        )
    }
    val events: LiveData<ArrayList<PlantEventsData>> = _events


    fun getEventForDay(calendar: Calendar): PlantEventsData? {
        val dateString = calendar.toDmyString()
        return events.value?.find { plantEventData ->
            plantEventData.events.find { it.dateDMY == dateString } != null
        }
    }

}
