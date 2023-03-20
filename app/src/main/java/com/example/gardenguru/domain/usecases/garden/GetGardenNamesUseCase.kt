package com.example.gardenguru.domain.usecases.garden

import com.example.gardenguru.data.garden.GardenRepositoryImpl
import javax.inject.Inject

class GetGardenNamesUseCase @Inject constructor(private val repository: GardenRepositoryImpl) {
    suspend fun getNames() = repository.getGardenNames()

}