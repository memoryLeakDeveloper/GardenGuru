package com.example.gardenguru.data.garden

import com.example.gardenguru.core.exception.ErrorResponseCodeException
import com.example.gardenguru.data.auth.TokenHelper
import com.example.gardenguru.data.garden.cloud.create.CreateGardenSource
import com.example.gardenguru.data.garden.cloud.edit.EditGardenSource
import com.example.gardenguru.data.garden.cloud.get.GardensDataSource
import com.example.gardenguru.data.garden.cloud.names.GetGardenNamesDataSource
import com.example.gardenguru.data.garden.models.GardenData
import com.example.gardenguru.data.garden.models.GardenName
import com.example.gardenguru.data.language.LanguageHelper
import java.lang.Exception
import java.net.ConnectException
import javax.inject.Inject

interface GardenRepository {

    suspend fun getGardens(): ArrayList<GardenData>
    suspend fun deleteGarden()
    suspend fun createGarden(gardenName: String): GardenName?

    suspend fun getGardenNames(): ArrayList<GardenName>

    suspend fun addParticipant()

    suspend fun editGardenNameAndSeason(
        id: String,
        name: String,
        summerClimateType: String,
    ): Boolean


    class Base @Inject constructor(
        private val tokenHelper: TokenHelper.Base,
        private val languageHelper: LanguageHelper,
        private val gardenCloudDataSource: GardensDataSource,
        private val editDataSource: EditGardenSource,
        private val createGardenSource: CreateGardenSource,
        private val getGardenNamesDataSource: GetGardenNamesDataSource,
        private val gardenMapper: GardenMapper
    ) : GardenRepository {
        override suspend fun getGardens(): ArrayList<GardenData> {
            try {
                val clouds = gardenCloudDataSource.fetch(tokenHelper.getToken(), languageHelper.getLanguage())

                val result = arrayListOf<GardenData>()
                clouds.forEach {
                    result.add(gardenMapper.map(it))
                }
                return result
            } catch (e: ErrorResponseCodeException) {
                e.printStackTrace()
            } catch (e: ConnectException) {
                e.printStackTrace()
            }
            return arrayListOf()
        }

        override suspend fun getGardenNames(): ArrayList<GardenName> {
            try {
                return getGardenNamesDataSource.fetch(tokenHelper.getToken())
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return arrayListOf()
        }

        override suspend fun deleteGarden() {
            TODO("Not yet implemented")
        }

        override suspend fun createGarden(gardenName: String): GardenName? {
            try {
                return createGardenSource.createGarden(tokenHelper.getToken(), gardenName, languageHelper.getLanguage())
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        override suspend fun addParticipant() {
            TODO("Not yet implemented")
        }

        override suspend fun editGardenNameAndSeason(
            id: String,
            name: String,
            summerClimateType: String,
        ): Boolean {
            return try {
                editDataSource.edit(tokenHelper.getToken(), id, name, summerClimateType)
            } catch (e: ErrorResponseCodeException) {
                e.printStackTrace()
                false
            } catch (e: ConnectException) {
                e.printStackTrace()
                false
            }
        }

    }
}