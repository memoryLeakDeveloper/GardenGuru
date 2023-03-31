package com.entexy.gardenguru.ui.fragments.plants

import androidx.lifecycle.ViewModel
import com.entexy.gardenguru.core.App
import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.user.UserData
import com.entexy.gardenguru.domain.usecases.plant.FetchAllPlantsUseCase
import com.entexy.gardenguru.domain.usecases.plant.FetchUserPlantsUseCase
import com.entexy.gardenguru.utils.bugger
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MyPlantsViewModel @Inject constructor(
    private val fetchUserPlantsUseCase: FetchUserPlantsUseCase,
    private val fetchAllPlantsUseCase: FetchAllPlantsUseCase
) : ViewModel() {

    suspend fun fetchPlants(): Flow<CloudResponse<ArrayList<PlantData>>> {
        Firebase.auth.currentUser?.uid?.let {
            fetchUserPlantsUseCase.fetch(it)?.let { list ->
                App.user = UserData(it, list)
                bugger(App.user)
            }
        }
        return fetchAllPlantsUseCase.perform(App.user!!.userPlants)
    }
}
