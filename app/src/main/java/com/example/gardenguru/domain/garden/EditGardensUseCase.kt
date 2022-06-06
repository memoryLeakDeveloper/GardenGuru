package com.example.gardenguru.domain.garden

import com.example.gardenguru.data.garden.GardenRepository
import javax.inject.Inject

class EditGardensUseCase @Inject constructor(private val repository: GardenRepository) {

    suspend fun perform(id: String,
                        name: String,
                        summerClimateType: String): Boolean =
        repository.editGardenNameAndSeason(id, name, summerClimateType)
}