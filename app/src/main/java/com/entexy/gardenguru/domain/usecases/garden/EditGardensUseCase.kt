package com.entexy.gardenguru.domain.usecases.garden

import com.entexy.gardenguru.data.garden.GardenRepository
import javax.inject.Inject

class EditGardensUseCase @Inject constructor(private val repository: GardenRepository) {

    suspend fun perform(id: String,
                        name: String,
                        summerClimateType: String): Boolean =
        repository.editGardenNameAndSeason(id, name, summerClimateType)
}