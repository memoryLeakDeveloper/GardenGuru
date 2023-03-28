package com.entexy.gardenguru.ui

import com.entexy.gardenguru.data.plant.CareComplexity
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.plant.SunRelation
import com.entexy.gardenguru.data.plant.benefit.BenefitData
import com.entexy.gardenguru.data.plant.pest.PestData
import com.entexy.gardenguru.data.plant.reproduction.Reproduction
import java.util.*

object PlantMockData {
    val plant =
        PlantData(
            "id",
            "Незабудка",
            "https://cdn.pixabay.com/photo/2015/04/19/08/33/flower-729512_960_720.jpg",
            "https://www.poison.org/-/media/images/shared/articles/plants/dumbane-dieffenbachia.jpg?h=336&w=448&la=en&hash=E7C4CE9A4E588C8EFA0B79533EC3E658",
            "Петя",
            null,
            CareComplexity.Easy,
            "НЕЗАБУДКА DESC",
            SunRelation.DirectLight,
            arrayListOf(PestData("qweqweqwe", "Жук Олень")),
            arrayListOf(Reproduction.Seeds),
            arrayListOf(BenefitData("qweqweqweqweqwe", "Плюсы определенно есть")),
            "СЕГОДНЯ ИЛИ ЗАВТРА НАДО ОБЯЗАТЕЛЬНО",
            Date(),
            3,
            4,
            5,
            6,
        )
}