package com.entexy.gardenguru.ui

import com.entexy.gardenguru.data.plant.event.EventData
import java.util.*

object EventMockData {

    val timetableListData = listOf(
        EventData("event_id_213wqe", true, Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -10)
        }.time, EventData.EventType.Create),
        EventData("event_id_213wqe1", false, Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -8)
        }.time, EventData.EventType.Watering),
        EventData("event_id_213wqe1", true, Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -7)
        }.time, EventData.EventType.Watering),
        EventData("event_id_213wqe2", true, Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -7)
        }.time, EventData.EventType.Spraying),
        EventData("event_id_213wqe4", true, Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -4)
        }.time, EventData.EventType.Transfer),
        EventData("event_id_213wqe5", true, Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -3)
        }.time, EventData.EventType.TopDressing),
        EventData("event_id_213wqe7", true, Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -2)
        }.time, EventData.EventType.Watering),
        EventData("event_id_213wqe8", true, Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -1)
        }.time, EventData.EventType.Spraying),
        )
}