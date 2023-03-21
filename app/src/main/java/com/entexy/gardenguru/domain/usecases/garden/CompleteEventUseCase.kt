package com.entexy.gardenguru.domain.usecases.garden

import com.entexy.gardenguru.domain.repository.GardenRepository
import javax.inject.Inject

class CompleteEventUseCase @Inject constructor(private val repository: GardenRepository) {

    suspend fun perform() = repository.getGardens()

}