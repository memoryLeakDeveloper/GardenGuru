package com.entexy.gardenguru.ui.fragments.plant_card.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.entexy.gardenguru.data.plant.CareComplexity
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.plant.SunRelation
import com.entexy.gardenguru.data.plant.benefit.BenefitData
import com.entexy.gardenguru.data.plant.event.EventData
import com.entexy.gardenguru.data.plant.event.PlantEventsData
import com.entexy.gardenguru.utils.toDmyString
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PlantCardHistoryViewModel @Inject constructor() : ViewModel() {
    private val _plantEvents = MutableLiveData<PlantEventsData>().apply {
        value =
            PlantEventsData(
                PlantData(
                    "qwwqeew",
                    "НЕЗАБУДКА",
                    "https://cdn.pixabay.com/photo/2015/04/19/08/33/flower-729512_960_720.jpg",
                    CareComplexity.Easy,
                    "НЕЗАБУДКА DESC",
                    SunRelation.DirectLight,
                    null,
                    null,
                    arrayListOf(BenefitData("qweqweqweqweqwe", "qwpoqfwepofqmvw")),
                    "СЕГОДНЯ ИЛИ ЗАВТРА НАДО ОБЯЗАТЕЛЬНО",
                    Date(),
                    3,
                    4,
                    5,
                    6,
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
