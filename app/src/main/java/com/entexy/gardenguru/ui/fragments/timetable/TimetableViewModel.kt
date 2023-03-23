package com.entexy.gardenguru.ui.fragments.timetable

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.entexy.gardenguru.data.plant.CareComplexity
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.plant.SunRelation
import com.entexy.gardenguru.data.plant.benefit.BenefitData
import com.entexy.gardenguru.data.plant.event.EventData
import com.entexy.gardenguru.data.plant.event.PlantEventsData
import com.entexy.gardenguru.domain.repository.PlantRepository
import com.entexy.gardenguru.utils.toDmyString
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TimetableViewModel @Inject constructor(private val plantRepository: PlantRepository) : ViewModel() {
    private val _events = MutableLiveData<ArrayList<PlantEventsData>>().apply {
        value = arrayListOf(
            PlantEventsData(
                PlantData(
                    "id",
                    "НЕЗАБУДКА",
                    "https://cdn.pixabay.com/photo/2015/04/19/08/33/flower-729512_960_720.jpg",
                    CareComplexity.Easy,
                    "НЕЗАБУДКА DESC",
                    SunRelation.DirectLight,
                    null,
                    null,
                    arrayListOf(BenefitData("qweqweqweqweqwe", "Плюсы определенно есть")),
                    "СЕГОДНЯ ИЛИ ЗАВТРА НАДО ОБЯЗАТЕЛЬНО",
                    Date(),
                    3,
                    4,
                    5,
                    6,
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
            )
        )
    }
    val events: LiveData<ArrayList<PlantEventsData>> = _events


    fun getEventForDay(calendar: Calendar): PlantEventsData? {
        val dateString = calendar.toDmyString()
        return events.value?.find { plantEventData ->
            plantEventData.events.find { it.dateDMY == dateString } != null
        }
    }

    suspend fun testGetPlant(idPlant: String) {
        plantRepository.searchPlantByVarietyCode(listOf(idPlant))
    }

    suspend fun testGetPlant1(idPlant: String) {
        plantRepository.searchPlantByName(idPlant)
    }
}
