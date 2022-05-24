package com.example.gardenguru.ui.timetable

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
                    "",
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
                Calendar.getInstance().apply {
                    add(Calendar.DAY_OF_YEAR, -1)
                }.toDmyString(),
                arrayListOf(
                    EventData(EventData.Event.Watering, true),
                    EventData(EventData.Event.Spraying, false),
                    EventData(EventData.Event.Transfer, false)
                )
            ),
            PlantEventsData(
                PlantData(
                    "0",
                    0,
                    "Игорь",
                    "",
                    arrayListOf(
                        PhotoData(
                            "0",
                            "https://images.deal.by/268832257_tantsuyuschij-kaktus-povtoryashka.jpg"
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
                ), Calendar.getInstance().apply {
                    add(Calendar.DAY_OF_YEAR, -2)
                }.toDmyString(),
                arrayListOf(
                    EventData(EventData.Event.Watering, true,),
                    EventData(EventData.Event.TopDressing, true, ),
                    EventData(EventData.Event.Transfer, true)
                )
            ),
            PlantEventsData(
                PlantData(
                    "0",
                    0,
                    "Степан",
                    "",
                    arrayListOf(PhotoData("0", "https://rastenievod.com/wp-content/uploads/2016/09/2-51.jpg")),
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
                ), Calendar.getInstance().apply {
                    add(Calendar.DAY_OF_YEAR, -4)
                }.toDmyString(),
                arrayListOf(
                    EventData(EventData.Event.Watering, false),
                    EventData(EventData.Event.Spraying, false),
                    EventData(EventData.Event.Transfer, false)
                )
            ),
        )
    }
    val events: LiveData<ArrayList<PlantEventsData>> = _events


    fun getEventForDay(calendar: Calendar): PlantEventsData? {
        val dateString = calendar.toDmyString()
        return events.value?.find {
            it.date == dateString
        }
    }
}
