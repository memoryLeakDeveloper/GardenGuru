package com.example.gardenguru.domain.usecases.garden

import com.example.gardenguru.data.garden.GardenRepository
import javax.inject.Inject

class GetGardensUseCase @Inject constructor(private val repository: GardenRepository) {

    suspend fun getGardens() =
        repository.getGardens()
}