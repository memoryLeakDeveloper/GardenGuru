package com.example.gardenguru.data.plant.cloud.create

data class CreatePlantBody(
    val name: String,
    val garden: String,
    val plant: CreatePlantCloudObj,
)
