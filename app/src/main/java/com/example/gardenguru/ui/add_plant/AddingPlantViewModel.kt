package com.example.gardenguru.ui.add_plant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gardenguru.data.garden.models.GardenName
import com.example.gardenguru.data.plant.PlantData
import com.example.gardenguru.data.plant.cloud.create.CreatePlantCloudObj
import com.example.gardenguru.domain.garden.CreateGardenUseCase
import com.example.gardenguru.domain.garden.GetGardenNamesUseCase
import com.example.gardenguru.domain.plant.CreatePlantUseCase
import javax.inject.Inject

class AddingPlantViewModel constructor(
    private val createPlantUseCase: CreatePlantUseCase,
    private val createGardenUseCase: CreateGardenUseCase,
    private val getGardenNamesUseCase: GetGardenNamesUseCase
) : ViewModel() {

    private lateinit var gardenNames: ArrayList<GardenName>

    var selectedGarden = -1
    suspend fun loadGardensNames(): ArrayList<GardenName>{
        gardenNames = getGardenNamesUseCase.getNames()
        return gardenNames
    }

    suspend fun createGarden(gardenName: String): GardenName?{
        val garden = createGardenUseCase.perform(gardenName)
        if (garden != null){
            gardenNames.add(garden)
        }
        return garden
    }

    suspend fun createPlant(plantData: PlantData): Boolean{
        return createPlantUseCase.createPlant(gardenNames[selectedGarden].id, plantData)
    }

    class Factory @Inject constructor(
        private val createPlantUseCase: CreatePlantUseCase,
        private val createGardenUseCase: CreateGardenUseCase,
        private val getGardenNamesUseCase: GetGardenNamesUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddingPlantViewModel(createPlantUseCase, createGardenUseCase, getGardenNamesUseCase) as T
        }
    }
}
