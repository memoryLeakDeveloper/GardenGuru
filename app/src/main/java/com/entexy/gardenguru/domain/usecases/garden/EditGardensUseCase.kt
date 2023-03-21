package com.entexy.gardenguru.domain.usecases.garden

import com.entexy.gardenguru.domain.repository.GardenRepository
import javax.inject.Inject

class EditGardensUseCase @Inject constructor(private val repository: GardenRepository) {

    suspend fun perform(id: String, name: String, summerClimateType: String) =
        repository.editGardenNameAndSeason(id, name, summerClimateType)

}