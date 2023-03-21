package com.entexy.gardenguru.ui.fragments.my_plants

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.entexy.gardenguru.data.garden.models.GardenData
import com.entexy.gardenguru.domain.usecases.app.UserEmailUseCase
import com.entexy.gardenguru.domain.usecases.garden.DeleteGardenUseCase
import com.entexy.gardenguru.domain.usecases.garden.GetGardensUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPlantsViewModel @Inject constructor(
    private val getGardensUseCase: GetGardensUseCase,
    private val deleteGardenUseCase: DeleteGardenUseCase,
    userEmailUseCase: UserEmailUseCase,
) : ViewModel() {

    private val _gardensLiveData = MutableLiveData<List<GardenData>?>()
    val gardensLiveData = _gardensLiveData

    val userEmail = userEmailUseCase.getEmail()

    private suspend fun loadGardens() = getGardensUseCase.getGardens()


    fun initGardens() = viewModelScope.launch {
        _gardensLiveData.value = null
        _gardensLiveData.postValue(loadGardens())
    }

    suspend fun leaveGarden(gardenId: String): Boolean {
        return deleteGardenUseCase.perform(gardenId)
    }

}
