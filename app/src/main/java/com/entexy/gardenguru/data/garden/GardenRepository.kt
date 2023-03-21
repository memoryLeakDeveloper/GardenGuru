package com.entexy.gardenguru.data.garden

import com.entexy.gardenguru.data.auth.TokenHelper
import com.entexy.gardenguru.data.garden.cloud.create.CreateGardenSource
import com.entexy.gardenguru.data.garden.cloud.delete.DeleteGardenSource
import com.entexy.gardenguru.data.garden.cloud.edit.EditGardenSource
import com.entexy.gardenguru.data.garden.cloud.get.GardensDataSource
import com.entexy.gardenguru.data.garden.cloud.mapToData
import com.entexy.gardenguru.data.garden.cloud.names.GetGardenNamesDataSource
import com.entexy.gardenguru.data.garden.cloud.participants.add.AddParticipantSource
import com.entexy.gardenguru.data.garden.cloud.participants.delete.DeleteParticipantSource
import com.entexy.gardenguru.data.garden.cloud.participants.edit.EditParticipantRoleSource
import com.entexy.gardenguru.data.language.LanguageHelper
import com.entexy.gardenguru.domain.repository.GardenRepository
import javax.inject.Inject

class GardenRepositoryImpl @Inject constructor(
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
) : GardenRepository {

    override suspend fun getGardens() = runCatching {
        val clouds = gardenCloudDataSource.fetch(tokenHelper.getToken(), languageHelper.getLanguage())
        clouds.map { it.mapToData() }
    }.getOrElse {
        it.stackTraceToString()
        listOf()
    }

    override suspend fun getGardenNames() = runCatching {
        getGardenNamesDataSource.fetch(tokenHelper.getToken())
    }.getOrElse {
        it.stackTraceToString()
        arrayListOf()
    }

    override suspend fun deleteGarden(gardenId: String) = runCatching {
        deleteGardenSource.delete(tokenHelper.getToken(), gardenId)
    }.getOrElse {
        it.printStackTrace()
        false
    }

    override suspend fun createGarden(gardenName: String) = runCatching {
        createGardenSource.createGarden(tokenHelper.getToken(), gardenName, languageHelper.getLanguage())
    }.getOrElse {
        it.printStackTrace()
        null
    }

    override suspend fun addParticipant(email: String, gardenId: String) = runCatching {
        addParticipantSource.addParticipant(tokenHelper.getToken(), email, gardenId)
    }.getOrElse {
        it.printStackTrace()
        false
    }

    override suspend fun deleteParticipant(participantId: String) = runCatching {
        deleteParticipantSource.deleteParticipant(tokenHelper.getToken(), participantId)
    }.getOrElse {
        it.printStackTrace()
        false
    }

    override suspend fun changeParticipantRole(participantId: String, role: String) = runCatching {
        editParticipantRoleSource.editRole(tokenHelper.getToken(), participantId, role)
    }.getOrElse {
        it.printStackTrace()
        false
    }

    override suspend fun editGardenNameAndSeason(id: String, name: String, summerClimateType: String) = runCatching {
        editDataSource.edit(tokenHelper.getToken(), id, name, summerClimateType)
    }.getOrElse {
        it.printStackTrace()
        false
    }

}