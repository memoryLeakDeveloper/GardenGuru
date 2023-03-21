package com.entexy.gardenguru.domain.usecases.garden

import com.entexy.gardenguru.domain.repository.GardenRepository
import javax.inject.Inject

class CreateGardenUseCase @Inject constructor(private val repository: GardenRepository) {
    suspend fun perform(name: String) = repository.createGarden(name)

}