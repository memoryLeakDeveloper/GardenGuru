package com.example.gardenguru.domain.garden

import com.example.gardenguru.data.garden.GardenRepository
import javax.inject.Inject

class CompleteEventUseCase @Inject constructor(private val repository: GardenRepository) {

    suspend fun perform() =
        repository.getGardens()
}