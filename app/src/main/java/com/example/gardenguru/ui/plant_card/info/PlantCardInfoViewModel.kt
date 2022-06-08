package com.example.gardenguru.ui.plant_card.info

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gardenguru.domain.plant.FetchPlantUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PlantCardInfoViewModel @Inject constructor(private val fetchPlantUseCase: FetchPlantUseCase) : ViewModel() {

    suspend fun fetchPlant(id: String) = fetchPlantUseCase.fetchPlant(id)

}
