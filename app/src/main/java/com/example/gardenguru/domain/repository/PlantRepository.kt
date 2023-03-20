package com.example.gardenguru.domain.repository

import com.example.gardenguru.data.plant.PlantData

interface PlantRepository {

    suspend fun fetchPlant(idPlant: String): PlantData?

    suspend fun createPlant(gardenId: String, plantData: PlantData): Boolean

}