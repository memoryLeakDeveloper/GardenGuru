package com.entexy.gardenguru.domain.usecases.garden

import com.entexy.gardenguru.domain.repository.GardenRepository
import javax.inject.Inject

class DeleteGardenUseCase @Inject constructor(private val repository: GardenRepository) {
    suspend fun perform(gardenId: String): Boolean = repository.deleteGarden(gardenId)

}