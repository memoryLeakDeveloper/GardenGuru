package com.example.gardenguru.ui.fragments.timetable

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gardenguru.data.event.EventData
import com.example.gardenguru.data.event.PlantEventsData
import com.example.gardenguru.data.media.PhotoData
import com.example.gardenguru.data.plant.PlantData
import com.example.gardenguru.data.sun.relation.SunRelationData
import com.example.gardenguru.utils.Extensions.toDmyString
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class TimetableViewModel @Inject constructor() : ViewModel() {
    private val _events = MutableLiveData<ArrayList<PlantEventsData>>().apply {
        value = arrayListOf(
            PlantEventsData(
                PlantData(
                    "0",
                    0,
                    "Иван",
                    null,
                    "",
                    (
                        PhotoData(
                            "0",
                            "https://flowers.evroopt.by/wp-content/uploads/2019/03/kaktus4_800h800_fon.png",
                            "https://flowers.evroopt.by/wp-content/uploads/2019/03/kaktus4_800h800_fon.png",
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
                    EventData(EventData.Event.Watering, Calendar.getInstance().apply {
                        add(Calendar.DAY_OF_YEAR, -1)
                    }.toDmyString(), true),
                    EventData(EventData.Event.Spraying, Calendar.getInstance().apply {
                        add(Calendar.DAY_OF_YEAR, -1)
                    }.toDmyString(), false),
                    EventData(EventData.Event.Transfer, Calendar.getInstance().apply {
                        add(Calendar.DAY_OF_YEAR, -1)
                    }.toDmyString(), false)
                )
            ),
            PlantEventsData(
                PlantData(
                    "0",
                    0,
                    "Игорь",
                    null,
                    "",
                    (
                        PhotoData(
                            "0",
                            "https://images.deal.by/268832257_tantsuyuschij-kaktus-povtoryashka.jpg",
                            "https://images.deal.by/268832257_tantsuyuschij-kaktus-povtoryashka.jpg",
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
                        EventData.Event.Watering,
                        Calendar.getInstance().apply {
                            add(Calendar.DAY_OF_YEAR, -2)
                        }.toDmyString(),
                        true,
                    ),
                    EventData(
                        EventData.Event.TopDressing,
                        Calendar.getInstance().apply {
                            add(Calendar.DAY_OF_YEAR, -2)
                        }.toDmyString(),
                        true,
                    ),
                    EventData(EventData.Event.Transfer, Calendar.getInstance().apply {
                        add(Calendar.DAY_OF_YEAR, -2)
                    }.toDmyString(), true)
                )
            ),
            PlantEventsData(
                PlantData(
                    "0",
                    0,
                    "Степан",
                    null,
                    "",
                    (
                            PhotoData(
                            "0",
                            "https://rastenievod.com/wp-content/uploads/2016/09/2-51.jpg",
                            "https://rastenievod.com/wp-content/uploads/2016/09/2-51.jpg"
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
                    EventData(EventData.Event.Watering, Calendar.getInstance().apply {
                        add(Calendar.DAY_OF_YEAR, -4)
                    }.toDmyString(), false),
                    EventData(EventData.Event.Spraying, Calendar.getInstance().apply {
                        add(Calendar.DAY_OF_YEAR, -4)
                    }.toDmyString(), false),
                    EventData(EventData.Event.Transfer, Calendar.getInstance().apply {
                        add(Calendar.DAY_OF_YEAR, -4)
                    }.toDmyString(), false)
                )
            ),
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
