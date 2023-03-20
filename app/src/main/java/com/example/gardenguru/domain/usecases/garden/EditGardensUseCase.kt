package com.example.gardenguru.domain.usecases.garden

import com.example.gardenguru.data.garden.GardenRepositoryImpl
import javax.inject.Inject

class EditGardensUseCase @Inject constructor(private val repository: GardenRepositoryImpl) {

    suspend fun perform(id: String, name: String, summerClimateType: String) =
        repository.editGardenNameAndSeason(id, name, summerClimateType)

}