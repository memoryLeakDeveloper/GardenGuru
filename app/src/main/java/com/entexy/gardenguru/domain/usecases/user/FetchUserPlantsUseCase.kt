package com.entexy.gardenguru.domain.usecases.user

import com.entexy.gardenguru.domain.repository.UserRepository
import javax.inject.Inject

class FetchUserPlantsUseCase @Inject constructor(private val repository: UserRepository) {

    suspend fun fetch(id: String) = runCatching {
        repository.fetchAllPlants(id)
    }.getOrNull()

}