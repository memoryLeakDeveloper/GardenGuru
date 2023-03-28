package com.entexy.gardenguru.ui

import com.entexy.gardenguru.data.plant.event.EventData
import com.entexy.gardenguru.data.plant.event.PlantEventData
import com.entexy.gardenguru.utils.toDmyString
import java.util.*

object PlantEventMockData {

    val timetableListData = listOf(
        PlantEventData("event_id_213wqe", "Игорь", "НЕЗАБУДКА", EventData.EventType.Watering, Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -1)
        }.toDmyString(), true),
        PlantEventData("event_id_213sad", "Иван", "ЗАБУДКА", EventData.EventType.Spraying, Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -1)
        }.toDmyString(), false),
        PlantEventData("event_id_21321333", "Вася", "Роза", EventData.EventType.Transfer, Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -1)
        }.toDmyString(), false),
        PlantEventData("event_id_213", "Игорь", "НЕЗАБУДКА", EventData.EventType.Watering, Calendar.getInstance().toDmyString(), true),
        PlantEventData("event_id_234", "Иван", "ЗАБУДКА", EventData.EventType.Watering, Calendar.getInstance().toDmyString(), false),
        PlantEventData("event_id_345", "Игорь", "НЕЗАБУДКА", EventData.EventType.Watering, Calendar.getInstance().toDmyString(), true),
        PlantEventData("event_id_456", "Иван", "ЗАБУДКА", EventData.EventType.Spraying, Calendar.getInstance().toDmyString(), false),

        PlantEventData("event_id_21312", "Вася", "Роза", EventData.EventType.Watering, Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, +1)
        }.toDmyString(), false),
    )
}