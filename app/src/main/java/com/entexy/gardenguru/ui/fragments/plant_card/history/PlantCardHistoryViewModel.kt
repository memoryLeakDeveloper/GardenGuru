package com.entexy.gardenguru.ui.fragments.plant_card.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.entexy.gardenguru.data.plant.event.PlantEventsData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlantCardHistoryViewModel @Inject constructor() : ViewModel() {
    private val _plantEvents = MutableLiveData<PlantEventsData>().apply {
        value = null
    }
    val plantEvents: LiveData<PlantEventsData> = _plantEvents

}
