package com.entexy.gardenguru.data.garden.cloud

import com.entexy.gardenguru.data.plant.cloud.PhotoDataCloud

data class GardenPlantCloud(
    val id: String,
    val name: String,
    val plant: String,
    val photos: ArrayList<PhotoDataCloud>
)