package com.example.gardenguru.domain.usecases.garden

import com.example.gardenguru.data.garden.GardenRepositoryImpl
import javax.inject.Inject

class DeleteGardenUseCase @Inject constructor(private val repository: GardenRepositoryImpl) {
    suspend fun perform(gardenId: String): Boolean = repository.deleteGarden(gardenId)

}