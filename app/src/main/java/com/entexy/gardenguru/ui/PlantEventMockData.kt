package com.entexy.gardenguru.ui

import com.entexy.gardenguru.data.plant.CareComplexity
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.plant.SunRelation
import com.entexy.gardenguru.data.plant.benefit.BenefitData
import com.entexy.gardenguru.data.plant.event.EventData
import com.entexy.gardenguru.data.plant.event.PlantEventsData
import com.entexy.gardenguru.utils.toDmyString
import java.util.*

object PlantEventMockData {

    val plant = PlantEventsData(
        PlantData(
            "id",
            "Незабудка",
            "https://cdn.pixabay.com/photo/2015/04/19/08/33/flower-729512_960_720.jpg",
            "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.psychologies.ru%2Fwellbeing%2Futki-naprolet%2F&psig=AOvVaw2d9XJrWS19XLpUAPmB9hEu&ust=1679754733258000&source=images&cd=vfe&ved=0CBAQjRxqFwoTCJjW5pPk9P0CFQAAAAAdAAAAABAE",
            "ЗДАРОВА",
            CareComplexity.Easy,
            "НЕЗАБУДКА DESC",
            SunRelation.DirectLight,
            null,
            emptyList(),
            arrayListOf(BenefitData("qweqweqweqweqwe", "Плюсы определенно есть")),
            "СЕГОДНЯ ИЛИ ЗАВТРА НАДО ОБЯЗАТЕЛЬНО",
            Date(),
            3,
            4,
            5,
            6,
        ),
        arrayListOf(
            EventData("Игорь", "НЕЗАБУДКА", EventData.Event.Watering, Calendar.getInstance().apply {
                add(Calendar.DAY_OF_YEAR, -1)
            }.toDmyString(), true),
            EventData("Иван", "ЗАБУДКА", EventData.Event.Spraying, Calendar.getInstance().apply {
                add(Calendar.DAY_OF_YEAR, -1)
            }.toDmyString(), false),
            EventData("Вася", "Роза", EventData.Event.Transfer, Calendar.getInstance().apply {
                add(Calendar.DAY_OF_YEAR, -1)
            }.toDmyString(), false)
        )
    )

}