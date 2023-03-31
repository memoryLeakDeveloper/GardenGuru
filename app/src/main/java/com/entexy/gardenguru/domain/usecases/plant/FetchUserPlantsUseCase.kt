package com.entexy.gardenguru.domain.usecases.plant

import com.entexy.gardenguru.domain.repository.PlantRepository
import com.entexy.gardenguru.domain.repository.UserRepository
import javax.inject.Inject

class FetchUserPlantsUseCase @Inject constructor(private val repository: PlantRepository) {

    suspend fun fetch(id: String) = runCatching {
        repository.fetchAllPlants(id)
    }.getOrNull()

}