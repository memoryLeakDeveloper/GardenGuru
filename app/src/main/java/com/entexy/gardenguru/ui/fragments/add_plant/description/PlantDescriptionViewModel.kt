package com.entexy.gardenguru.ui.fragments.add_plant.description

import androidx.lifecycle.ViewModel
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.domain.usecases.plant.AddPlantUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlantDescriptionViewModel @Inject constructor(private val addPlantUseCase: AddPlantUseCase) : ViewModel() {

    suspend fun addPlant(plantData: PlantData) = addPlantUseCase.invoke(plantData)

}
