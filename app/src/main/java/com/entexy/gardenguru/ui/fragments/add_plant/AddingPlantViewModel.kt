package com.entexy.gardenguru.ui.fragments.add_plant

import androidx.lifecycle.ViewModel
import com.entexy.gardenguru.data.garden.models.GardenName
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.domain.usecases.garden.CreateGardenUseCase
import com.entexy.gardenguru.domain.usecases.garden.GetGardensUseCase
import com.entexy.gardenguru.domain.usecases.plant.AddPlantUseCase
import com.entexy.gardenguru.domain.usecases.plant.SearchPlantUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddingPlantViewModel @Inject constructor(
    private val createGardenUseCase: CreateGardenUseCase,
    private val getGardenNamesUseCase: GetGardensUseCase,
    private val searchPlantUseCase: SearchPlantUseCase,
    private val addPlantUseCase: AddPlantUseCase
) : ViewModel() {

    private lateinit var gardenNames: ArrayList<GardenName>
    suspend fun loadGardens() = getGardenNamesUseCase.getGardens()

    suspend fun findPlants(plantSearchQuires: List<String>) = searchPlantUseCase.searchPlantByVarietyCode(plantSearchQuires)

    suspend fun findPlantsByVariety(plantVariety: String) = searchPlantUseCase.searchPlantByName(plantVariety)

    suspend fun createGarden(gardenName: String): GardenName? {
        val garden = createGardenUseCase.perform(gardenName)
        if (garden != null) {
            gardenNames.add(garden)
        }
        return garden
    }

    suspend fun addPlant(plantData: PlantData) = addPlantUseCase.invoke(plantData.id)


}
