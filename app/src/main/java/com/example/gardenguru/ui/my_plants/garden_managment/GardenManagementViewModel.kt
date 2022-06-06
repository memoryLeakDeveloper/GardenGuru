package com.example.gardenguru.ui.my_plants.garden_managment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gardenguru.domain.app.UserEmailUseCase
import com.example.gardenguru.domain.garden.EditGardensUseCase
import com.example.gardenguru.domain.garden.GetGardensUseCase
import com.example.gardenguru.ui.my_plants.MyPlantsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class GardenManagementViewModel constructor(private val editGardensUseCase: EditGardensUseCase) : ViewModel() {

    class Factory @Inject constructor(
        private val editGardensUseCase: EditGardensUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return GardenManagementViewModel(editGardensUseCase) as T
        }
    }

    suspend fun editGarden(id: String, name: String, summerClimateType: String): Boolean{
        return editGardensUseCase.perform(id, name, summerClimateType)
    }
}
