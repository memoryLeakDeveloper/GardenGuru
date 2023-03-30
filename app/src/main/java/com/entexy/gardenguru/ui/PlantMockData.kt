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
    val plant =
        PlantData(
            "id",
            "Петр",
            "Кактус",
            "https://cactus.by/sites/default/files/styles/product/public/products/chi-chi-pi_edit.jpg?itok=USPbAKWy",
            null,
            "https://meksika.info/wp-content/uploads/2018/09/Kaktusy-Meksiki.jpg",
            CareComplexity.Medium,
            "Ка́ктусовые (лат. Cactaceae) — семейство многолетних цветковых растений порядка Гвоздичноцветные, включает около 127 родов и около 1750 видов[2], обитающих преимущественно в засушливых областях, включая одну из самых сухих пустынь мира — пустыню Атакама.",
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
            3,
            4,
            5,
            6,
            "Темной весенней ночью в полночь",
            mapOf("ru" to "qweqwe"),
            mapOf("ru" to "qweqwe"),
        )

    val plantsData = listOf(
        PlantData(
            "id",
            "Здарова",
            "Незабудка",
            "https://n1s1.hsmedia.ru/52/bc/05/52bc058ca615dd1193673a3d7be39e49/690x460_0xc0a8392b_949800641501063422.jpeg",
            null,
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
            listOf(Reproduction.Seeds, Reproduction.Seeds),
            arrayListOf(BenefitData("qweqweqweqweqwe", "Плюсы определенно есть")),
            "СЕГОДНЯ ИЛИ ЗАВТРА НАДО ОБЯЗАТЕЛЬНО",
            Date(),
            10,
            55,
            3,
            2,
            4,
            33,
            5,
            6,
            "Темной весенней ночью в полночь",

            mapOf("ru" to "qweqwe"),
            mapOf("ru" to "qweqwe"),
        ),
        PlantData(
            "id",
            "Лизаблюдка",
            "GFRF",
            "https://n1s1.hsmedia.ru/52/bc/05/52bc058ca615dd1193673a3d7be39e49/690x460_0xc0a8392b_949800641501063422.jpeg",
            null,
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
            Date(),
            9,
            10,
            3,
            2,
            4,
            666,
            999,
            9999292,
            "Темной весенней ночью в полночь",

            mapOf("ru" to "qweqwe"),
            mapOf("ru" to "qweqwe"),
        ),
        PlantData(
            "id",
            "Незабудка",
            "ПАКА",
            "https://n1s1.hsmedia.ru/52/bc/05/52bc058ca615dd1193673a3d7be39e49/690x460_0xc0a8392b_949800641501063422.jpeg",
            null,
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
            Date(),
            234,
            111,
            3,
            2,
            4,
            666,
            5,
            6,
            "Темной весенней ночью в полночь",

            mapOf("ru" to "qweqwe"),
            mapOf("ru" to "qweqwe"),
        ),
        PlantData(
            "id",
            "Незабудка",
            "ЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖЖ",
            "https://n1s1.hsmedia.ru/52/bc/05/52bc058ca615dd1193673a3d7be39e49/690x460_0xc0a8392b_949800641501063422.jpeg",
            null,
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
            Date(),
            1,
            10,
            3,
            2,
            4,
            666,
            5,
            6,
            "Темной весенней ночью в полночь",

            mapOf("ru" to "qweqwe"),
            mapOf("ru" to "qweqwe"),
        )
    )

    val plantsCloud = listOf(
        PlantCloud(
            "id",
            "Федор",
            "Незабудка",
            mapOf("ru" to "Незабудка"),
            "https://cdn.pixabay.com/photo/2015/04/19/08/33/flower-729512_960_720.jpg",
            null,
            "https://n1s1.hsmedia.ru/52/bc/05/52bc058ca615dd1193673a3d7be39e49/690x460_0xc0a8392b_949800641501063422.jpeg",
            CareComplexity.VeryDifficult.cloudName,
            "НЕЗАБУДКА DESC",
            mapOf(Pair("ru", "23423423423 desc")),
            SunRelation.DirectLight.cloudName,
            arrayListOf("Easy"),
            arrayListOf(Reproduction.Seeds.cloudValue),
            listOf("23423", "2", "23423423"),
            "qwpkq[kfdqw[kd",
            Timestamp(Date()),
            "Темной весенней ночью в полночь",

            555,
            1,
            3,
            2,
            4,
            666,
            3,
            4,
        ),
    )
}