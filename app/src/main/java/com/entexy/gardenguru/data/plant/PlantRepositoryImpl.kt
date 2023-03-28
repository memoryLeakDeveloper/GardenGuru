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
    private val deletePlantPhotoDataSource: DeletePlantPhotoDataSource,
    private val renamePlantDataSource: RenamePlantDataSource,
    private val addPlantDataSource: AddPlantDataSource,
) : PlantRepository {

    override suspend fun fetchPlant(idPlant: String): CloudResponse<PlantData> {
        return plantSource.fetchPlant(idPlant)
    }

    override suspend fun fetchPests(idPests: List<String>?): ArrayList<PestData> {
        val result = arrayListOf<PestData>()
        idPests?.forEach {
            val cloudResponse = pestsSource.fetchPests(it)
            if (cloudResponse is CloudResponse.Success) {
                val data = cloudResponse.result?.mapToData()
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
                val data = cloudResponse.result?.mapToData()
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
                result.add(plantResult.result)
            } else {
                return CloudResponse.Error((plantResult as? CloudResponse.Error)?.exception)
            }
        }
        return CloudResponse.Success(result)
    }

    override suspend fun searchPlantByName(plantName: String): CloudResponse<List<PlantData>> {
        val plantResults = searchPlantDataSource.searchPlantByName(plantName)

        return if (plantResults is CloudResponse.Success) {
            CloudResponse.Success(plantResults.result)
        } else {
            CloudResponse.Error((plantResults as? CloudResponse.Error)?.exception)
        }
    }

    override suspend fun renamePlant(plantId: String, plantName: String): CloudResponse<Unit> =
        renamePlantDataSource.renamePlant(plantId, plantName)

    override suspend fun deletePlantPhoto(plantId: String): CloudResponse<Unit> {
        return deletePlantPhotoDataSource.deletePhoto(plantId)
    }

    override suspend fun deletePlant(plantId: String): CloudResponse<Unit> {
        return deletePlantDataSource.deletePlant(plantId)
    }

    override suspend fun addPlant(plantId: String) = addPlantDataSource.addPlant(plantId)

}