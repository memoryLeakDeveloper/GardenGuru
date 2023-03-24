package com.entexy.gardenguru.ui

import com.entexy.gardenguru.data.plant.event.EventData
import com.entexy.gardenguru.utils.toDmyString
import java.util.*

object EventMockData {

    val listData = listOf(
        EventData("event_id_213wqe", "Игорь", "НЕЗАБУДКА", EventData.Event.Watering, Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -1)
        }.toDmyString(), true),
        EventData("event_id_213sad", "Иван", "ЗАБУДКА", EventData.Event.Spraying, Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -1)
        }.toDmyString(), false),
        EventData("event_id_21321333", "Вася", "Роза", EventData.Event.Transfer, Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -1)
        }.toDmyString(), false),
        EventData("event_id_213", "Игорь", "НЕЗАБУДКА", EventData.Event.Watering, Calendar.getInstance().toDmyString(), true),
        EventData("event_id_234", "Иван", "ЗАБУДКА", EventData.Event.Watering, Calendar.getInstance().toDmyString(), false),
        EventData("event_id_345", "Игорь", "НЕЗАБУДКА", EventData.Event.Watering, Calendar.getInstance().toDmyString(), true),
        EventData("event_id_456", "Иван", "ЗАБУДКА", EventData.Event.Spraying, Calendar.getInstance().toDmyString(), false),

        EventData("event_id_21312", "Вася", "Роза", EventData.Event.Watering, Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, +1)
        }.toDmyString(), false),
    )
}