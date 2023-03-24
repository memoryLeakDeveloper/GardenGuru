package com.entexy.gardenguru.ui.fragments.plants

import androidx.lifecycle.ViewModel
import com.entexy.gardenguru.domain.usecases.app.UserEmailUseCase
import com.entexy.gardenguru.domain.usecases.garden.DeleteGardenUseCase
import com.entexy.gardenguru.domain.usecases.garden.GetGardensUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPlantsViewModel @Inject constructor(
    private val getGardensUseCase: GetGardensUseCase,
    private val deleteGardenUseCase: DeleteGardenUseCase,
    userEmailUseCase: UserEmailUseCase,
) : ViewModel() {

    val userEmail = userEmailUseCase.getEmail()

    private suspend fun loadGardens() = getGardensUseCase.getGardens()

    suspend fun getGardens() = getGardensUseCase.getGardens()

    suspend fun leaveGarden(gardenId: String): Boolean {
        return deleteGardenUseCase.perform(gardenId)
    }

}
