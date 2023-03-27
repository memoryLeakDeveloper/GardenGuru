package com.entexy.gardenguru.ui

import com.entexy.gardenguru.data.plant.CareComplexity
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.plant.SunRelation
import com.entexy.gardenguru.data.plant.benefit.BenefitData
import com.entexy.gardenguru.data.plant.cloud.PlantCloud
import com.entexy.gardenguru.data.plant.pest.PestData
import com.entexy.gardenguru.data.plant.reproduction.Reproduction
import com.google.firebase.Timestamp
import java.util.*

object PlantMockData {
    val plantsData = listOf(
        PlantData(
            "id",
            "Незабудка",
            "Здарова",
            "https://n1s1.hsmedia.ru/52/bc/05/52bc058ca615dd1193673a3d7be39e49/690x460_0xc0a8392b_949800641501063422.jpeg",
            "https://cdn.pixabay.com/photo/2015/04/19/08/33/flower-729512_960_720.jpg",
            CareComplexity.Easy,
            "НЕЗАБУДКА DESC",
            SunRelation.DirectLight,
            listOf(
                PestData("111", "ПАРАЗИТ"),
                PestData("2", "ЭУККУ"),
                PestData("9", "ПАУК"),
                PestData("88", "ЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖУк")
            ),
            listOf(Reproduction.SEEDS, Reproduction.SEEDS),
            arrayListOf(BenefitData("qweqweqweqweqwe", "Плюсы определенно есть")),
            "СЕГОДНЯ ИЛИ ЗАВТРА НАДО ОБЯЗАТЕЛЬНО",
            10,
            55,
            Date(),
            3,
            2,
            4,
            666,
            5,
            6,
        ),
        PlantData(
            "id",
            "Лизаблюдка",
            "GFRF",
            "https://n1s1.hsmedia.ru/52/bc/05/52bc058ca615dd1193673a3d7be39e49/690x460_0xc0a8392b_949800641501063422.jpeg",
            "https://cdn.pixabay.com/photo/2015/04/19/08/33/flower-729512_960_720.jpg",
            CareComplexity.Easy,
            "ыыыыыыыыыыыыыыыыыыыыыыыыыыыыыыыыыыыыыыыыыыыыыыыыыы",
            SunRelation.DirectLight,
            listOf(
                PestData("111", "ПАРАЗИТ"),
                PestData("2", "ЭУККУ"),
                PestData("9", "ПАУК"),
                PestData("88", "ЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖУк")
            ),
            emptyList(),
            arrayListOf(BenefitData("qweqweqweqweqwe", "Плюсы были а может и еее")),
            "СЕГОДНЯ ИЛИ ЗАВТРА НАДО ОБЯЗАТЕЛЬНО",
            9,
            10,
            Date(),
            3,
            2,
            4,
            666,
            999,
            9999292,
        ),
        PlantData(
            "id",
            "Незабудка",
            "ПАКА",
            "https://n1s1.hsmedia.ru/52/bc/05/52bc058ca615dd1193673a3d7be39e49/690x460_0xc0a8392b_949800641501063422.jpeg",
            "https://cdn.pixabay.com/photo/2015/04/19/08/33/flower-729512_960_720.jpg",
            CareComplexity.Easy,
            "НЕЗАБУДКА DESC",
            SunRelation.DirectLight,
            listOf(
                PestData("111", "ПАРАЗИТ"),
                PestData("2", "ЭУККУ"),
                PestData("9", "ПАУК"),
                PestData("88", "ЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖУк")
            ),
            emptyList(),
            arrayListOf(BenefitData("qweqweqweqweqwe", "Плюсы определенно есть")),
            "СЕГОДНЯ ИЛИ ЗАВТРА НАДО ОБЯЗАТЕЛЬНО",
            24234,
            111,
            Date(),
            3,
            2,
            4,
            666,
            5,
            6,
        ),
        PlantData(
            "id",
            "Незабудка",
            "ЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖ",
            "https://n1s1.hsmedia.ru/52/bc/05/52bc058ca615dd1193673a3d7be39e49/690x460_0xc0a8392b_949800641501063422.jpeg",
            "https://cdn.pixabay.com/photo/2015/04/19/08/33/flower-729512_960_720.jpg",
            CareComplexity.Easy,
            "НЕЗАБУДКА DESC",
            SunRelation.DirectLight,
            listOf(
                PestData("111", "ПАРАЗИТ"),
                PestData("2", "ЭУККУ"),
                PestData("9", "ПАУК"),
                PestData("88", "ЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖУк")
            ),
            emptyList(),
            arrayListOf(BenefitData("qweqweqweqweqwe", "Плюсы определенно есть")),
            "СЕГОДНЯ ИЛИ ЗАВТРА НАДО ОБЯЗАТЕЛЬНО",
            1,
            10,
            Date(),
            3,
            2,
            4,
            666,
            5,
            6,
        )
    )

