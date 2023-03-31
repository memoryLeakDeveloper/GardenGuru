package com.entexy.gardenguru.ui.fragments.plants

import androidx.lifecycle.ViewModel
import com.entexy.gardenguru.core.App
import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.auth.TokenHelper
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.user.UserData
import com.entexy.gardenguru.domain.usecases.plant.FetchAllPlantsUseCase
import com.entexy.gardenguru.domain.usecases.user.FetchUserPlantsUseCase
import com.entexy.gardenguru.utils.bugger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MyPlantsViewModel @Inject constructor(
    private val fetchUserPlantsUseCase: FetchUserPlantsUseCase,
    private val fetchAllPlantsUseCase: FetchAllPlantsUseCase,
    private val tokenHelper: TokenHelper
) : ViewModel() {

    suspend fun fetchPlants(): Flow<CloudResponse<ArrayList<PlantData>>> {
        tokenHelper.getToken()?.let {
            fetchUserPlantsUseCase.fetch(it)?.let { list ->
                App.user = UserData(it, list)
                bugger(App.user)
            }
        }
        return fetchAllPlantsUseCase.perform(App.user!!.userPlants)
    }
}
