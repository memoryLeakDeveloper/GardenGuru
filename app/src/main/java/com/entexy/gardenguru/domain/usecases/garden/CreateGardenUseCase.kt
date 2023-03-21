package com.entexy.gardenguru.domain.usecases.garden

import com.entexy.gardenguru.data.garden.GardenRepositoryImpl
import javax.inject.Inject

class CreateGardenUseCase @Inject constructor(private val repository: GardenRepositoryImpl) {
    suspend fun perform(name: String) = repository.createGarden(name)

}