    val plantsCloud = listOf(
        PlantCloud(
            "id",
            "Незабудка",
            "https://cdn.pixabay.com/photo/2015/04/19/08/33/flower-729512_960_720.jpg",
            mapOf(Pair("234242", "23423423423")),
            "https://n1s1.hsmedia.ru/52/bc/05/52bc058ca615dd1193673a3d7be39e49/690x460_0xc0a8392b_949800641501063422.jpeg",
            "ЗДАРОВА",
            "Easy",
            "НЕЗАБУДКА DESC",
            mapOf(Pair("234242", "23423423423")),
            "DIRECT_LIGHT",
            listOf("23423", "2", "23423423"),
            listOf("22", "22342", "2342342"),
            listOf("22", "22342", "2342342"),
            "24322",
            555,
            1,
            Timestamp(Date()),
            3,
            2,
            4,
            666,
            3,
            4,
        ),
        PlantCloud(
            "id",
            "Незабудка",
            "https://cdn.pixabay.com/photo/2015/04/19/08/33/flower-729512_960_720.jpg",
            mapOf(Pair("234242", "23423423423")),
            "https://n1s1.hsmedia.ru/52/bc/05/52bc058ca615dd1193673a3d7be39e49/690x460_0xc0a8392b_949800641501063422.jpeg",
            "ЗДАРОВА",
            "Easy",
            "НЕЗАБУДКА DESC",
            mapOf(Pair("234242", "23423423423")),
            "DIRECT_LIGHT",
            listOf("23423", "2", "23423423"),
            listOf("22", "22342", "2342342"),
            listOf("22", "22342", "2342342"),
            "24322",
            555,
            99,
            Timestamp(Date()),
            3,
            2,
            4,
            666,
            3,
            4,
        ),
        PlantCloud(
            "id",
            "Незабудка",
            "https://cdn.pixabay.com/photo/2015/04/19/08/33/flower-729512_960_720.jpg",
            mapOf(Pair("234242", "23423423423")),
            "https://n1s1.hsmedia.ru/52/bc/05/52bc058ca615dd1193673a3d7be39e49/690x460_0xc0a8392b_949800641501063422.jpeg",
            "ЗДАРОВА",
            "Easy",
            "НЕЗАБУДКА DESC",
            mapOf(Pair("234242", "23423423423")),
            "DIRECT_LIGHT",
            listOf("23423", "2", "23423423"),
            listOf("22", "22342", "2342342"),
            listOf("22", "22342", "2342342"),
            "24322",
            555,
            29,
            Timestamp(Date()),
            3,
            2,
            4,
            666,
            3,
            4,
        ),
        PlantCloud(
            "id",
            "Незабудка",
            "https://cdn.pixabay.com/photo/2015/04/19/08/33/flower-729512_960_720.jpg",
            mapOf(Pair("234242", "23423423423")),
            "https://n1s1.hsmedia.ru/52/bc/05/52bc058ca615dd1193673a3d7be39e49/690x460_0xc0a8392b_949800641501063422.jpeg",
            "ЗДАРОВА",
            "Easy",
            "НЕЗАБУДКА DESC",
            mapOf(Pair("234242", "23423423423")),
            "DIRECT_LIGHT",
            listOf("23423", "2", "23423423"),
            listOf("22", "22342", "2342342"),
            listOf("22", "22342", "2342342"),
            "24322",
            555,
            1,
            Timestamp(Date()),
            3,
            2,
            4,
            666,
            3,
            4,
        )
    )
}