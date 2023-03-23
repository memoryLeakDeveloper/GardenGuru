package com.entexy.gardenguru.domain.repository

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.plant.benefit.BenefitData
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.plant.pest.PestData
import kotlinx.coroutines.flow.Flow

interface PlantRepository {

    suspend fun fetchPlant(idPlant: String): Flow<CloudResponse<PlantData>>

    suspend fun fetchPests(idPests: List<String>?): ArrayList<PestData>

    suspend fun fetchBenefits(idBenefits: List<String>?): ArrayList<BenefitData>

    suspend fun searchPlantByVarietyCode(plantSearchQuires: List<String>): CloudResponse<List<PlantData>>

    suspend fun searchPlantByName(plantName: String): CloudResponse<List<PlantData>>

}