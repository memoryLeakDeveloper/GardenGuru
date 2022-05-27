package com.example.gardenguru.ui.plant_card.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gardenguru.data.event.EventData
import com.example.gardenguru.data.event.PlantEventsData
import com.example.gardenguru.data.photo.PhotoData
import com.example.gardenguru.data.plant.PlantData
import com.example.gardenguru.data.sun.relation.SunRelationData
import com.example.gardenguru.utils.Extensions.toDmyString
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PlantCardHistoryViewModel @Inject constructor() : ViewModel() {
    private val _plantEvents = MutableLiveData<PlantEventsData>().apply {
        value =
            PlantEventsData(
                PlantData(
                    "0",
                    0,
                    "Иван",
                    "Кактус",
                    arrayListOf(
                        PhotoData(
                            "0",
                            "https://flowers.evroopt.by/wp-content/uploads/2019/03/kaktus4_800h800_fon.png"
                        )
                    ),
                    SunRelationData(0, ""),
                    arrayListOf(),
                    arrayListOf(),
                    arrayListOf(),
                    "",
                    "",
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0
                ),
                arrayListOf(
                    EventData(
                        EventData.Event.Spraying, Calendar.getInstance().apply {
                            add(Calendar.DAY_OF_YEAR, -7)
                        }.toDmyString(), true, timeOfCompletionHHMM = "14:30"
                    ),
                    EventData(
                        EventData.Event.TopDressing, Calendar.getInstance().apply {
                            add(Calendar.DAY_OF_YEAR, -5)
                        }.toDmyString(), true, timeOfCompletionHHMM = "14:00"
                    ),
                    EventData(
                        EventData.Event.Spraying, Calendar.getInstance().apply {
                            add(Calendar.DAY_OF_YEAR, -4)
                        }.toDmyString(), false, timeOfCompletionHHMM = "14:30"
                    ),
                    EventData(
                        EventData.Event.Watering, Calendar.getInstance().apply {
                            add(Calendar.DAY_OF_YEAR, -2)
                        }.toDmyString(), true, executor = "ecat@gmail.com", "10:30"
                    ),
                    EventData(
                        EventData.Event.Spraying, Calendar.getInstance().apply {
                            add(Calendar.DAY_OF_YEAR, -1)
                        }.toDmyString(), true
                    ),
                )
            )
    }
    val plantEvents: LiveData<PlantEventsData> = _plantEvents

}
