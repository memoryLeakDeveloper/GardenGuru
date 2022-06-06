package com.example.gardenguru.data.plant

import com.example.gardenguru.core.exception.ErrorResponseCodeException
import com.example.gardenguru.data.auth.TokenHelper
import com.example.gardenguru.data.plant.cloud.PlantCloud
import com.example.gardenguru.data.plant.cloud.create.CreatePlantBody
import com.example.gardenguru.data.plant.cloud.create.CreatePlantSource
import java.net.ConnectException
import javax.inject.Inject

interface PlantRepository {

    suspend fun fetchPlant(idPlant: String): PlantCloud

    suspend fun createPlant(name: String, gardenId: String, plantCloud: CreatePlantBody): Boolean

    class Base @Inject constructor(
        private val tokenHelper: TokenHelper.Base,
        private val createPlantSource: CreatePlantSource
    ): PlantRepository{
        override suspend fun fetchPlant(idPlant: String): PlantCloud {
            TODO("Not yet implemented")
        }

        override suspend fun createPlant(name: String, gardenId: String, plantCloud: CreatePlantBody): Boolean {
            try {
                return createPlantSource.createPlant(tokenHelper.getToken(), name, gardenId, plantCloud) //todo
            } catch (e: ErrorResponseCodeException) {
                e.printStackTrace()
            } catch (e: ConnectException) {
                e.printStackTrace()
            }
            return false
        }
    }
}