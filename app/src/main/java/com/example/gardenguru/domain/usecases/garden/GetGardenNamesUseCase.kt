package com.example.gardenguru.domain.usecases.garden

import com.example.gardenguru.data.garden.GardenRepository
import javax.inject.Inject

class GetGardenNamesUseCase @Inject constructor(private val repository: GardenRepository) {
    suspend fun getNames() =
        repository.getGardenNames()
}