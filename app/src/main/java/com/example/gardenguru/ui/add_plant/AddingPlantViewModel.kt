package com.example.gardenguru.ui.add_plant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gardenguru.data.garden.models.GardenData
import com.example.gardenguru.data.garden.models.GardenName
import com.example.gardenguru.domain.garden.CreateGardenUseCase
import com.example.gardenguru.domain.garden.GetGardenNamesUseCase
import com.example.gardenguru.domain.plant.CreatePlantUseCase
import javax.inject.Inject

class AddingPlantViewModel constructor(
    private val createPlantUseCase: CreatePlantUseCase,
    private val createGardenUseCase: CreateGardenUseCase,
    private val getGardenNamesUseCase: GetGardenNamesUseCase
) : ViewModel() {

    private val _gardens = MutableLiveData<ArrayList<GardenData>>().apply {
        value = arrayListOf()
    }
    val gardens: LiveData<ArrayList<GardenData>> = _gardens

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
