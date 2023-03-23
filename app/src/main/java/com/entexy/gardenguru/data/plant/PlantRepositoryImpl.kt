package com.entexy.gardenguru.data.plant

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.language.LanguageHelper
import com.entexy.gardenguru.data.plant.benefit.BenefitData
import com.entexy.gardenguru.data.plant.benefit.BenefitsCloudDataSource
import com.entexy.gardenguru.data.plant.benefit.mapToData
import com.entexy.gardenguru.data.plant.cloud.PlantCloudDataSource
import com.entexy.gardenguru.data.plant.cloud.SearchPlantDataSource
import com.entexy.gardenguru.data.plant.pest.PestData
import com.entexy.gardenguru.data.plant.pest.PestsCloudDataSource
import com.entexy.gardenguru.data.plant.pest.mapToData
import com.entexy.gardenguru.domain.repository.PlantRepository
import javax.inject.Inject

class PlantRepositoryImpl @Inject constructor(
    private val plantSource: PlantCloudDataSource,
    private val pestsSource: PestsCloudDataSource,
    private val benefitsSource: BenefitsCloudDataSource,
    private val searchPlantDataSource: SearchPlantDataSource,
    private val languageHelper: LanguageHelper
) : PlantRepository {

    override suspend fun fetchPlant(idPlant: String): CloudResponse<PlantData> {
        val plantCloud = plantSource.fetchPlant(idPlant)

        return if (plantCloud is CloudResponse.Success) {
            val plantData = plantCloud.result!!.mapToData(languageHelper.getLanguage())

            if (plantData != null)
                CloudResponse.Success(plantData.apply {
                    pests = fetchPests(plantCloud.result.pestsIds)
                    benefits = fetchBenefits(plantCloud.result.benefitsIds)
                })
            else CloudResponse.Error(IllegalArgumentException())
        } else {
            val exception = (plantCloud as? CloudResponse.Error)?.exception
            CloudResponse.Error(exception)
        }
    }

    override suspend fun fetchPests(idPests: List<String>?): ArrayList<PestData> {
        val result = arrayListOf<PestData>()
        idPests?.forEach {
            val cloudResponse = pestsSource.fetchPests(it)
            if (cloudResponse is CloudResponse.Success) {
                val data = cloudResponse.result?.mapToData(languageHelper.getLanguage())
                if (data != null)
                    result.add(data)
            }
        }
        return result
    }

    override suspend fun fetchBenefits(idBenefits: List<String>?): ArrayList<BenefitData> {
        val result = arrayListOf<BenefitData>()
        idBenefits?.forEach {
            val cloudResponse = benefitsSource.fetchBenefits(it)
            if (cloudResponse is CloudResponse.Success) {
                val data = cloudResponse.result?.mapToData(languageHelper.getLanguage())
                if (data != null)
                    result.add(data)
            }
        }
        return result
    }

    override suspend fun searchPlantByVarietyCode(plantSearchQuires: List<String>): CloudResponse<List<PlantData>> {
        val result = mutableListOf<PlantData>()
        plantSearchQuires.forEach {
            val plantResult = searchPlantDataSource.searchPlantByVarietyCode(it)

            if (plantResult is CloudResponse.Success) {
                val plantData = plantResult.result?.mapToData(languageHelper.getLanguage())
                if (plantData != null)
                    result.add(plantData)
            } else {
                return CloudResponse.Error((plantResult as? CloudResponse.Error)?.exception)
            }
        }
        return CloudResponse.Success(result)
    }

    override suspend fun searchPlantByName(plantName: String): CloudResponse<List<PlantData>> {
        val plantResults = searchPlantDataSource.searchPlantByName(plantName)
        val result = mutableListOf<PlantData>()

        if (plantResults is CloudResponse.Success) {
            plantResults.result?.forEach {
                val plantData = it.mapToData(languageHelper.getLanguage())
                if (plantData != null)
                    result.add(plantData)
            }

        } else {
            return CloudResponse.Error((plantResults as? CloudResponse.Error)?.exception)
        }

        return CloudResponse.Success(result)
    }
}