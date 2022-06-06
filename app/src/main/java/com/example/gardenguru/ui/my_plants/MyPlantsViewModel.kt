package com.example.gardenguru.ui.my_plants

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gardenguru.data.garden.models.GardenData
import com.example.gardenguru.domain.app.UserEmailUseCase
import com.example.gardenguru.domain.garden.GetGardensUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class MyPlantsViewModel constructor(
    private val getGardensUseCase: GetGardensUseCase,
    userEmailUseCase: UserEmailUseCase,
    ) : ViewModel() {

    val userEmail = userEmailUseCase.getEmail()

    private suspend fun loadGardens(): ArrayList<GardenData> {
        return getGardensUseCase.getGardens()
    }

    fun initGardens(){
        CoroutineScope(Dispatchers.IO).launch {
            _gardens.postValue(loadGardens())
        }
    }

    private val _gardens = MutableLiveData<ArrayList<GardenData>>().apply {
        value = arrayListOf()
    }
    val gardens: LiveData<ArrayList<GardenData>> = _gardens

    class Factory @Inject constructor(
        private val getGardensUseCase: GetGardensUseCase,
        private val userEmailUseCase: UserEmailUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MyPlantsViewModel(getGardensUseCase, userEmailUseCase) as T
        }
    }
}
