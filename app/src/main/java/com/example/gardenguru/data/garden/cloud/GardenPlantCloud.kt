package com.example.gardenguru.data.garden.cloud

import com.example.gardenguru.core.Base
import com.example.gardenguru.data.plant.cloud.PhotoDataCloud

data class GardenPlantCloud(
    val id: String,
    val name: String,
    val plant: String,
    val photos: ArrayList<PhotoDataCloud>
) : Base.CloudObject