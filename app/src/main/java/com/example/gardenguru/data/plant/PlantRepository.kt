package com.example.gardenguru.data.plant

import com.example.gardenguru.data.auth.TokenHelper
import com.example.gardenguru.data.plant.cloud.PlantCloud
import com.example.gardenguru.data.plant.cloud.create.CreatePlantBody
import com.example.gardenguru.data.plant.cloud.create.CreatePlantCloudMapper
import com.example.gardenguru.data.plant.cloud.create.CreatePlantCloudObj
import com.example.gardenguru.data.plant.cloud.create.CreatePlantSource
import javax.inject.Inject

interface PlantRepository {

    suspend fun fetchPlant(idPlant: String): PlantCloud

    suspend fun createPlant(gardenId: String, plantData: PlantData): Boolean

    class Base @Inject constructor(
        private val tokenHelper: TokenHelper.Base,
        private val createPlantCloudMapper: CreatePlantCloudMapper,
        private val createPlantSource: CreatePlantSource
    ): PlantRepository{
        override suspend fun fetchPlant(idPlant: String): PlantCloud {
            TODO("Not yet implemented")
        }

        override suspend fun createPlant(gardenId: String, plantData: PlantData): Boolean {
            try {
                val plantCloudObj = createPlantCloudMapper.map(plantData)
                return createPlantSource.createPlant(tokenHelper.getToken(), CreatePlantBody(plantData.name, gardenId, plantCloudObj)) //todo
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return false
        }
    }
}