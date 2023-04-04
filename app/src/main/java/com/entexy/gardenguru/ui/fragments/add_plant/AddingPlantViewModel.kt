package com.entexy.gardenguru.ui.fragments.add_plant

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.domain.usecases.plant.AddPlantUseCase
import com.entexy.gardenguru.domain.usecases.plant.SearchPlantUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddingPlantViewModel @Inject constructor(
    private val searchPlantUseCase: SearchPlantUseCase,
    private val addPlantUseCase: AddPlantUseCase
) : ViewModel() {

    private val _plantsData = MutableLiveData<List<PlantData>>()
    val plantsData = _plantsData

    suspend fun findPlants(plantSearchQuires: List<String>) = searchPlantUseCase.searchPlantByVarietyCode(plantSearchQuires)

    suspend fun findPlantsByVariety(plantVariety: String) = searchPlantUseCase.searchPlantByName(plantVariety)

    suspend fun addPlant(plantData: PlantData) = addPlantUseCase.invoke(plantData)
}
