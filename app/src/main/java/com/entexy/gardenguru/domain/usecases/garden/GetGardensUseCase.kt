package com.entexy.gardenguru.domain.usecases.garden

import com.entexy.gardenguru.domain.repository.GardenRepository
import javax.inject.Inject

class GetGardensUseCase @Inject constructor(private val repository: GardenRepository) {

    suspend fun getGardens() =
        repository.getGardens()
}