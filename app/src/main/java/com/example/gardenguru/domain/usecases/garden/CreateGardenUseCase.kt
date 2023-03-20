package com.example.gardenguru.domain.usecases.garden

import com.example.gardenguru.data.garden.GardenRepositoryImpl
import javax.inject.Inject

class CreateGardenUseCase @Inject constructor(private val repository: GardenRepositoryImpl) {
    suspend fun perform(name: String) = repository.createGarden(name)

}