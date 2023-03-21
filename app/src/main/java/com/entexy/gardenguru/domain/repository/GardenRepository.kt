package com.entexy.gardenguru.domain.repository

import com.entexy.gardenguru.data.garden.models.GardenData
import com.entexy.gardenguru.data.garden.models.GardenName

interface GardenRepository {

    suspend fun getGardens(): List<GardenData>

    suspend fun deleteGarden(gardenId: String): Boolean

    suspend fun createGarden(gardenName: String): GardenName?

    suspend fun getGardenNames(): ArrayList<GardenName>

    suspend fun addParticipant(email: String, gardenId: String): Boolean

    suspend fun deleteParticipant(participantId: String): Boolean

    suspend fun changeParticipantRole(participantId: String, role: String): Boolean

    suspend fun editGardenNameAndSeason(id: String, name: String, summerClimateType: String): Boolean
}