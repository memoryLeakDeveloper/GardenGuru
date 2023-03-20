package com.entexy.gardenguru.ui.fragments.plant_card.info

import androidx.lifecycle.ViewModel
import com.entexy.gardenguru.domain.usecases.plant.FetchPlantUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlantCardInfoViewModel @Inject constructor(private val fetchPlantUseCase: FetchPlantUseCase) : ViewModel() {

    suspend fun fetchPlant(id: String) = fetchPlantUseCase.fetchPlant(id)

}
