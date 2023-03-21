package com.entexy.gardenguru.data.plant

import com.entexy.gardenguru.data.auth.TokenHelper
import com.entexy.gardenguru.data.language.LanguageHelper
import com.entexy.gardenguru.data.plant.cloud.PlantCloudDataSource
import com.entexy.gardenguru.data.plant.cloud.create.CreatePlantBody
import com.entexy.gardenguru.data.plant.cloud.create.CreatePlantSource
import com.entexy.gardenguru.data.plant.cloud.mapToData
import com.entexy.gardenguru.domain.repository.PlantRepository
import javax.inject.Inject

class PlantRepositoryImpl @Inject constructor(
    private val tokenHelper: TokenHelper.Base,
    private val createPlantSource: CreatePlantSource,
    private val plantSource: PlantCloudDataSource,
    private val languageHelper: LanguageHelper
) : PlantRepository {

    override suspend fun fetchPlant(idPlant: String) = runCatching {
        plantSource.fetchPlant(tokenHelper.getToken(), languageHelper.getLanguage(), idPlant).mapToData()
    }.getOrNull()

    override suspend fun createPlant(gardenId: String, plantData: PlantData) = runCatching {
        val plantCloudObj = plantData.mapToCreatePlantCloud()
        createPlantSource.createPlant(tokenHelper.getToken(), CreatePlantBody(plantData.name, gardenId, plantCloudObj)) //todo
    }.getOrElse {
        it.printStackTrace()
        false
    }

}