package com.example.gardenguru.domain.usecases.garden

import com.example.gardenguru.data.garden.GardenRepositoryImpl
import javax.inject.Inject

class GetGardensUseCase @Inject constructor(private val repository: GardenRepositoryImpl) {

    suspend fun getGardens() = repository.getGardens()

}