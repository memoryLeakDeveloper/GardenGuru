package com.example.gardenguru.ui.plant_card.info

import androidx.lifecycle.ViewModel
import com.example.gardenguru.domain.plant.FetchPlantUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlantCardInfoViewModel @Inject constructor(private val fetchPlantUseCase: FetchPlantUseCase) : ViewModel() {

    suspend fun fetchPlant(id: String) = fetchPlantUseCase.fetchPlant(id)

}
