package com.example.gardenguru.data.plant

import com.example.gardenguru.data.plant.cloud.PlantCloud

interface PlantRepository {
    suspend fun fetchPlant(idPlant: String): PlantCloud
}