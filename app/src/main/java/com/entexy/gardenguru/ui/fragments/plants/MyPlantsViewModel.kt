package com.entexy.gardenguru.ui.fragments.plants

import androidx.lifecycle.ViewModel
import com.entexy.gardenguru.domain.usecases.plant.FetchAllPlantsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPlantsViewModel @Inject constructor(
    private val fetchAllPlantsUseCase: FetchAllPlantsUseCase,
) : ViewModel() {

    suspend fun fetchPlants() = fetchAllPlantsUseCase.perform()
}
