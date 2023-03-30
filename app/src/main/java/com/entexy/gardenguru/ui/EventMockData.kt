package com.entexy.gardenguru.ui

import com.entexy.gardenguru.data.plant.event.EventData
import java.util.*

object EventMockData {

    val timetableListData = listOf(
        EventData("event_id_213wqe", true, Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -10)
        }, EventData.EventType.Create),
        EventData("event_id_213wqe1", false, Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -8)
        }, EventData.EventType.Watering),
        EventData("event_id_213wqe1", true, Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -7)
        }, EventData.EventType.Watering),
        EventData("event_id_213wqe2", true, Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -7)
        }, EventData.EventType.Spraying),
        EventData("event_id_213wqe4", true, Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -4)
        }, EventData.EventType.Transfer),
        EventData("event_id_213wqe5", true, Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -3)
        }, EventData.EventType.Feeding),
        EventData("event_id_213wqe7", true, Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -2)
        }, EventData.EventType.Watering),
        EventData("event_id_213wqe8", true, Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -1)
        }, EventData.EventType.Spraying),
        )
}