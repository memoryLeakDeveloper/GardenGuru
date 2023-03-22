package com.entexy.gardenguru.data.plant

import com.entexy.gardenguru.data.auth.TokenHelper
import com.entexy.gardenguru.data.language.LanguageHelper
import com.entexy.gardenguru.data.plant.benefit.BenefitData
import com.entexy.gardenguru.data.plant.benefit.BenefitsCloudDataSource
import com.entexy.gardenguru.data.plant.cloud.PlantCloudDataSource
import com.entexy.gardenguru.data.plant.cloud.SearchPlantDataSource
import com.entexy.gardenguru.data.plant.pest.PestData
import com.entexy.gardenguru.data.plant.pest.PestsCloudDataSource
import com.entexy.gardenguru.domain.repository.PlantRepository
import javax.inject.Inject

class PlantRepositoryImpl @Inject constructor(
    private val tokenHelper: TokenHelper,
    private val plantSource: PlantCloudDataSource,
    private val pestsSource: PestsCloudDataSource,
    private val benefitsSource: BenefitsCloudDataSource,
    private val searchPlantDataSource: SearchPlantDataSource,
    private val languageHelper: LanguageHelper
) : PlantRepository {

    override suspend fun fetchPlant(idPlant: String) = runCatching {
        val plantCloud = plantSource.fetchPlant(tokenHelper.getToken(), languageHelper.getLanguage(), idPlant)

        plantCloud.mapToData().apply {
            pests = fetchPests(plantCloud.pestsIds)
            benefits = fetchBenefits(plantCloud.benefitsIds)
        }
    }.getOrNull()

    override suspend fun fetchPests(idPests: List<String>?): ArrayList<PestData> {
        val result = arrayListOf<PestData>()
        idPests?.forEach {
            result.add(pestsSource.fetchPests(languageHelper.getLanguage(), it))
        }
        return result
    }

    override suspend fun fetchBenefits(idBenefits: List<String>?): ArrayList<BenefitData> {
        val result = arrayListOf<BenefitData>()
        idBenefits?.forEach {
            result.add(benefitsSource.fetchBenefits(languageHelper.getLanguage(), it))
        }
        return result
    }

    override suspend fun searchPlant(plantRecognitionStrings: List<String>): List<PlantData> {
        val result = mutableListOf<PlantData>()
        plantRecognitionStrings.forEach {
            val plantResult = searchPlantDataSource.searchPlant(it)
            result.add(plantResult.mapToData())
        }
        return result
    }
}