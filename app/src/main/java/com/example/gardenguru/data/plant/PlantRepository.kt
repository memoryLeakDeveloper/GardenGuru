package com.example.gardenguru.data.plant

import com.example.gardenguru.data.plant.cloud.PlantCloud

interface PlantRepository {

    suspend fun fetchPlant(idPlant: String): PlantCloud

    class Base: PlantRepository{
        override suspend fun fetchPlant(idPlant: String): PlantCloud {
            TODO("Not yet implemented")
        }

    }
}