package com.entexy.gardenguru.data.plant

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.language.LanguageHelper
import com.entexy.gardenguru.data.plant.benefit.BenefitData
import com.entexy.gardenguru.data.plant.benefit.BenefitsCloudDataSource
import com.entexy.gardenguru.data.plant.benefit.mapToData
import com.entexy.gardenguru.data.plant.cloud.*
import com.entexy.gardenguru.data.plant.pest.PestData
import com.entexy.gardenguru.data.plant.pest.PestsCloudDataSource
import com.entexy.gardenguru.data.plant.pest.mapToData
import com.entexy.gardenguru.domain.repository.PlantRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlantRepositoryImpl @Inject constructor(
    private val plantSource: PlantCloudDataSource,
    private val pestsSource: PestsCloudDataSource,
    private val benefitsSource: BenefitsCloudDataSource,
    private val searchPlantDataSource: SearchPlantDataSource,
    private val deletePlantDataSource: DeletePlantDataSource,
    private val movePlantDataSource: MovePlantDataSource,
    private val renamePlantDataSource: RenamePlantDataSource,
    private val addPlantDataSource: AddPlantDataSource,
    private val languageHelper: LanguageHelper
) : PlantRepository {

    override suspend fun fetchPlant(idPlant: String) = flow {
        emit(CloudResponse.Loading())
        val plantCloud = plantSource.fetchPlant(idPlant)
        val plantData = plantCloud?.mapToData(languageHelper.getLanguage())
        if (plantData != null)
            emit(CloudResponse.Success(plantData.apply {
                pests = fetchPests(plantCloud.pestsIds)
                benefits = fetchBenefits(plantCloud.benefitsIds)
            }))
    }.catch {
        emit(CloudResponse.Error(it))
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
                result.add(it.mapToData(languageHelper.getLanguage()))
            }
        } else {
            return CloudResponse.Error((plantResults as? CloudResponse.Error)?.exception)
        }
        return CloudResponse.Success(result)
    }

    override suspend fun renamePlant(plantId: String, plantName: String) = renamePlantDataSource.renamePlant(plantId, plantName)

    override suspend fun deletePlant(plantId: String) = deletePlantDataSource.deletePlant(plantId)

    override suspend fun addPlant(plantId: String) = addPlantDataSource.addPlant(plantId)

    override suspend fun movePlantToAnotherGarden(plantId: String, gardenId: String): CloudResponse<Boolean> {
        return movePlantDataSource.movePlant(plantId, gardenId)
    }
}