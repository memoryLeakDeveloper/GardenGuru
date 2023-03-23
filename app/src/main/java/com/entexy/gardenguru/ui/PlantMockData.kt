package com.entexy.gardenguru.ui

import com.entexy.gardenguru.data.plant.CareComplexity
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.plant.SunRelation
import com.entexy.gardenguru.data.plant.benefit.BenefitData
import java.util.*

object PlantMockData {
    val plant =
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
        )
}