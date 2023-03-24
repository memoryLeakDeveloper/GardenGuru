package com.entexy.gardenguru.ui.fragments.plant_card.info

import androidx.lifecycle.ViewModel
import com.entexy.gardenguru.domain.usecases.garden.GetGardensUseCase
import com.entexy.gardenguru.domain.usecases.plant.DeletePlantUseCase
import com.entexy.gardenguru.domain.usecases.plant.FetchPlantUseCase
import com.entexy.gardenguru.domain.usecases.plant.MovePlantUseCase
import com.entexy.gardenguru.domain.usecases.plant.RenamePlantUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlantCardInfoViewModel @Inject constructor(
    private val fetchPlantUseCase: FetchPlantUseCase,
    private val deletePlantUseCase: DeletePlantUseCase,
    private val movePlantUseCase: MovePlantUseCase,
    private val getGardensUseCase: GetGardensUseCase,
    private val renamePlantUseCase: RenamePlantUseCase,
) : ViewModel() {

    suspend fun fetchPlant(id: String) = fetchPlantUseCase.fetchPlant(id)
    suspend fun deletePlant(plantId: String) =
        deletePlantUseCase.perform(plantId)


    suspend fun movePlant(plantId: String, gardenId: String) =
        movePlantUseCase.perform(plantId, gardenId)

    suspend fun getGardens() = getGardensUseCase.getGardens()
    suspend fun setPlantName(plantId: String, plantName: String) =
        renamePlantUseCase.perform(plantId, plantName)
}
