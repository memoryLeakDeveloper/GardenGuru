package com.entexy.gardenguru.domain.usecases.garden

import com.entexy.gardenguru.data.garden.GardenRepositoryImpl
import javax.inject.Inject

class GetGardensUseCase @Inject constructor(private val repository: GardenRepositoryImpl) {

    suspend fun getGardens() =
        repository.getGardens()
}