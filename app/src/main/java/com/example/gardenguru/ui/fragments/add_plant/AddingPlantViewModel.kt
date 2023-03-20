package com.example.gardenguru.ui.fragments.add_plant

import androidx.lifecycle.ViewModel
import com.example.gardenguru.data.garden.models.GardenName
import com.example.gardenguru.data.plant.PlantData
import com.example.gardenguru.domain.usecases.garden.CreateGardenUseCase
import com.example.gardenguru.domain.usecases.garden.GetGardenNamesUseCase
import com.example.gardenguru.domain.usecases.plant.CreatePlantUseCase
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
    suspend fun loadGardensNames() = getGardenNamesUseCase.getNames()

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
