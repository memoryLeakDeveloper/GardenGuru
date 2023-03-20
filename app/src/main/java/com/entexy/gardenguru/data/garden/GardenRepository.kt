package com.entexy.gardenguru.data.garden

import com.entexy.gardenguru.core.exception.ErrorResponseCodeException
import com.entexy.gardenguru.data.auth.TokenHelper
import com.entexy.gardenguru.data.garden.cloud.create.CreateGardenSource
import com.entexy.gardenguru.data.garden.cloud.delete.DeleteGardenSource
import com.entexy.gardenguru.data.garden.cloud.edit.EditGardenSource
import com.entexy.gardenguru.data.garden.cloud.get.GardensDataSource
import com.entexy.gardenguru.data.garden.cloud.names.GetGardenNamesDataSource
import com.entexy.gardenguru.data.garden.cloud.participants.add.AddParticipantSource
import com.entexy.gardenguru.data.garden.cloud.participants.delete.DeleteParticipantSource
import com.entexy.gardenguru.data.garden.cloud.participants.edit.EditParticipantRoleSource
import com.entexy.gardenguru.data.garden.models.GardenData
import com.entexy.gardenguru.data.garden.models.GardenName
import com.entexy.gardenguru.data.language.LanguageHelper
import java.lang.Exception
import java.net.ConnectException
import javax.inject.Inject

interface GardenRepository {

    suspend fun getGardens(): ArrayList<GardenData>

    suspend fun deleteGarden(gardenId: String): Boolean

    suspend fun createGarden(gardenName: String): GardenName?

    suspend fun getGardenNames(): ArrayList<GardenName>

    suspend fun addParticipant(email: String, gardenId: String): Boolean

    suspend fun deleteParticipant(participantId: String): Boolean

    suspend fun changeParticipantRole(participantId: String, role: String): Boolean

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
        private val editParticipantRoleSource: EditParticipantRoleSource,
        private val addParticipantSource: AddParticipantSource,
        private val deleteParticipantSource: DeleteParticipantSource,
        private val createGardenSource: CreateGardenSource,
        private val deleteGardenSource: DeleteGardenSource,
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

        override suspend fun deleteGarden(gardenId: String): Boolean {
            try {
                return deleteGardenSource.delete(tokenHelper.getToken(), gardenId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return false
        }

        override suspend fun createGarden(gardenName: String): GardenName? {
            try {
                return createGardenSource.createGarden(tokenHelper.getToken(), gardenName, languageHelper.getLanguage())
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        override suspend fun addParticipant(email: String, gardenId: String): Boolean{
            return try {
                addParticipantSource.addParticipant(tokenHelper.getToken(), email, gardenId)
            } catch (e: Exception){
                e.printStackTrace()
                false
            }
        }

        override suspend fun deleteParticipant(participantId: String): Boolean {
            return try {
                deleteParticipantSource.deleteParticipant(tokenHelper.getToken(), participantId)
            } catch (e: Exception){
                e.printStackTrace()
                false
            }
        }

        override suspend fun changeParticipantRole(participantId: String, role: String): Boolean {
            return try {
                editParticipantRoleSource.editRole(tokenHelper.getToken(), participantId, role)
            } catch (e: Exception){
                e.printStackTrace()
                false
            }
        }

        override suspend fun editGardenNameAndSeason(
            id: String,
            name: String,
            summerClimateType: String,
        ): Boolean {
            return try {
                editDataSource.edit(tokenHelper.getToken(), id, name, summerClimateType)
            } catch (e: Exception){
                e.printStackTrace()
                false
            }
        }

    }
}