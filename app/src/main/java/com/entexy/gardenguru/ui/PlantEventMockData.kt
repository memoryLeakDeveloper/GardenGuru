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