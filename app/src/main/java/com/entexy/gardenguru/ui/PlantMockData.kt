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
            "Незабудка",
            "https://cdn.pixabay.com/photo/2015/04/19/08/33/flower-729512_960_720.jpg",
            "https://n1s1.hsmedia.ru/52/bc/05/52bc058ca615dd1193673a3d7be39e49/690x460_0xc0a8392b_949800641501063422.jpeg",
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
        )
}