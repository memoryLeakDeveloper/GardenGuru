package com.entexy.gardenguru.data.garden.cloud

import com.entexy.gardenguru.data.garden.models.GardenData

data class GardenCloud(
    val id: String,
    val name: String,
    val photo: String,
    val plants: ArrayList<String>,
)

fun GardenCloud.mapToData() = GardenData(
    id = id,
    name = name,
    photo = photo,
    plants = arrayListOf()

)