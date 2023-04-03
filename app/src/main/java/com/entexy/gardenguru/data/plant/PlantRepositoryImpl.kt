package com.entexy.gardenguru.data.plant

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.plant.benefit.BenefitData
import com.entexy.gardenguru.data.plant.benefit.BenefitsCloudDataSource
import com.entexy.gardenguru.data.plant.benefit.mapToData
import com.entexy.gardenguru.data.plant.cloud.*
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
    private val deletePlantDataSource: DeletePlantDataSource,
    private val renamePlantDataSource: RenamePlantDataSource,
    private val updatePlantCustomPhotoDataSource: UpdatePlantCustomPhotoDataSource,
    private val addPlantDataSource: AddPlantDataSource,
) : PlantRepository {

    override suspend fun fetchUserPlants(): CloudResponse<ArrayList<PlantData>> {
        val plantDataCloud = plantSource.fetchPlants()

        val result = arrayListOf<PlantData>()
        return if (plantDataCloud is CloudResponse.Success) {
            plantDataCloud.result.forEach {
                val plantData = it.mapToData()
                if (plantData != null) {
                    CloudResponse.Success(plantData.apply {
                        pests = fetchPests(it.pestsIds)
                        benefits = fetchBenefits(it.benefitsIds)
                    })
                    result.add(plantData)
                }
            }
            CloudResponse.Success(result)
        } else CloudResponse.Error(null)
    }

    //returns a list of plants with empty benefits, pests
    override suspend fun fetchPlainUserPlants(): CloudResponse<List<PlantData>> {
        val plantDataCloud = plantSource.fetchPlants()

        return if (plantDataCloud is CloudResponse.Success) {
            val result = mutableListOf<PlantData>()
            plantDataCloud.result.forEach {
                val plantData = it.mapToData()
                if (plantData != null)
                    result.add(plantData)
            }
            CloudResponse.Success(result)
        } else CloudResponse.Error(null)
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

    override suspend fun updatePlantCustomPhoto(plantId: String, photoUrl: String?): CloudResponse<Unit> =
        updatePlantCustomPhotoDataSource.renamePlant(plantId, photoUrl)

    override suspend fun deletePlant(plantId: String): CloudResponse<Unit> {
        return deletePlantDataSource.deletePlant(plantId)
    }

    override suspend fun addPlant(plantId: String) = addPlantDataSource.addPlant(plantId)

}