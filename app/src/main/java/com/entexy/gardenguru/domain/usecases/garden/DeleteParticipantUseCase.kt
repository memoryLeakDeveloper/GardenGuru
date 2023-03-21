package com.entexy.gardenguru.domain.usecases.garden

import com.entexy.gardenguru.data.garden.GardenRepositoryImpl
import javax.inject.Inject

class DeleteParticipantUseCase @Inject constructor(private val repository: GardenRepositoryImpl) {
    suspend fun perform(id: String): Boolean = repository.deleteParticipant(id)

}