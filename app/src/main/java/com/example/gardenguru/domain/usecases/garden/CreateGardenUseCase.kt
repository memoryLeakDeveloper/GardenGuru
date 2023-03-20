package com.example.gardenguru.domain.usecases.garden

import com.example.gardenguru.data.garden.GardenRepository
import javax.inject.Inject

class CreateGardenUseCase @Inject constructor(private val repository: GardenRepository) {
    suspend fun perform(name: String) = repository.createGarden(name)
}