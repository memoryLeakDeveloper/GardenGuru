package com.example.gardenguru.data.plant

import com.example.gardenguru.data.plant.cloud.PlantCloud
import javax.inject.Inject

class PlantRepositoryImpl @Inject constructor() : PlantRepository {

    override suspend fun fetchPlant(idPlant: String): PlantCloud {
        TODO("Not yet implemented")
    }
}