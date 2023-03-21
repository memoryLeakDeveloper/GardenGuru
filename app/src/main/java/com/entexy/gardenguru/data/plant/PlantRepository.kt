package com.entexy.gardenguru.data.plant

import android.util.Log
import com.entexy.gardenguru.data.auth.TokenHelper
import com.entexy.gardenguru.data.language.LanguageHelper
import com.entexy.gardenguru.data.plant.cloud.PlantCloudDataSource
import com.entexy.gardenguru.data.plant.cloud.PlantCloudMapper
import com.entexy.gardenguru.data.plant.cloud.create.CreatePlantBody
import com.entexy.gardenguru.data.plant.cloud.create.CreatePlantCloudMapper
import com.entexy.gardenguru.data.plant.cloud.create.CreatePlantSource
import javax.inject.Inject

interface PlantRepository {

    suspend fun fetchPlant(idPlant: String): PlantData?

    suspend fun createPlant(gardenId: String, plantData: PlantData): Boolean

    class Base @Inject constructor(
        private val tokenHelper: TokenHelper.Base,
        private val createPlantSource: CreatePlantSource,
        private val plantSource: PlantCloudDataSource,
        private val cloudMapper: PlantCloudMapper,
        private val createPlantCloudMapper: CreatePlantCloudMapper,
        private val languageHelper: LanguageHelper
    ) : PlantRepository {

        override suspend fun fetchPlant(idPlant: String): PlantData? {
            Log.d("bugger", "${plantSource.fetchPlant(tokenHelper.getToken(), languageHelper.getLanguage(), idPlant)}")
            return try {
                cloudMapper.mapCloudToData(plantSource.fetchPlant(tokenHelper.getToken(), languageHelper.getLanguage(), idPlant))
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        override suspend fun createPlant(gardenId: String, plantData: PlantData): Boolean {
            try {
                val plantCloudObj = createPlantCloudMapper.map(plantData)
                return createPlantSource.createPlant(
                    tokenHelper.getToken(),
                    CreatePlantBody(plantData.name, gardenId, plantCloudObj)
                ) //todo
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return false
        }
    }
}