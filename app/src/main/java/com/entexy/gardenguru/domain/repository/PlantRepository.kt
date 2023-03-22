package com.entexy.gardenguru.domain.repository

import com.entexy.gardenguru.data.plant.benefit.BenefitData
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.plant.pest.PestData

interface PlantRepository {

    suspend fun fetchPlant(idPlant: String): PlantData?

    suspend fun fetchPests(idPests: List<String>?): ArrayList<PestData>

    suspend fun fetchBenefits(idBenefits: List<String>?): ArrayList<BenefitData>

    suspend fun searchPlant(plantRecognitionStrings: List<String>): List<PlantData>

}