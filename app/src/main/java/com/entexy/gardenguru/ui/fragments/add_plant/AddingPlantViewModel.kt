package com.entexy.gardenguru.ui.fragments.add_plant

import androidx.lifecycle.ViewModel
import com.entexy.gardenguru.data.garden.models.GardenName
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.domain.usecases.garden.CreateGardenUseCase
import com.entexy.gardenguru.domain.usecases.garden.GetGardenNamesUseCase
import com.entexy.gardenguru.domain.usecases.plant.CreatePlantUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddingPlantViewModel @Inject constructor(
    private val createPlantUseCase: CreatePlantUseCase,
    private val createGardenUseCase: CreateGardenUseCase,
    private val getGardenNamesUseCase: GetGardenNamesUseCase
) : ViewModel() {

    private lateinit var gardenNames: ArrayList<GardenName>

    var selectedGarden = -1
    suspend fun loadGardensNames(): ArrayList<GardenName> {
        gardenNames = getGardenNamesUseCase.getNames()
        return gardenNames
    }

    suspend fun createGarden(gardenName: String): GardenName? {
        val garden = createGardenUseCase.perform(gardenName)
        if (garden != null) {
            gardenNames.add(garden)
        }
        return garden
    }

    suspend fun createPlant(plantData: PlantData): Boolean {
        return createPlantUseCase.createPlant(gardenNames[selectedGarden].id, plantData)
    }
}
