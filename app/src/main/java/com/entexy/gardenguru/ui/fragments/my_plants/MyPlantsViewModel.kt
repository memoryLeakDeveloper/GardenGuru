package com.entexy.gardenguru.ui.fragments.my_plants

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.entexy.gardenguru.data.garden.models.GardenData
import com.entexy.gardenguru.domain.usecases.app.UserEmailUseCase
import com.entexy.gardenguru.domain.usecases.garden.DeleteGardenUseCase
import com.entexy.gardenguru.domain.usecases.garden.GetGardensUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPlantsViewModel @Inject constructor(
    private val getGardensUseCase: GetGardensUseCase,
    private val deleteGardenUseCase: DeleteGardenUseCase,
    userEmailUseCase: UserEmailUseCase,
) : ViewModel() {

    val userEmail = userEmailUseCase.getEmail()

    private suspend fun loadGardens(): ArrayList<GardenData> {
        return getGardensUseCase.getGardens()
    }

    fun initGardens() {
        _gardens.value = null
        CoroutineScope(Dispatchers.IO).launch {
            _gardens.postValue(loadGardens())
        }
    }

    suspend fun leaveGarden(gardenId: String): Boolean {
        return deleteGardenUseCase.perform(gardenId)
    }

    private val _gardens = MutableLiveData<ArrayList<GardenData>>().apply {
        value = null
    }
    val gardens: LiveData<ArrayList<GardenData>> = _gardens
}
