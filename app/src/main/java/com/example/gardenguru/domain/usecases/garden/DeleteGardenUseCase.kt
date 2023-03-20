package com.example.gardenguru.domain.usecases.garden

import com.example.gardenguru.data.garden.GardenRepository
import javax.inject.Inject

class DeleteGardenUseCase @Inject constructor(private val repository: GardenRepository) {
    suspend fun perform(gardenId: String): Boolean =
        repository.deleteGarden(gardenId)
}