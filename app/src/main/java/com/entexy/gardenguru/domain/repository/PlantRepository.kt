package com.entexy.gardenguru.domain.repository

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.plant.benefit.BenefitData
import com.entexy.gardenguru.data.plant.pest.PestData

interface PlantRepository {

    suspend fun fetchUserPlants(): CloudResponse<ArrayList<PlantData>>

    suspend fun fetchPlainUserPlants(): CloudResponse<List<PlantData>>

    suspend fun fetchPests(idPests: List<String>?): ArrayList<PestData>

    suspend fun fetchBenefits(idBenefits: List<String>?): ArrayList<BenefitData>

    suspend fun searchPlantByVarietyCode(plantSearchQuires: List<String>): CloudResponse<List<PlantData>>

    suspend fun searchPlantByName(plantName: String): CloudResponse<List<PlantData>>

    suspend fun renamePlant(plantId: String, plantName: String): CloudResponse<Unit>

    suspend fun updatePlantCustomPhoto(plantId: String, photoUrl: String?): CloudResponse<Unit>

    suspend fun deletePlant(plantId: String): CloudResponse<Unit>

    suspend fun addPlant(data: PlantData): CloudResponse<Unit>

}